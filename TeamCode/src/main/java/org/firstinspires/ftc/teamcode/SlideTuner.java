package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * SlideTuner,Tunes the Slide
 *
 * @author Simran Bali
 *
 */
public class SlideTuner extends LinearOpMode  {
    private FTCLibRobotFunctions bot = new FTCLibRobotFunctions();

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize telemetry and dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

        //Initialize bot
        bot.initBot(hardwareMap);
        int targetpos = 2000;
        while(opModeIsActive()){
            bot.motorUpdate(targetpos);
            if(!bot.isSlideBusy()){
                sleep(1000);
                if(targetpos == 2000){
                    targetpos = 200;
                }else{
                    targetpos = 2000;
                }
            }
            telemetry.addData("TargetLeftVelocity", bot.leftSlideMotorTargetVelocity);
            telemetry.addData("TargetRightVelocity", bot.rightSlideMotorTargetVelocity);
            telemetry.addData("ActualLeftVelocity", bot.leftSlideMotor.getVelocity());
            telemetry.addData("ActualRightVelocity", bot.rightSlideMotor.getVelocity());
        }
    }
}
