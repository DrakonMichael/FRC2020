/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj.TimedRobot;

import static frc.robot.Constants.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  private CommandBase driveCommand;
  private CommandBase autonomousCommand;
  private CommandBase prototypeCommand;

  private RobotContainer rContainer;
  private PowerDistributionPanel PDP = new PowerDistributionPanel(00);

  @Override
  public void robotInit() {
    PDP.clearStickyFaults();
    System.out.println("Robot Initiated:");
    rContainer = new RobotContainer();
    driveCommand = RobotContainer.getDriveCommand();
    autonomousCommand = RobotContainer.getAutonomousCommand();
    prototypeCommand = RobotContainer.getPrototypeCommand();
  }

  @Override
  public void teleopInit() {
    if (driveCommand == null) {
      driveCommand.schedule();
    }

    if (prototypeCommand == null) {
      prototypeCommand.schedule();
    }

    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
  public void autonomousInit() {
    if (autonomousCommand == null) {
      autonomousCommand.schedule();
    }

    if (driveCommand != null) {
      driveCommand.cancel();
    }

    if (prototypeCommand != null) {
      prototypeCommand.cancel();
    }
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    if (driveCommand != null) {
      driveCommand.cancel();
    }
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    System.out.println("\n\n\n\n\n\n\n\n\nRobot Disabled");
  }
}
