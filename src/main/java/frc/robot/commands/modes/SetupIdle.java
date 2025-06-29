package frc.robot.commands.modes;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.SetMode;
import frc.robot.commands.subsystemControl.intake.SetIntakePosition;
import frc.robot.commands.subsystemControl.shooter.SetShooterPosition;
import frc.robot.utils.Constants;
import frc.robot.utils.Modes;
import frc.robot.utils.TriggerBoard;

import java.util.HashMap;
import java.util.Map;

public class SetupIdle extends SequentialCommandGroup {

    private Map<String, Command> commandMap = new HashMap<String, Command>();

    public SetupIdle() {
        commandMap.put("prep shoot", prepShootCommand());
        commandMap.put("normal", normalCommand());

        addCommands(
                new SelectCommand<String>(commandMap, this::selectTargetIdle),
                new SetMode(Modes.idle)
        );
    }

    private Command prepShootCommand() {
        return new ParallelCommandGroup(
                new SetShooterPosition(Constants.Shooter.stowPosition),
                new SetIntakePosition(0.15)
        );
    }

    private Command normalCommand() {
        return new ParallelCommandGroup(
                new SetShooterPosition(Constants.Shooter.stowPosition),
                new SetIntakePosition((Constants.Intake.topPosition))
        );
    }

    private String selectTargetIdle() {
        if (TriggerBoard.shooterHasNote() && !TriggerBoard.intakeHasNote()) {
            return "prepShoot";
        } else {
            return "normal";
        }
    }
}
