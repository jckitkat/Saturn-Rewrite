package frc.robot.utils;

import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveRequest.Idle;

import frc.robot.commands.modes.Initialize;

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
