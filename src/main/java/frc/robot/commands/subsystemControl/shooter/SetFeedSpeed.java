package frc.robot.commands.subsystemControl.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class SetFeedSpeed extends Command {

    private final double speed;

    public SetFeedSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void initialize() {
        Robot.shooter.setFeederSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
