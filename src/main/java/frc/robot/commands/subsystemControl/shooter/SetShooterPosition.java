package frc.robot.commands.subsystemControl.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class SetShooterPosition extends Command {

    private final double position;

    public SetShooterPosition(double position) {
        this.position = position;

    }

    @Override
    public void initialize() {
        Robot.shooter.setPosition(position);
    }

    @Override
    public boolean isFinished() {
        return Robot.shooter.isAtPosition();
    }

}
