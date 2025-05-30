package frc.robot.commands.modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;

public class Initialize extends SequentialCommandGroup {

    public Initialize() {
        addRequirements(Robot.intake, Robot.shooter);
        addCommands(

        );
    }

}
