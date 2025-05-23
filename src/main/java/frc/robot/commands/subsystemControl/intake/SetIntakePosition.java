package frc.robot.commands.subsystemControl.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;

public class SetIntakePosition extends Command {

    private final double position;

    public SetIntakePosition(double position) {

        this.position = position;

    }

    @Override
    public void initialize() {
        Robot.intake.setPivotPosition(position);
    }

    @Override
    public boolean isFinished() {
        return Robot.intake.isAtPosition();
    }

}
