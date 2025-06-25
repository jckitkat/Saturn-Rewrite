// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

// import frc.robot.commands.Amp;
// import frc.robot.commands.AutoIntake;
// import frc.robot.commands.Pass;
// import frc.robot.commands.SetIntakePosition;
// import frc.robot.commands.SetShooterPosition;
// import frc.robot.commands.Shoot;
// import frc.robot.commands.ShootAutoAim;
// import frc.robot.commands.ShortPass;
import frc.robot.commands.TeleopDrive;
// import frc.robot.commands.auton.GetRingAuton;
// import frc.robot.commands.auton.IntakeAndShoot;
// import frc.robot.commands.auton.PrepareCloseShotAuton;
// import frc.robot.commands.auton.PrepareFarShotAuton;
// import frc.robot.commands.auton.PrepareSideShotAuton;
// import frc.robot.commands.auton.ShootAuton;
// import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.Elevator;
// import frc.robot.subsystems.Intake;
// import frc.robot.subsystems.NoteLight;
// import frc.robot.subsystems.Shooter;
// import frc.robot.subsystems.Vision;
// // import frc.robot.subsystems.Vision;
import frc.robot.utils.Constants;
import frc.robot.utils.Modes;
// import frc.robot.utils.PhotonNoteDetection;
import frc.robot.utils.Constants.IO;

public class RobotContainer {

    public enum FaceLocation {
        None(0),
        Speaker(1),
        Pass(2);

        int faceLocation;

        private FaceLocation(int faceLocation) {
            this.faceLocation = faceLocation;
        }
    }

    public final Drivetrain drivetrain = Drivetrain.getInstance();

    PS5Controller driver = new PS5Controller(0);
    PS5Controller operator = new PS5Controller(1);

    GenericHID guitar = new GenericHID(2);

    FaceLocation faceLocation = FaceLocation.None;

    public RobotContainer() {
        configureBindings();
        new Modes();
        drivetrain.setDefaultCommand(new TeleopDrive(
                drivetrain,
                () -> MathUtil.applyDeadband(-driver.getRawAxis(IO.driveXAxis), Constants.IO.swerveDeadband),
                () -> MathUtil.applyDeadband(-driver.getRawAxis(IO.driveYAxis), Constants.IO.swerveDeadband),
                () -> MathUtil.applyDeadband(-driver.getRawAxis(IO.driveOmegaAxis), Constants.IO.swerveDeadband),
                () -> true,
                () -> (faceLocation == FaceLocation.Speaker)
                        ? (DriverStation.getAlliance().get() == Alliance.Blue ? Constants.FieldLocations.blueSpeaker
                                : Constants.FieldLocations.redSpeaker)
                        : Constants.FieldLocations.none,
                () -> MathUtil.applyDeadband(driver.getRawAxis(5), 0.5),
                () -> MathUtil.applyDeadband(driver.getRawAxis(2), 0.5)));
    }

    private void configureBindings() {
        Trigger resetGyroButton = new Trigger(() -> driver.getRawButton(IO.resetGyroButton));

        resetGyroButton.onTrue(new InstantCommand(drivetrain::zeroGyro));
    }

    public Command getAutonomousCommand() {
        return Commands.none();
    }
}
