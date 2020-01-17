/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.PrototypingSubsystem;

/**
 * An example command that uses an example subsystem.
 */
public class prototypingCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final PrototypingSubsystem m_subsystem;
    private NetworkTableEntry speedIndicator = Shuffleboard.getTab("Prototyping").add("Prototype motor speed", 0.0)
            .getEntry();
    private NetworkTableEntry voltageIndicator = Shuffleboard.getTab("Prototyping").add("Motor Voltage", 0.0)
            .getEntry();

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public prototypingCommand(PrototypingSubsystem subsystem) {
        m_subsystem = subsystem;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Prototyping device");
        m_subsystem.setMotorSpeed(0.0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Double speedCap = 1.0;
        Double speed = -speedCap * RobotContainer.driverControlJoystick.getRawAxis(3);
        speed = (speed + 1) / 2.0;
        speedIndicator.setDouble(speed * 100);
        voltageIndicator.setDouble(m_subsystem.getMotorVoltage());
        m_subsystem.setMotorSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.setMotorSpeed(0.0);
        if (interrupted) {
            System.out.println("DriveCommand Ended: Interrupted");
        } else {
            System.out.println("DriveCommand Ended: Uninterrupted");
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
