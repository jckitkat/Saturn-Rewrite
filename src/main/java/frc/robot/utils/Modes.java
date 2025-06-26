package frc.robot.utils;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.modes.Handoff;
import frc.robot.commands.modes.Initialize;
import frc.robot.commands.states.FloorPickup;
import frc.robot.commands.states.Idle;

public class Modes {

    public static Initialize initialize;
    public static Idle idle;
    public static FloorPickup FloorPickup;
    public static Handoff Handoff;

    public Modes() {
        initCommands();
    }

    public static void initCommands() {
        initialize = new Initialize();
        idle = new Idle();
    }
}
