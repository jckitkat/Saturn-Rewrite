package frc.robot.utils;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.modes.*;
import frc.robot.commands.states.FloorPickup;
import frc.robot.commands.states.Idle;
import frc.robot.commands.states.ShotReady;

public class Modes {

    public static Initialize initialize;
    public static Idle idle;
    public static SetupIdle setupIdle;
    public static FloorPickup floorPickup;
    public static FloorIntake floorIntake;
    public static Handoff Handoff;
    public static PrepareShoot prepareShoot;
    public static ShotReady shotReady;
    public static Shoot shoot;

    public Modes() {
        initCommands();
    }

    public static void initCommands() {
        initialize = new Initialize();
        idle = new Idle();
    }
}
