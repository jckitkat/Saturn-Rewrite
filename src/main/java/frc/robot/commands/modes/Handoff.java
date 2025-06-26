package frc.robot.commands.modes;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.subsystemControl.intake.SetIntakePosition;
import frc.robot.commands.subsystemControl.intake.SetRollerSpeed;
import frc.robot.commands.subsystemControl.shooter.SetFeedSpeed;
import frc.robot.commands.subsystemControl.shooter.SetShooterPosition;
import frc.robot.utils.Constants;
import frc.robot.utils.TriggerBoard;

public class Handoff extends SequentialCommandGroup {
    public Handoff() {
        addCommands(
                new SetIntakePosition(Constants.Intake.topPosition),
                new SetShooterPosition(Constants.Shooter.stowPosition),
                new ParallelCommandGroup(
                        new SetRollerSpeed(20),
                        new SetFeedSpeed(20)
                ),
                new WaitUntilCommand(TriggerBoard::shooterHasNote)
        );
    }
}
