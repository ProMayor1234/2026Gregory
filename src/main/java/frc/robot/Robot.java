// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final WPI_TalonSRX frontl_drive = new WPI_TalonSRX(11);
  private final WPI_TalonSRX frontr_drive = new WPI_TalonSRX(12);
  private final WPI_TalonSRX backl_drive = new WPI_TalonSRX(13);
  private final WPI_TalonSRX backr_drive = new WPI_TalonSRX(14);
  private final SparkMax top = new SparkMax(45, MotorType.kBrushless);
  private final SparkMax bottom = new SparkMax(44, MotorType.kBrushless);
  private final SparkMax indexer = new SparkMax(43, MotorType.kBrushless);
  private final SparkMaxConfig config = new SparkMaxConfig();
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(frontl_drive, frontr_drive);
  private final PS5Controller m_controller = new PS5Controller(0);
  private final Timer m_timer = new Timer();


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    backl_drive.follow(frontl_drive);
    backr_drive.follow(frontr_drive);

    frontl_drive.setNeutralMode(NeutralMode.Brake);
    frontr_drive.setNeutralMode(NeutralMode.Brake);
    backl_drive.setNeutralMode(NeutralMode.Brake);
    backr_drive.setNeutralMode(NeutralMode.Brake);
/*
    top.setIdleMode(IdleMode.kCoast);
    bottom.setIdleMode(IdleMode.kCoast);*/
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      // m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(m_controller.getRightX(), m_controller.getLeftY());
    top.set(0);
    bottom.set(0);
    indexer.set(0);
    // if (m_controller.getTriangleButton()) {
    //   top.set(1);
    // }
    if (m_controller.getCircleButton()) {
      bottom.set(0);
      top.set(0);
    }
    // if (m_controller.getSquareButton()) {
    //   bottom.set(1);
    // }
    if (m_controller.getCrossButton()) {
      indexer.set(-1);
    }
    if (m_controller.getTriangleButton()) {                                     
      top.set(-.7);
      bottom.set(0.7);
      indexer.set(-1);
    }
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}