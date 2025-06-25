package frc.robot.utils;

import frc.robot.commands.modes.Initialize;
import frc.robot.commands.states.Idle;

public class Modes {

    public static Initialize initialize;
    public static Idle idle;

    public Modes() {
        initCommands();
    }

    public static void initCommands() {
        initialize = new Initialize();
        idle = new Idle();
    }
}
