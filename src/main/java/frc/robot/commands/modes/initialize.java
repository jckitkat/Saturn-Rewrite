package frc.robot.commands.modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class initialize extends SequentialCommandGroup {

    public initialize() {
        addRequirements(Robot.intake, Robot.shooter);
        addCommands(

        );
    }

}
