/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.VisionCommand;
import frc.robot.commands.prototypingCommand;
import frc.robot.subsystems.PrototypingSubsystem;
import frc.robot.subsystems.TankDriveSubsystem;
import java.util.HashMap;
import java.util.Map;


/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems
    private static final TankDriveSubsystem r_tankDriveSubsystem = new TankDriveSubsystem(5, 7);
    private static final PrototypingSubsystem r_prototypingSubsystem = new PrototypingSubsystem(0);

    // The robot's commands
    private static final DriveCommand driveCommand = new DriveCommand(r_tankDriveSubsystem);
    private static final VisionCommand autonomousCommand = new VisionCommand(r_tankDriveSubsystem);
    private static final prototypingCommand prototypeCommand = new prototypingCommand(r_prototypingSubsystem);

    public static Joystick driverControlJoystick = new Joystick(0);

    public Map<String, Integer> joyMap = new HashMap<String, Integer>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        joyMap.put("leftX", 0);
        joyMap.put("leftY", 1);
        joyMap.put("rightX", 4);
        joyMap.put("rightY", 5);
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
    }

    // get Subsystems
    public static SubsystemBase getTankDriveSubsystem() {
        return r_tankDriveSubsystem;
    }

    public static SubsystemBase getPrototypingSubsystem() {
        return r_prototypingSubsystem;
    }

    // get Commands
    public static CommandBase getDriveCommand() {
        return driveCommand;
    }

    public static CommandBase getAutonomousCommand() {
        return autonomousCommand;
    }

    public static CommandBase getPrototypeCommand() {
        return prototypeCommand;
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */

}
