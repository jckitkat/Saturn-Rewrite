package frc.robot.commands.modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.SetMode;
import frc.robot.commands.subsystemControl.intake.SetIntakePosition;
import frc.robot.commands.subsystemControl.shooter.SetFeedSpeed;
import frc.robot.commands.subsystemControl.shooter.SetFlywheelSpeed;
import frc.robot.commands.subsystemControl.shooter.SetShooterPosition;
import frc.robot.utils.Constants;
import frc.robot.utils.Modes;
import frc.robot.utils.TriggerBoard;

public class PrepareShoot extends SequentialCommandGroup {

   public PrepareShoot() {
       addCommands(
               new SetIntakePosition(Constants.Intake.groundPosition),
               new SetShooterPosition(Constants.Shooter.stowPosition),
               new SetFeedSpeed(-0.5),
               new WaitUntilCommand(TriggerBoard::shooterDoesntHasNote),
               new SetFlywheelSpeed(90),
               new SetMode(Modes.shotReady)
       );
   }

}
