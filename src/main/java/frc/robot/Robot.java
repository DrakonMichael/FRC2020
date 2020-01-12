/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  private Command testCommand;
  private RobotContainer rContainer.

  @Override
  public void robotInit() {
    PDP.clearStickyFaults();

    // Set up input map
    inputMap.put("leftX", 0);
    inputMap.put("leftY", 1);
    inputMap.put("rightX", 4);
    inputMap.put("rightY", 5);

    // Set up networktables
    NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    NetworkTable table = tableInstance.getTable("datatable");
    xOffsetData = table.getEntry("X");
    System.out.println("\n---ROBOT INITIATED---\n\n Begin Log:\n\n\n");
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    System.out.println("\n---SETTING UP AUTONOMOUS---\n");
    // START CODE HERE
    m_timer.reset();
    m_timer.start();
    // END CODE HERE
    System.out.println("\n---AUTONOMOUS INITIATED---\n");
  }

  @Override
  public void autonomousPeriodic() {

    Number xOffset = xOffsetData.getNumber(0.0);

    Double leftMotorValue = 0.0;
    Double rightMotorValue = 0.0;
    Double minThreshhold = 0.5;
    Double MotorSpeedCap = 0.10;

    leftMotorValue = xOffset.doubleValue() * MotorSpeedCap;
    rightMotorValue = xOffset.doubleValue() * MotorSpeedCap;

    if (Math.abs(leftMotorValue) < minThreshhold) {
      leftMotorValue = 0.0;
    }

    if (Math.abs(rightMotorValue) < minThreshhold) {
      rightMotorValue = 0.0;
    }

    // setMotors(leftMotorValue, rightMotorValue);
    System.out.println(
        "\n\n------\nxOffset: " + xOffset + "\nleftMotors: " + leftMotorValue + "\nrightMotors: " + rightMotorValue);
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    System.out.println("\n---SETTING UP TELEOP---\n");
    // START CODE HERE

    System.out.print("Enabling Talon Motor(s) and setting them to 0...\n");
    setMotors(0.0, 0.0);
    System.out.print("Done. \n");

    m_timer.reset();
    m_timer.start();
    eStop = false;

    // END CODE HERE
    System.out.println("\n---TELEOP INITIATED---\n");
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {

    if (driverControlJoystick.getRawButtonPressed(2) == true) {
      System.out
          .println("\n\n\nWARNING - Emergency stop button pressed. Disable and Re-enable teleop to enable driving.");
      eStop = true;
    }

    if (eStop == false) {
      // Dual Joystick system --
      Double leftJoystickValue = driverControlJoystick.getRawAxis(inputMap.get("leftY"));
      Double rightJoystickValue = driverControlJoystick.getRawAxis(inputMap.get("rightY"));

      setMotors(leftJoystickValue * -0.5, rightJoystickValue * -0.5);
      // both motors to be the joystick

      // Print some logs
      System.out.println("\nleftMotor: " + RoundNumber(leftJoystickValue * -100) + "%  ("
          + leftMotorMain.getTemperature() + ")" + "\nrightMotor: " + RoundNumber(rightJoystickValue * -100) + "%  ("
          + leftMotorMain.getTemperature() + ")");

    } else {
      setMotors(0.0, 0.0);
    }
  }

  /**
   * This function is called periodically during test mode.
   */

  @Override
  public void disabledInit() {
    System.out.println("Disabling all motors...");
    setMotors(0.0, 0.0);
    System.out.println("Done. \n");

    System.out.println("\n---ROBOT DISABLED---\n");
    // Final Log
    System.out.println("\nSummary:\nTotal Runtime: " + RoundNumber(m_timer.get()) + "s");
  }
}
