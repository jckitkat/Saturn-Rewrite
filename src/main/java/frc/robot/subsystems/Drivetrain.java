package frc.robot.subsystems;

// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.path.PathConstraints;
// import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
// import com.pathplanner.lib.util.PIDConstants;
// import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.Constants;
import swervelib.SwerveController;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance = null;
    private final SwerveDrive swerveDrive;
    private final AddressableLED leds = new AddressableLED(0);
    private final AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(48);

    public final double maxSpeed = Constants.Swerve.maxVelocity;
    // public final double maxSpeed = 1;
    private final double driveConversionFactor = Constants.Swerve.driveConversionFactor;
    private final double angleConversionFactor = Constants.Swerve.angleConversionFactor;

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }

        return instance;
    }

    public Drivetrain() {
        System.out.println("\"conversionFactor\": {");
        System.out.println("\t\"angle\": " + angleConversionFactor + ",");
        System.out.println("\t\"drive\": " + driveConversionFactor);
        System.out.println("}");

        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.LOW;

        try {
            swerveDrive = new SwerveParser(Constants.Swerve.directory).createSwerveDrive(maxSpeed,
                    angleConversionFactor, driveConversionFactor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // setupPathPlanner();

        leds.setLength(ledBuffer.getLength());
        for (int i = 0; i < 48; i++) {
            ledBuffer.setRGB(i, 0, 255, 0);
        }
        leds.setData(ledBuffer);
        leds.start();
    }

    @Override
    public void periodic() {
    }

    public Command aimChassis(Translation2d target) {
        double faceLocationHeading = Math.atan2(
                target.getX() - getPose().getX(),
                target.getY() - getPose().getY());
        return new RunCommand(() -> driveFieldOriented(getTargetSpeeds(
                swerveDrive.getFieldVelocity().vxMetersPerSecond,
                swerveDrive.getFieldVelocity().vyMetersPerSecond,
                Math.sin(faceLocationHeading),
                Math.cos(faceLocationHeading))));
    }

    public void setHeadingCorrection(boolean headingCorrection) {
        swerveDrive.setHeadingCorrection(headingCorrection);
    }

    // public Command driveToPose(Pose2d pose)
    // {
    // // Create the constraints to use while pathfinding
    // PathConstraints constraints = new PathConstraints(
    // swerveDrive.getMaximumVelocity(), 4.0,
    // swerveDrive.getMaximumAngularVelocity(), Units.degreesToRadians(720));

    // // Since AutoBuilder is configured, we can use it to build pathfinding
    // commands
    // return AutoBuilder.pathfindToPose(
    // pose,
    // constraints,
    // 0.0, // Goal end velocity in meters/sec
    // 0.0 // Rotation delay distance in meters. This is how far the robot should
    // travel before attempting to rotate.
    // );
    // }

    /**
     * The primary method for controlling the drivebase. Takes a
     * {@link Translation2d} and a rotation rate, and
     * calculates and commands module states accordingly. Can use either open-loop
     * or closed-loop velocity control for
     * the wheel velocities. Also has field- and robot-relative modes, which affect
     * how the translation vector is used.
     *
     * @param translation   {@link Translation2d} that is the commanded linear
     *                      velocity of the robot, in meters per
     *                      second. In robot-relative mode, positive x is torwards
     *                      the bow (front) and positive y is
     *                      torwards port (left). In field-relative mode, positive x
     *                      is away from the alliance wall
     *                      (field North) and positive y is torwards the left wall
     *                      when looking through the driver station
     *                      glass (field West).
     * @param rotation      Robot angular rate, in radians per second. CCW positive.
     *                      Unaffected by field/robot
     *                      relativity.
     * @param fieldRelative Drive mode. True for field-relative, false for
     *                      robot-relative.
     */
    public void drive(Translation2d translation, double rotation, boolean fieldRelative) {
        swerveDrive.drive(translation, rotation, fieldRelative, false);
    }

    /**
     * Drive the robot given a chassis field oriented velocity.
     *
     * @param velocity Velocity according to the field.
     */
    public void driveFieldOriented(ChassisSpeeds chassisSpeeds) {
        swerveDrive.driveFieldOriented(chassisSpeeds);
    }

    /**
     * Drive according to the chassis robot oriented velocity.
     *
     * @param velocity Robot oriented {@link ChassisSpeeds}
     */
    public void driveRobotOriented(ChassisSpeeds chassisSpeeds) {
        swerveDrive.drive(chassisSpeeds);
    }

    /**
     * Resets odometry to the given pose. Gyro angle and module positions do not
     * need to be reset when calling this
     * method. However, if either gyro angle or module position is reset, this must
     * be called in order for odometry to
     * keep working.
     *
     * @param initialHolonomicPose The pose to set the odometry to
     */
    public void resetOdometry(Pose2d initialHolonomicPose) {
        swerveDrive.resetOdometry(initialHolonomicPose);
    }

    /**
     * Gets the current pose (position and rotation) of the robot, as reported by
     * odometry.
     *
     * @return The robot's pose
     */
    public Pose2d getPose() {
        return swerveDrive.getPose();
    }

    /**
     * Resets the gyro angle to zero and resets odometry to the same position, but
     * facing toward 0.
     */
    public void zeroGyro() {
        swerveDrive.zeroGyro();
    }

    /**
     * Gets the current yaw angle of the robot, as reported by the imu. CCW
     * positive, not wrapped.
     *
     * @return The yaw angle
     */
    public Rotation2d getHeading() {
        return swerveDrive.getOdometryHeading();
    }

    /**
     * Get the chassis speeds based on controller input of 2 joysticks. One for
     * speeds in which direction. The other for
     * the angle of the robot.
     *
     * @param xInput   X joystick input for the robot to move in the X direction.
     * @param yInput   Y joystick input for the robot to move in the Y direction.
     * @param headingX X joystick which controls the angle of the robot.
     * @param headingY Y joystick which controls the angle of the robot.
     * @return {@link ChassisSpeeds} which can be sent to th Swerve Drive.
     */
    public ChassisSpeeds getTargetSpeeds(double xInput, double yInput, double headingX, double headingY) {
        return swerveDrive.swerveController.getTargetSpeeds(xInput, yInput, headingX, headingY,
                getHeading().getRadians(), Constants.Swerve.maxVelocity);
    }

    public ChassisSpeeds getTargetSpeeds(double xInput, double yInput, Rotation2d heading) {
        return swerveDrive.swerveController.getTargetSpeeds(yInput, xInput, heading.getRadians(),
                getHeading().getRadians(), Constants.Swerve.maxVelocity);
    }

    /**
     * Gets the current field-relative velocity (x, y and omega) of the robot
     *
     * @return A ChassisSpeeds object of the current field-relative velocity
     */
    public ChassisSpeeds getFieldVelocity() {
        return swerveDrive.getFieldVelocity();
    }

    /**
     * Gets the current velocity (x, y and omega) of the robot
     *
     * @return A {@link ChassisSpeeds} object of the current velocity
     */
    public ChassisSpeeds getRobotVelocity() {
        return swerveDrive.getRobotVelocity();
    }

    /**
     * Get the {@link SwerveController} in the swerve drive.
     *
     * @return {@link SwerveController} from the {@link SwerveDrive}.
     */
    public SwerveController getSwerveController() {
        return swerveDrive.getSwerveController();
    }

    public void addVisionMeasurement(Pose3d measurement) {
        swerveDrive.addVisionMeasurement(measurement.toPose2d(), Timer.getFPGATimestamp());
        Pose2d newOdometry = new Pose2d(swerveDrive.getPose().getTranslation().getX(),
                swerveDrive.getPose().getTranslation().getY(), measurement.getRotation().toRotation2d());
        swerveDrive.setGyroOffset(new Rotation3d(0, 0, measurement.getRotation().toRotation2d().getRadians()));
        swerveDrive.resetOdometry(newOdometry);
    }

    /**
     * Lock the swerve drive to prevent it from moving.
     */
    public void lock() {
        swerveDrive.lockPose();
    }
}
