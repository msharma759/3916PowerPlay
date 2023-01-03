/*
Apex Robotics FTC Team 3916: Basic TeleOp for Freight Frenzy season (2021-2022)

Uses a Mecanum-style drivetrain for movement.
 */

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

/**
 * FieldCentric TeleOp method, build season code on top of this.
 *
 * @author Aman Anas
 * @author Gabrian Chua
 * @author Nathan Battle
 * @author Jason Armbruster
 * @author Jude Naramor
 *
 * @version April 2022
 *
 */

// This is the main TeleOp, with full bot functionality as well as telemetry
@TeleOp(name="TeleOp with FieldCentric", group="Apex Robotics 3916")
//@Disabled
public class TeleOp_With_FieldCentric extends LinearOpMode {

    //Define our robot class
    private FTCLibRobotFunctions bot = new FTCLibRobotFunctions();

    @Override
    public void runOpMode() throws InterruptedException {

        //Initialize telemetry and dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

        //Initialize bot
        bot.initBot(hardwareMap);
        RevIMU imu = new RevIMU(hardwareMap);
        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);
        imu.init();

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        //Initialize working variables
        double x = 0;
        double y = 0;
        double z = 0;
        double g1triggers;
        double g2triggers;
        boolean bucketLift = false;
        double slidePos = 0;
        double prevSlidePos;
        boolean slideLimit;

        //Wait for the driver to hit Start
        waitForStart();


        while (opModeIsActive()) {
            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }

            //Sensor Inputs

            /*
               ////////////////////////// GAMEPAD 1 //////////////////////////
            */

            //Get stick inputs
            double leftY = Gamepad1.getLeftY();
            double leftX = Gamepad1.getLeftX();
            double rightX = Gamepad1.getRightX();
            boolean precisionMode = (Gamepad1.getButton(GamepadKeys.Button.RIGHT_BUMPER) || Gamepad1.getButton(GamepadKeys.Button.LEFT_BUMPER));
            bot.motorUpdate();
            // Rotation Axis
            if (Math.abs(rightX) > TeleOpConfig.STICK_DEAD_ZONE) {
                z = (rightX);
            } else {
                z = 0;
            }
            // Forward/Back Drive
            if (Math.abs(leftY) > TeleOpConfig.STICK_DEAD_ZONE) {
                y = (leftY);
            } else {
                y = 0;
            }
            // Left/Right Strafe
            if (Math.abs(leftX) > TeleOpConfig.STICK_DEAD_ZONE) {
                x = (leftX);
            } else {
                x = 0;
            }

            //Trigger Inputs
            g1triggers = 0;
            if (Gamepad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > TeleOpConfig.STICK_DEAD_ZONE) {
                g1triggers += Gamepad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
            }
            if (Gamepad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > TeleOpConfig.STICK_DEAD_ZONE) {
                g1triggers -= Gamepad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
            }

            // Reset Encoders
            /*if (Gamepad1.getButton(GamepadKeys.Button.X)) {
                bot.motor_backRight.encoder.reset();
                bot.motor_backLeft.encoder.reset();
                bot.motor_frontRight.encoder.reset();
                bot.motor_frontLeft.encoder.reset();
            }*/

            //Send the X, Y, and rotation (Z) to the mecanum drive method
            bot.driveFieldCentric(x, y, z, precisionMode,imu);

            // Other Motors
            //bot.runSlideMotor(g1triggers);

            // Gamepad 2 Redundancies


            /*
               ////////////////////////// GAMEPAD 2 //////////////////////////
            */

            //Get stick inputs


            //Button inputs
            //BE CAREFUL THIS DOES NOT WORK AND WILL BREAK ROBOT - sorry
            if (Gamepad2.getButton(GamepadKeys.Button.A)) {
                bot.motorTo(TeleOpConfig.SLIDE_MOTOR_TEST_POS);
            }

            if (Gamepad2.getButton(GamepadKeys.Button.B)) {
                bot.motorTo(0);
            }
            /*
               ////////////////////////// TELEMETRY //////////////////////////
            */
            telemetry.addData("Status", "power: x:" + x + " y:" + y + " z:" + z);
            telemetry.addData("Front Left Motor", "pos: "+bot.motor_frontLeft.encoder.getPosition());
            telemetry.addData("Front Right Motor", "pos: "+bot.motor_frontRight.encoder.getPosition());
            telemetry.addData("Back Left Motor", "pos: "+bot.motor_backLeft.encoder.getPosition());
            telemetry.addData("Back Right Motor", "pos: "+bot.motor_backRight.encoder.getPosition());
            telemetry.addData("Slide Motor Encoder", "pos: "+bot.slideMotors.encoder.getPosition());
            telemetry.addData("Slide Motor Position", "pos: "+bot.slideMotors.getCurrentPosition());
            telemetry.update();
        }
    }
}
