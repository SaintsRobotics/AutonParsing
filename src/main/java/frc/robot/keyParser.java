/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.fasterxml.jackson.databind.SequenceWriter;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.SetCommand;
import frc.robot.commands.TestCommand;

/**
 * Do NOT add any static variables to this class, or any initialization at all. Unless you know what
 * you are doing, do not modify this file except to change the parameter class to the startRobot
 * call.
 */
public class keyParser {
    private static SequentialCommandGroup group;
    private static String[] keyArray = {"test@t", "set@s"};

    public keyParser() {

    }

    private static String[] splitString (String key) {
        // assuming delimiter is @        
        return key.split("@");        
    }

    public static SequentialCommandGroup parse() {
        for (String key : keyArray) {
            String[] arr = splitString(key);
            String ID = arr[0];
            String params = arr[1];
            if (ID.equals("test")) {            
                group.addCommands(new TestCommand(params));
            } else if (ID.equals("set")) {
                group.addCommands(new SetCommand(params));
            }   
            // continue chaining if-else statements for each key 
            // bad code - fix later if time       
        }
        return group;        
    }
}

