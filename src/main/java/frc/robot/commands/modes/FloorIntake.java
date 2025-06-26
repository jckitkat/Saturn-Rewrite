package frc.robot.commands.modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.SetMode;
import frc.robot.commands.subsystemControl.intake.SetIntakePosition;
import frc.robot.commands.subsystemControl.intake.SetRollerSpeed;
import frc.robot.utils.Constants;
import frc.robot.utils.Modes;
import frc.robot.utils.TriggerBoard;

public class FloorIntake extends SequentialCommandGroup {
    public FloorIntake() {
        addCommands(
                new SetIntakePosition(Constants.Intake.groundPosition),
                new SetRollerSpeed(20),
                new WaitUntilCommand(TriggerBoard::intakeHasNote),
                new SetMode(Modes.FloorPickup)
        );
    }
}
