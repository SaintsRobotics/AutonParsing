package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/



/**
 * A mock version of the  Command
 */
public class SetCommand extends ParsableCommand {
    String a;
    
    public SetCommand() {

    }

    public ParsableCommand setA(String input) {
        a = input;
        return this;
    }

    @Override
    public void initialize() {
        SmartDashboard.putString(a, a);
    }

    @Override
    public void execute() {

    }

}