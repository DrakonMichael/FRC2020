package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class PrototypingSubsystem extends SubsystemBase {

    private int prototypeMotorID = 0;
    private Double motorSpeed;

    public PrototypingSubsystem(Integer prototypeMotor) {
        prototypeMotorID = prototypeMotor;
    }

    private TalonSRX prototypeMotor = new TalonSRX(prototypeMotorID);

    public void setMotorSpeed(Double speed) {
        prototypeMotor.set(ControlMode.PercentOutput, speed);
        motorSpeed = speed;
    }

    public Double getMotorVoltage() {
        return prototypeMotor.getBusVoltage();
    }

    public Double getMotorControllerTemperature() {
        return prototypeMotor.getTemperature();
    }

}