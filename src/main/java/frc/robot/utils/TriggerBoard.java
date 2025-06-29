package frc.robot.utils;

import frc.robot.Robot;

public class TriggerBoard {

    public static boolean isIntakeButtonPressed() {
        return Robot.controller.getRightBumperButton();
    }

    public static boolean isShootButtonPressed() {
        return Robot.controller.getLeftBumperButton();
    }

    public static boolean isResetButtonPressed() {
        return Robot.controller.getStartButton();
    }

    public static boolean intakeHasNote() {
        return Robot.intake.hasGamePiece();
    }

    public static boolean shooterHasNote() {
        return Robot.shooter.hasGamePiece();
    }

    public static boolean shooterDoesntHasNote() {
        return !Robot.shooter.hasGamePiece();
    }
}
