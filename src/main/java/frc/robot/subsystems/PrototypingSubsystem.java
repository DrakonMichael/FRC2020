package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class PrototypingSubsystem extends SubsystemBase {

    private int prototypeMotorID = 0;

    public PrototypingSubsystem(Integer prototypeMotor) {
        prototypeMotorID = prototypeMotor;
    }

    private CANSparkMax prototypeMotor = new CANSparkMax(prototypeMotorID, MotorType.kBrushless);

    public void setMotorSpeed(Double speed) {
        prototypeMotor.set(speed);
    }

}