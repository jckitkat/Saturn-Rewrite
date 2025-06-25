package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.utils.Constants;

public class Shooter extends SubsystemBase {

    private double targetPosition = 0;
    private double targetShootVelocity = 0;
    private double targetFeedSpeed = 0;

    private final TalonFX shooterTop, shooterBottom, feeder;
    private final TalonFXConfiguration shooterTopConfig, shooterBottomConfig, feederConfig;

    private final TalonFX positionMotor;
    private final TalonFXConfiguration positionMotorConfig;

    private final MotionMagicConfigs positionMotionMagicConfig, topMotionMagicConfig, BottomMotionMagicConfig,
            feederMotionMagicConfig;
    private final Slot0Configs topSlot0Config, bottomSlot0Config, positionSlot0Config, feederSlot0Config;

    private final DutyCycleEncoder absoluteEncoder;

    private final DigitalInput digitalSensor = new DigitalInput(0);

    public Shooter() {
        shooterTop = new TalonFX(Constants.Shooter.topCANID);
        shooterBottom = new TalonFX(Constants.Shooter.bottomCANID);

        shooterTopConfig = new TalonFXConfiguration();
        shooterTopConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        shooterTopConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        topMotionMagicConfig = new MotionMagicConfigs();
        topMotionMagicConfig.MotionMagicAcceleration = Constants.Shooter.topMaxAccel;
        topMotionMagicConfig.MotionMagicCruiseVelocity = Constants.Shooter.topMaxVel;

        topSlot0Config = new Slot0Configs();
        topSlot0Config.kP = Constants.Shooter.topP;
        topSlot0Config.kI = Constants.Shooter.topI;
        topSlot0Config.kD = Constants.Shooter.topD;
        topSlot0Config.kV = Constants.Shooter.topFF;

        shooterBottomConfig = new TalonFXConfiguration();
        shooterBottomConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        shooterBottomConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        bottomSlot0Config = new Slot0Configs();
        bottomSlot0Config.kP = Constants.Shooter.bottomP;
        bottomSlot0Config.kI = Constants.Shooter.bottomI;
        bottomSlot0Config.kD = Constants.Shooter.bottomD;
        bottomSlot0Config.kV = Constants.Shooter.bottomFF;

        BottomMotionMagicConfig = new MotionMagicConfigs();
        BottomMotionMagicConfig.MotionMagicCruiseVelocity = Constants.Shooter.bottomMaxVel;
        BottomMotionMagicConfig.MotionMagicAcceleration = Constants.Shooter.bottomMaxAccel;

        shooterTop.getConfigurator().apply(shooterTopConfig);
        shooterTop.getConfigurator().apply(topSlot0Config);
        shooterTop.getConfigurator().apply(topMotionMagicConfig);

        shooterBottom.getConfigurator().apply(shooterBottomConfig);
        shooterBottom.getConfigurator().apply(bottomSlot0Config);
        shooterBottom.getConfigurator().apply(BottomMotionMagicConfig);

        feeder = new TalonFX(Constants.Shooter.feederCANID);
        feederConfig = new TalonFXConfiguration();
        feederConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        feederConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        feederSlot0Config = new Slot0Configs();
        feederSlot0Config.kP = Constants.Shooter.feederP;
        feederSlot0Config.kI = Constants.Shooter.feederI;
        feederSlot0Config.kD = Constants.Shooter.feederD;
        feederSlot0Config.kV = Constants.Shooter.feederFF;

        feederMotionMagicConfig = new MotionMagicConfigs();
        feederMotionMagicConfig.MotionMagicAcceleration = Constants.Shooter.feederMaxAccel;
        feederMotionMagicConfig.MotionMagicCruiseVelocity = Constants.Shooter.feederMaxVel;

        feeder.getConfigurator().apply(feederConfig);
        feeder.getConfigurator().apply(feederSlot0Config);
        feeder.getConfigurator().apply(feederMotionMagicConfig);

        positionMotor = new TalonFX(Constants.Shooter.positionCANID);
        positionMotorConfig = new TalonFXConfiguration();
        positionMotorConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        positionMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        positionSlot0Config = new Slot0Configs();
        positionSlot0Config.kP = Constants.Shooter.positionP;
        positionSlot0Config.kI = Constants.Shooter.positionI;
        positionSlot0Config.kD = Constants.Shooter.positionD;
        positionSlot0Config.kG = Constants.Shooter.positionG;
        positionSlot0Config.kV = Constants.Shooter.positionV;
        positionSlot0Config.kS = Constants.Shooter.positionS;

        positionMotionMagicConfig = new MotionMagicConfigs();
        positionMotionMagicConfig.MotionMagicCruiseVelocity = Constants.Shooter.maxVel;
        positionMotionMagicConfig.MotionMagicAcceleration = Constants.Shooter.maxAcc;

        positionMotor.getConfigurator().apply(positionMotorConfig);
        positionMotor.getConfigurator().apply(positionSlot0Config);
        positionMotor.getConfigurator().apply(positionMotionMagicConfig);

        absoluteEncoder = new DutyCycleEncoder(2);

    }

    @Override
    public void periodic() {
        if (!areEncodersSynced()) {
            positionMotor.setPosition(getAbsolutePosition());
        }
    }

    public boolean areEncodersSynced() {
        return !(positionMotor.getPosition()
                .getValueAsDouble() < (getAbsolutePosition() - Constants.Sensors.encoderTolerance)
                || positionMotor.getPosition()
                        .getValueAsDouble() > (getAbsolutePosition() + Constants.Sensors.encoderTolerance));
    }

    public double getAbsolutePosition() {
        return -absoluteEncoder.get() - Constants.Shooter.absoluteOffset;
    }

    public void setPosition(double position) {
        positionMotor.setControl(new MotionMagicVoltage(position));
        targetPosition = position;
    }

    public void setShootSpeed(double shootSpeed) {
        shooterTop.setControl(new MotionMagicVelocityVoltage(shootSpeed));
        shooterBottom.setControl(new MotionMagicVelocityVoltage(shootSpeed));
        targetShootVelocity = shootSpeed;
    }

    public void setFeederSpeed(double feedSpeed) {
        feeder.setControl(new MotionMagicVelocityVoltage(feedSpeed));
        targetFeedSpeed = feedSpeed;
    }

    public boolean hasGamePiece() {
        return !digitalSensor.get();
    }

    public boolean isAtPosition() {
        return MathUtil.isNear(targetPosition, positionMotor.getPosition().getValueAsDouble(),
                Constants.Shooter.positionTolerance);
    }

    public boolean areFlywheelsAtSpeed() {
        return MathUtil.isNear(targetShootVelocity, shooterTop.getVelocity().getValueAsDouble(), 0.05)
                && MathUtil.isNear(targetShootVelocity, shooterBottom.getVelocity().getValueAsDouble(), 0.05);
    }

}
