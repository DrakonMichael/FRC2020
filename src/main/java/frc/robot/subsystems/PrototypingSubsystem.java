package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;

public class PrototypingSubsystem extends SubsystemBase {

    private int prototypeMotorID = 0;
    private CANSparkMax prototypeMotor;
    private CANEncoder encoder;

    private I2C.Port i2cport = I2C.Port.kOnboard;
    private ColorSensorV3 colorsensor = new ColorSensorV3(i2cport);

    public PrototypingSubsystem(Integer prototypeMotorid, Integer analogOutputPin) {
        prototypeMotorID = prototypeMotorid;
        prototypeMotor = new CANSparkMax(21, MotorType.kBrushless);
        encoder = new CANEncoder(prototypeMotor);

        System.out.println(prototypeMotor.clearFaults());
    }

    public void setMotorSpeed(Double speed) {

        prototypeMotor.set(speed);
    }

    public Color getcolor() {
        return colorsensor.getColor();
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