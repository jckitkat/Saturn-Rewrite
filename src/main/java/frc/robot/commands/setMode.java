package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class setMode extends Command {

    private final Command newMode;

    public setMode(Command newMode) {
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
