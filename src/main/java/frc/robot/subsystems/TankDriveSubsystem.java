package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TankDriveSubsystem extends SubsystemBase {

    private int leftMotorID = 00;
    private int rightMotorID = 00;

    public TankDriveSubsystem(Integer leftMotor, Integer rightMotor) {
        leftMotorID = leftMotor;
        rightMotorID = rightMotor;
    }

    TalonSRX leftMotorMain = new TalonSRX(leftMotorID);
    TalonSRX rightMotorMain = new TalonSRX(rightMotorID);
    TalonSRX leftMotorSlave = new TalonSRX(leftMotorID + 1);
    TalonSRX rightMotorSlave = new TalonSRX(rightMotorID + 1);

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

}