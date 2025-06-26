package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class SetMode extends Command {

    private Command newMode;

    public SetMode(Command newMode) {
        this.newMode = newMode;
    }

    @Override
    public void initialize() {
        Robot.setMode(newMode);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
