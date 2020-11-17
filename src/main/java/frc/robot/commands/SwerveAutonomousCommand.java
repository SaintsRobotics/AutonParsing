/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

/**
 * A mock version of the Command
 */
public class SwerveAutonomousCommand extends ParsableCommand {

    private Constants m_constants;

    private TrapezoidProfile.Constraints m_xConstraints;
    private ProfiledPIDController m_xPID;

    private TrapezoidProfile.Constraints m_yConstraints;
    private ProfiledPIDController m_yPID;

    private TrapezoidProfile.Constraints m_rotationConstraints;
    private ProfiledPIDController m_rotationPID;

    private SwerveDrivetrain m_drivetrain;

    private Pose2d m_targetPosition;
    private Pose2d m_currentPosition;

    private double m_xSpeed;
    private double m_ySpeed;
    private double m_rotationSpeed;

    public SwerveAutonomousCommand(String params) {
        parse(params);

        m_constants = new Constants();

        // default, use odometry later
        m_currentPosition = new Pose2d();

        m_xConstraints = new TrapezoidProfile.Constraints(0, 0);
        m_xPID = new ProfiledPIDController(Math.toRadians((m_constants.maxMetersPerSecond / 180) * 5), 0, 0,
                m_xConstraints);
        m_xPID.reset(0);
        m_xPID.enableContinuousInput(0, Math.PI * 2);
        m_xPID.setTolerance(1 / 36);
        m_xPID.setGoal(m_targetPosition.getTranslation().getX());

        m_yConstraints = new TrapezoidProfile.Constraints(0, 0);
        m_yPID = new ProfiledPIDController(Math.toRadians((m_constants.maxMetersPerSecond / 180) * 5), 0, 0,
                m_yConstraints);
        m_yPID.reset(0);
        m_yPID.enableContinuousInput(0, Math.PI * 2);
        m_yPID.setTolerance(1 / 36);
        m_yPID.setGoal(m_targetPosition.getTranslation().getY());

        m_rotationConstraints = new TrapezoidProfile.Constraints(0, 0);
        m_rotationPID = new ProfiledPIDController(Math.toRadians((m_constants.maxMetersPerSecond / 180) * 5), 0, 0,
                m_rotationConstraints);
        m_rotationPID.reset(0);
        m_rotationPID.enableContinuousInput(0, Math.PI * 2);
        m_rotationPID.setTolerance(1 / 36);
        m_rotationPID.setGoal(m_targetPosition.getRotation().getRadians());

        m_drivetrain = new SwerveDrivetrain(m_constants);
    }

    public void parse(String params) {
        String[] arr = params.split(", ");

        double targetXPosition = Double.parseDouble(arr[0]);
        double targetYPosition = Double.parseDouble(arr[1]);
        double targetRotation = Double.parseDouble(arr[2]);

        m_targetPosition = new Pose2d(targetXPosition, targetYPosition, new Rotation2d(targetRotation));
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_xSpeed = m_xPID.calculate(m_currentPosition.getTranslation().getX());
        m_ySpeed = m_yPID.calculate(m_currentPosition.getTranslation().getY());
        m_rotationSpeed = m_rotationPID.calculate(m_currentPosition.getRotation().getRadians());

        m_drivetrain.move(m_xSpeed, m_ySpeed, m_rotationSpeed, false);
    }

    public boolean isFinished() {
        return false;
    }
}