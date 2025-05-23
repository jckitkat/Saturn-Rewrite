package frc.robot.commands.subsystemControl.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class SetFlywheelSpeed extends Command {

    private final double speed;

    public SetFlywheelSpeed(double speed) {

        this.speed = speed;

    }

    @Override
    public void initialize() {
        Robot.shooter.setShootSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return Robot.shooter.areFlywheelsAtSpeed();
    }

}
