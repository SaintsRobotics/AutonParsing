package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 * A mock version of the Command
 */
public class SwerveParsableCommand extends ParsableCommand {

    private SwerveDrivetrain m_drivetrain;

    private Pose2d targetPosition;
    private double xSpeed;
    private double ySpeed;
    private double rotSpeed;

    private PIDController m_xPID;
    private PIDController m_yPID;
    private PIDController m_turningPID;

    private Constants m_constants;

    private Pose2d m_currPose = new Pose2d(); // default, use odom later

    public SwerveParsableCommand() {

    }

    public SwerveParsableCommand(String params) { // 3,3,30
        parse(params);
        m_constants = new Constants();

        m_xPID = new PIDController(Math.toRadians((m_constants.maxMetersPerSecond / 180) * 5), 0, 0);
        m_yPID = new PIDController(Math.toRadians((m_constants.maxMetersPerSecond / 180) * 5), 0, 0);
        m_turningPID = new PIDController(Math.toRadians((m_constants.maxMetersPerSecond / 180) * 5), 0, 0);
        m_xPID.enableContinuousInput(0, Math.PI * 2);
        m_xPID.setTolerance(1 / 36); 
        m_yPID.enableContinuousInput(0, Math.PI * 2);
        m_yPID.setTolerance(1 / 36); 
        m_turningPID.enableContinuousInput(0, Math.PI * 2);
        m_turningPID.setTolerance(1 / 36); 
        m_drivetrain = new SwerveDrivetrain(m_constants);
    }

    public void parse (String params) { // parameter values separated by ", "
        params.strip();
        String[] arr = params.split(", "); 
        double xPos = Double.parseDouble(arr[0]);
        double yPos = Double.parseDouble(arr[1]);
        double rot = Double.parseDouble(arr[2]);

        targetPosition = new Pose2d(xPos, yPos, new Rotation2d(rot));
    }
    
    @Override
    public void initialize() {        
    }

    @Override
    public void execute() {
        m_turningPID.setSetpoint(targetPosition.getRotation().getRadians());
        m_xPID.setSetpoint(targetPosition.getTranslation().getX());
        m_yPID.setSetpoint(targetPosition.getTranslation().getY());


        rotSpeed = m_turningPID.calculate(m_currPose.getRotation().getRadians());
        xSpeed = m_xPID.calculate(m_currPose.getTranslation().getX());
        ySpeed = m_yPID.calculate(m_currPose.getTranslation().getY());


        m_drivetrain.move(xSpeed, ySpeed, rotSpeed, false);
    }
    
    public boolean isFinished(){
        return false;
    }
}