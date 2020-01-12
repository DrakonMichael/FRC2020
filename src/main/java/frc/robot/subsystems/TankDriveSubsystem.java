import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TankDriveSubsystem extends Subsystem {

    private int leftMotorID = 05;
    private int rightMotorID = 07;

    TalonSRX leftMotorMain = new TalonSRX(leftMotorID);
    TalonSRX rightMotorMain = new TalonSRX(rightMotorID);
    TalonSRX leftMotorSlave = new TalonSRX(leftMotorID + 1);
    TalonSRX rightMotorSlave = new TalonSRX(rightMotorID + 1);

    @Override
    protected void initDefaultCommand() {

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

}