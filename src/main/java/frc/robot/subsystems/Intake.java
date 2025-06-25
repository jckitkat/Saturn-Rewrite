package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.Constants;

public class Intake extends SubsystemBase {

    private double targetPivotPosition = 0;

    private final TalonFX leftPivotMotor, rightPivotMotor;
    private final TalonFX rollerMotor;
    private final TalonFXConfiguration leftPivotMotorConfig, rightPivotMotorConfig, rollerConfig;
    private final Slot0Configs pivotPID;
    private final MotionMagicConfigs pivotMotionMagicConfig;
    private final DutyCycleEncoder absoluteEncoder;

    private final AnalogInput analogSensor = new AnalogInput(3);
    private final DigitalInput digitalSensor = new DigitalInput(3);

    public Intake() {
        leftPivotMotor = new TalonFX(Constants.Intake.leftPivotID);
        leftPivotMotorConfig = new TalonFXConfiguration();
        leftPivotMotorConfig.Feedback.SensorToMechanismRatio = Constants.Intake.conversionFactor;
        leftPivotMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        leftPivotMotorConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        pivotPID = new Slot0Configs();
        pivotPID.kP = Constants.Intake.positionP;
        pivotPID.kI = Constants.Intake.positionI;
        pivotPID.kD = Constants.Intake.positionD;
        pivotPID.kG = Constants.Intake.positionG;
        pivotPID.kS = Constants.Intake.positionS;
        pivotPID.kV = Constants.Intake.positionV;
        pivotPID.GravityType = GravityTypeValue.Arm_Cosine;
        pivotMotionMagicConfig = new MotionMagicConfigs();
        pivotMotionMagicConfig.MotionMagicCruiseVelocity = Constants.Intake.maxVel;
        pivotMotionMagicConfig.MotionMagicAcceleration = Constants.Intake.maxAccel;
        leftPivotMotor.getConfigurator().apply(leftPivotMotorConfig);
        leftPivotMotor.getConfigurator().apply(pivotPID);
        leftPivotMotor.getConfigurator().apply(pivotMotionMagicConfig);

        rightPivotMotor = new TalonFX(Constants.Intake.rightPivotID);
        rightPivotMotorConfig = new TalonFXConfiguration();
        rightPivotMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        rightPivotMotor.getConfigurator().apply(rightPivotMotorConfig);
        rightPivotMotor.setControl(new Follower(Constants.Intake.leftPivotID, true));

        rollerMotor = new TalonFX(Constants.Intake.rollerCANID);
        rollerConfig = new TalonFXConfiguration();
        rollerConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        absoluteEncoder = new DutyCycleEncoder(1);

    }

    @Override
    public void periodic() {
        if (!areEncodersSynced()) {
            leftPivotMotor.setPosition(getPivotAbsolutePosition());
        }
    }

    public boolean areEncodersSynced() {
        return !(leftPivotMotor.getPosition()
                .getValueAsDouble() < (getPivotAbsolutePosition() - Constants.Sensors.encoderTolerance)
                || leftPivotMotor.getPosition()
                        .getValueAsDouble() > (getPivotAbsolutePosition() - Constants.Sensors.encoderTolerance));
    }

    public void setRollerSpeed(double speed) {
        rollerMotor.set(speed);
    }

    public void setPivotPosition(double position) {
        leftPivotMotor.setControl(new MotionMagicVoltage(position));
        targetPivotPosition = position;
    }

    public boolean isAtPosition() {
        return MathUtil.isNear(targetPivotPosition, getPivotPosition(), Constants.Intake.pivotTolerance);
    }

    public double getPivotPosition() {
        return leftPivotMotor.getPosition().getValueAsDouble();
    }

    public double getPivotAbsolutePosition() {
        return absoluteEncoder.get() - Constants.Intake.absoluteOffset;
    }

    public boolean hasGamePiece() {
        return (analogSensor.getValue() > 2700) || !digitalSensor.get();
    }

}
