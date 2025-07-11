// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;

// import com.ctre.phoenix6.SignalLogger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.Shooter;
// import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.Modes;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private RobotContainer m_robotContainer;

    private static Command currentMode = Commands.none();
    private static Command currentDriveMode = Commands.none();
    private static Command previousMode = Commands.none();
    private static Command previousDriveMode = Commands.none();

    public static Intake intake = new Intake();
    public static Shooter shooter = new Shooter();

    public static XboxController controller = new XboxController(0);

    @Override
    public void robotInit() {
        m_robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        // SmartDashboard.putNumber("Match Time", DriverStation.getMatchTime());
        SmartDashboard.putString("Current Mode", currentMode.getName() != null && !currentMode.getName().isEmpty() ? currentMode.getName() : "None");
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void disabledExit() {
    }

    @Override
    public void autonomousInit() {
        // Vision.getInstance().turnOffAprilTags();

        // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // m_robotContainer.drivetrain.setHeadingCorrection(false);

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void autonomousExit() {
        // Vision.getInstance().turnOnAprilTags();
    }

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }

        // Vision.getInstance().turnOnAprilTags();

        // m_robotContainer.shooter.moveTo(Shooter.PositionState.HANDOFF);
        setMode(Modes.initialize);
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void teleopExit() {
        // SignalLogger.stop();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void testExit() {
    }

    public static void setMode(Command newMode) {
        if (currentMode != null) {
            currentMode.cancel();
        }
        newMode.schedule();
        currentMode = newMode;
    }

    public static void setDriveMode(Command newDriveMode) {
        currentDriveMode.cancel();
        newDriveMode.schedule();
        currentDriveMode = newDriveMode;
    }

    public static void returnToPreviousMode() {
        currentMode.cancel();
        previousMode.schedule();
        Command temp = currentMode;
        currentMode = previousMode;
        previousMode = temp;
    }
}
