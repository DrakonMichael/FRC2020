package frc.robot.commands;

import frc.robot.subsystems.TankDriveSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionCommand extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private NetworkTableEntry xOffsetData;

    private final TankDriveSubsystem m_subsystem;

    public VisionCommand(TankDriveSubsystem subsystem) {
        m_subsystem = subsystem;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Vision initialized");
        NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
        NetworkTable networkTable = tableInstance.getTable("8029data");
        xOffsetData = networkTable.getEntry("x");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        Double xOffset = xOffsetData.getNumber(125.0).doubleValue() / 125.0;
        Double maxMotorSpeed = 0.25;

        Double leftPower = xOffset * maxMotorSpeed;
        Double rightPower = xOffset * maxMotorSpeed * -1;

        m_subsystem.setMotors(leftPower, rightPower);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Vision command ended");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}