package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.utils.TriggerBoard;

public class Idle extends Command {

    public Idle() {
        addRequirements(Robot.intake, Robot.shooter);
    }

    @Override
    public void initialize() {
        return;
    }

    @Override
    public void execute() {
        if (TriggerBoard.isShootButtonPressed()) {
            return;
        }
    }

}
