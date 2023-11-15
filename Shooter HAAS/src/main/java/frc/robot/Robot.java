// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Shooter.shooterStates;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  final private CommandXboxController controller = new CommandXboxController(Constants.DRIVER_JOYSTICK_PORT);
  private final Shooter m_Shooter;
  private RobotContainer m_robotContainer;

  public Robot() {
    m_Shooter = new Shooter(0, 1, 2, 3, 4);

  }

  private void configureBindings() {
    Shooter shooter = new Shooter(1, 2, 3, 4, 5);
    controller.rightTrigger().onTrue(new InstantCommand(() -> shooter.shoot()));
    controller.rightBumper().onTrue(new InstantCommand(() -> shooter.flyWheeleStop()));

    controller.a().onTrue(new InstantCommand(() -> shooter.setshooterState(shooterStates.SHOOTER_HIGH)));
    controller.x().onTrue(new InstantCommand(() -> shooter.setshooterState(shooterStates.SHOOTER_MID)));
    controller.y().onTrue(new InstantCommand(() -> shooter.setshooterState(shooterStates.SHOOTER_LOW)));
    controller.b().onTrue(new InstantCommand(() -> shooter.setshooterState(shooterStates.SHOOTER_RETRACTED)));
    // controller.rightTrigger().onTrue(new InstantCommand(m_Shooter::shoot));
    // controller.b().onTrue(new InstantCommand(m_Shooter::flyWheeleStop));
    shooter.getCurrentCommand();
  }

  @Override
  public void robotInit() {
    configureBindings();
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    SmartDashboard.putString("Command",
        m_Shooter.getCurrentCommand() != null ? m_Shooter.getCurrentCommand().toString() : "null");
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void autonomousExit() {
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void testExit() {
  }
}
