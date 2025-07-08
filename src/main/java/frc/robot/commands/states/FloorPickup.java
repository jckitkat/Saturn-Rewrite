package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.commands.SetMode;
import frc.robot.utils.Modes;
import frc.robot.utils.TriggerBoard;

public class FloorPickup extends Command {

    public FloorPickup() {

    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (TriggerBoard.intakeHasNote()) {
            Robot.setMode(Modes.Handoff);
            return;
        }
    }

}