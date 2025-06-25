package frc.robot.utils;

import java.io.File;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import swervelib.math.SwerveMath;

public class Constants {

    public static class FieldLocations {
        public static final Translation2d none = new Translation2d(0, 0);
        public static final Translation2d blueSpeaker = new Translation2d(0, 5.5);
        public static final Translation2d redSpeaker = new Translation2d(16.5, 5.5);
        public static final Translation2d bluePassZone = new Translation2d();
        public static final Translation2d redPassZone = new Translation2d();
    }

    public static class IO {
        public static final double swerveDeadband = 0.04;
        public static final double elevatorDeadband = 0.04;
        public static final double climberDeadband = 0.05;
        public static final int driveXAxis = 1;
        public static final int driveYAxis = 0;
        public static final int driveOmegaAxis = 4;
        public static final int resetGyroButton = 2;

        public static final int driveModeButton = 7;

        public static final int intakeButton = 6;
        public static final int cancelIntakeButton = 4;
        public static final int prepareSpeakerButton = 5;
        public static final int safeShootPOV = 180;
        public static final int prepareAmpButton = 8;
        public static final int shootButton = 7;
        public static final int resetSuperstructureButton = 10;

    }

    public static class Swerve {
        public static final File directory = new File(Filesystem.getDeployDirectory(), "swerve");
        public static final double maxVelocity = 50;
        public static final double driveConversionFactor = SwerveMath
                .calculateMetersPerRotation(Units.inchesToMeters(4), 6.12, 1);
        public static final double angleConversionFactor = SwerveMath
                .calculateDegreesPerSteeringRotation(21.4285714285714, 1);
    }

    public static class Intake {
        public static final int leftPivotID = 26;
        public static final int rightPivotID = 25;
        public static final int rollerCANID = 24;

        // public static final double conversionFactor = 0.19047619;
        public static final double conversionFactor = 23.3333333333333;
        public static final double absoluteConversionFactor = -1.0;
        public static final double absoluteOffset = 0.437;
        // public static final double absoluteOffset = 0.0;

        public static final double positionP = 45;
        public static final double positionI = 0;
        public static final double positionD = 0;
        public static final double positionG = 0.32;
        public static final double positionV = 0;
        public static final double positionS = 0.08;
        public static final double maxVel = 20;
        public static final double maxAccel = 50;
        public static final double openLoopRampRate = .5;

        public static final double defaultPosition = 1;

        public static final double sensorThreshhold = 50;

        public static final double pivotTolerance = 0.01;

        public static final double topPosition = 0.21;
    }

    public static class Elevator {
        public static final int motorID = 15;
        public static final double conversionFactor = 1;
        public static final double positionP = 0.1;
        public static final double positionI = 0;
        public static final double positionD = 0;
        public static final double positionG = 0;
        public static final double positionV = 1;
        public static final double positionS = 0;
        public static final double maxVel = 3;
        public static final double maxAccel = 3;

        public static final double defaultPosition = 1;
    }

    public static class Shooter {
        public static final int feederCANID = 21;
        public static final int topCANID = 23;
        public static final int bottomCANID = 22;
        public static final int positionCANID = 20;

        public static final double topP = 0;
        public static final double topI = 0;
        public static final double topD = 0;
        public static final double topFF = 0.177;

        public static final double bottomP = 0.0;
        public static final double bottomI = 0;
        public static final double bottomD = 0;
        public static final double bottomFF = 0.117;

        // public static final double positionP = 0.004;
        public static final double positionP = 20;
        public static final double positionI = 0;
        public static final double positionD = 0;
        public static final double positionG = 0.31;
        public static final double positionV = 1.4;
        // public static final double positionV = 0.0;
        public static final double positionS = 0;
        public static final double conversionFactor = 44.1176471;
        public static final double maxVel = 30;
        public static final double maxAcc = 60;
        // public static final double absoluteOffset = 0.49;
        public static final double absoluteOffset = -0.827;
        public static final double absoluteConversionFactor = 1.0;

        public static final double feederP = 0;
        public static final double feederI = 0;
        public static final double feederD = 0;
        public static final double feederFF = 0;

        public static final double sensorThreshhold = 33;

        public static final double topMaxVel = 100;
        public static final double topMaxAccel = 200;

        public static final double bottomMaxVel = 100;
        public static final double bottomMaxAccel = 200;

        public static final double feederMaxVel = 100;
        public static final double feederMaxAccel = 200;

        public static final double positionTolerance = 0.02;

        public static final double stowPosition = 0;
    }

    public static class Sensors {
        public static final Transform3d cameraToRobot = new Transform3d(new Translation3d(-.298, 0, -.205),
                new Rotation3d(0.0, 45.0, 0.0));

        public static final double encoderTolerance = 0.0005;
    }

    public static class Climber {
        public static final int leaderID = 20;
        public static final int followerID = 21;

        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double conversionfactor = 1;
        public static final double maxVel = 1;
        public static final double maxAccel = 1;
    }

    public static final boolean defaultControlMode = false;
}
