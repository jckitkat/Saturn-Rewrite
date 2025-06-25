package frc.robot.commands.modes;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.setMode;
import frc.robot.commands.subsystemControl.intake.SetIntakePosition;
import frc.robot.commands.subsystemControl.shooter.SetShooterPosition;
import frc.robot.utils.Constants;
import frc.robot.utils.Modes;

public class Initialize extends SequentialCommandGroup {

    public Initialize() {
        addRequirements(Robot.intake, Robot.shooter);
        addCommands(
                new SetShooterPosition(Constants.Shooter.stowPosition),
                new SetIntakePosition(Constants.Intake.topPosition),
                new InstantCommand(() -> {
                    Robot.setMode(Modes.idle);
                }));
    }

}
