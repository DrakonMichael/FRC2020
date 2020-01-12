/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  // VARIABLE INSTANTIATION

  private boolean eStop = false;
  private int JoystickPort = 0;
  private int leftMotorID = 05;
  private int rightMotorID = 07;

  // OBJECT INSTANTIATION

  TalonSRX leftMotorMain = new TalonSRX(leftMotorID);
  TalonSRX rightMotorMain = new TalonSRX(rightMotorID);
  TalonSRX leftMotorSlave = new TalonSRX(leftMotorID + 1);
  TalonSRX rightMotorSlave = new TalonSRX(rightMotorID + 1);
  private PowerDistributionPanel PDP = new PowerDistributionPanel(00);
  private final Timer m_timer = new Timer();
  private Joystick driverControlJoystick = new Joystick(JoystickPort);
  Map<String, Integer> inputMap = new HashMap<String, Integer>();

  NetworkTableEntry xOffsetData;

  // HELPER FUNCTIONS

  public Double RoundNumber(Double Num) { // Reduce number Num to 3 decimal places
    DecimalFormat df = new DecimalFormat("#.###");
    return Double.parseDouble(df.format(Num));
  }

  public void setMotors(Double leftSpeed, Double rightSpeed) {
    if (leftSpeed > 1.0) {
      leftSpeed = 1.0;
    }
    if (rightSpeed > 1.0) {
      rightSpeed = 1.0;
    }
    leftMotorMain.set(ControlMode.PercentOutput, leftSpeed);
    leftMotorSlave.set(ControlMode.PercentOutput, leftSpeed);
    rightMotorMain.set(ControlMode.PercentOutput, rightSpeed);
    rightMotorSlave.set(ControlMode.PercentOutput, -rightSpeed);
  }

  // ROBOT CODE

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

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
    NetworkTable table = tableInstance.getTable("8029data");
    xOffsetData = table.getEntry("x");
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

    Double xOffset = xOffsetData.getNumber(125.0).doubleValue() / 125.0;
    xOffset -= 1;

    Double leftMotorValue = 0.0;
    Double rightMotorValue = 0.0;
    Double minThreshhold = 0.00;
    Double MotorSpeedCap = 0.20;

    leftMotorValue = xOffset * MotorSpeedCap * -1;
    rightMotorValue = xOffset * MotorSpeedCap * 1;

    if (Math.abs(leftMotorValue) < minThreshhold) {
      leftMotorValue = 0.0;
    }

    if (Math.abs(rightMotorValue) < minThreshhold) {
      rightMotorValue = 0.0;
    }

    setMotors(leftMotorValue, rightMotorValue);

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
