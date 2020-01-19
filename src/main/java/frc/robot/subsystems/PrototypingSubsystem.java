package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class PrototypingSubsystem extends SubsystemBase {

    private int prototypeMotorID = 0;
    private CANSparkMax prototypeMotor;
    private CANEncoder encoder;

    public PrototypingSubsystem(Integer prototypeMotorid) {
        prototypeMotorID = prototypeMotorid;
        prototypeMotor = new CANSparkMax(21, MotorType.kBrushless);
        encoder = new CANEncoder(prototypeMotor);
        System.out.println(prototypeMotor.clearFaults());
    }

    public void setMotorSpeed(Double speed) {

        prototypeMotor.set(speed);
    }

    public Double getMotorVoltage() {
        return prototypeMotor.getBusVoltage();
    }

    public Double getMotorTemp() {
        return prototypeMotor.getMotorTemperature();
    }

    public Double getvelocity() {
        return encoder.getVelocity();
    }

}