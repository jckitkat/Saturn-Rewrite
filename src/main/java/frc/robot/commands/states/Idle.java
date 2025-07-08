package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;
import frc.robot.utils.Modes;
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
            if (!TriggerBoard.intakeHasNote() && !TriggerBoard.shooterHasNote()) {
               Robot.setMode(Modes.floorIntake);
               return;
            }
            if (!TriggerBoard.intakeHasNote() && TriggerBoard.shooterHasNote()) {
                Robot.setMode(Modes.prepareShoot);
                return;
            }
        }
    }

}
