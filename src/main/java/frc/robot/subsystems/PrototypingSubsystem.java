package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;

public class PrototypingSubsystem extends SubsystemBase {

    private NetworkTableEntry ProportionalData = Shuffleboard.getTab("Prototyping").add("Proportinonal", 0.0)
            .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 0.1)).getEntry();
    private NetworkTableEntry IntegralData = Shuffleboard.getTab("Prototyping").add("Integral", 0.0)
            .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 0.1)).getEntry();
    private NetworkTableEntry DerivativeData = Shuffleboard.getTab("Prototyping").add("Derivative", 0.0)
            .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 0.1)).getEntry();

    private int prototypeMotorID = 21;
    private TalonFX prototypeMotor;
    private TalonFXSensorCollection sensorCollection;

    private Double proportional = 0.0;
    private Double derivative = 0.0;
    private Double integral = 0.0;
    private Double maxMotorSpeed = 6500.0;

    private PIDController pidLoop = new PIDController(proportional, integral, derivative);

    @Override
    public void periodic() {
        proportional = ProportionalData.getDouble(0.0);
        integral = IntegralData.getDouble(0.0);
        derivative = DerivativeData.getDouble(0.0);

        pidLoop.setPID(proportional, integral, derivative);

    }

    public PrototypingSubsystem(Integer prototypeMotorid, Integer analogOutputPin) {
        prototypeMotorID = prototypeMotorid;
        prototypeMotor = new TalonFX(prototypeMotorid);
        sensorCollection = new TalonFXSensorCollection(prototypeMotor);

        System.out.println(prototypeMotor.clearStickyFaults());
    }

    public void setMotorSpeed(Double speed) {
        prototypeMotor.set(ControlMode.PercentOutput, pidLoop.calculate(getvelocity() / maxMotorSpeed, speed));
        System.out.println(pidLoop.calculate(getvelocity() / maxMotorSpeed, speed));
    }

    public Double getMotorVoltage() {
        return prototypeMotor.getBusVoltage();
    }

    public Double getMotorTemp() {
        return prototypeMotor.getTemperature();
    }

    public Double getvelocity() {
        return (sensorCollection.getIntegratedSensorVelocity() / 2048) * 600;
    }

}