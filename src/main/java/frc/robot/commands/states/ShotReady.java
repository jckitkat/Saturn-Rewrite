package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.utils.Modes;
import frc.robot.utils.TriggerBoard;

public class ShotReady extends Command {
    public ShotReady() {
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (TriggerBoard.isShootButtonPressed()) {
            Robot.setMode(Modes.shoot);
        }
    }
}
