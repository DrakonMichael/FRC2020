/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.TankDriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * An example command that uses an example subsystem.
 */
public class DriveCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final TankDriveSubsystem m_subsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public DriveCommand(TankDriveSubsystem subsystem) {
        m_subsystem = subsystem;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Drive initialized");
        m_subsystem.setMotors(0.0, 0.0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Double speedCap = 0.5;

        Double leftPower = speedCap * RobotContainer.driverControlJoystick.getRawAxis(1);
        Double rightPower = speedCap * RobotContainer.driverControlJoystick.getRawAxis(5);
        m_subsystem.setMotors(leftPower, rightPower);

        m_subsystem.setIntake(RobotContainer.driverControlJoystick.getRawAxis(3));
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.setMotors(0.0, 0.0);
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
