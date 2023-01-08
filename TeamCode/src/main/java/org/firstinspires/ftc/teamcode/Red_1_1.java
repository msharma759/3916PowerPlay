package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.vision.ConeDetectionPipeline;

import java.lang.reflect.Method;

@Autonomous(name="Red_1_1", group="Apex Robotics 3916")
public class Red_1_1 extends LinearOpMode {



    @Override
    public void runOpMode() throws InterruptedException {

//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        WebcamName webcamName = hardwareMap.get(WebcamName.class,"webcam");
//        OpenCvCamera extCam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        //Jason: We probably dont need to care about this but vision is scary so im keeping it
        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though. !Edit by Aman: Since we're using an external webcam, this is not applicable
        //extCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

//        pipeline = new SkystoneDeterminationPipeline();
//        extCam.setPipeline(pipeline);
//
//        extCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
//        {
//            @Override
//            public void onOpened()
//            {
//                extCam.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
//                FtcDashboard.getInstance().startCameraStream(extCam,30);
//            }
//        });

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        FTCLibRobotFunctions bot = new FTCLibRobotFunctions();
        bot.initBot(hardwareMap);

        int currentStep = 1;

        TrajectorySequence traj = drive.trajectorySequenceBuilder(new Pose2d(-35, -65, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-12, -65, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-12, -12, Math.toRadians(90)))
                //place to put down preloaded cone/scoring utility
                .lineToLinearHeading(new Pose2d(-25, -12, Math.toRadians(90)))
                //place to load a cone/scoring utility
                .lineToLinearHeading(new Pose2d(-55, -12, Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(-40, -12, Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(-25, -12, Math.toRadians(90)))
                //place to put down a preloaded cone/scoring utility
                .lineToLinearHeading(new Pose2d(-35, -12, Math.toRadians(90)))
                .build();


        waitForStart();

        //ConeDetectionPipeline.Color guessedColor = pipeline.getColorGuess();

        while (opModeIsActive() && !isStopRequested()) {
            bot.motorUpdate();
            // I WANT COROUTINES SO BAD, WHY DO WE NOT USE KOTLIN
            switch (currentStep) {
                case 1:
                    drive.followTrajectorySequenceAsync(traj);
                    currentStep++;
                    break;
                case 2:
                    if (!drive.isBusy()) currentStep++;
                    break;
                case 3:
                    bot.motorTo(TeleOpConfig.SLIDE_MOTOR_TOP_POS);
                    currentStep++;
                    break;
                case 4:
                    if (!bot.isSlideBusy()) currentStep++;
                    break;
                case 5:
                    //Do Claw Here, can be pausing
                default:
                    break;
            }
        }
    }
}
