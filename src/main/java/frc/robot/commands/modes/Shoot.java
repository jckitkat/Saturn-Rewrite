package frc.robot.commands.modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.SetMode;
import frc.robot.commands.subsystemControl.shooter.SetFeedSpeed;
import frc.robot.utils.Modes;
import frc.robot.utils.TriggerBoard;

public class Shoot extends SequentialCommandGroup {

    public Shoot() {
        addCommands(
                new SetFeedSpeed(10),
                new WaitUntilCommand(TriggerBoard::shooterHasNote),
                new WaitUntilCommand(TriggerBoard::shooterDoesntHasNote),
                new SetMode(Modes.setupIdle)
        );
    }

}
