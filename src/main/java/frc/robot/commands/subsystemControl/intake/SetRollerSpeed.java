package frc.robot.commands.subsystemControl.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class SetRollerSpeed extends Command {

    private final double speed;

    public SetRollerSpeed(double speed) {

        this.speed = speed;

    }

    @Override
    public void initialize() {
        Robot.intake.setRollerSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
