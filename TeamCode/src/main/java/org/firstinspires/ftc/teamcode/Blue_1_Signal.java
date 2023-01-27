package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.ConeDetectionPipeline;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

/*
 * Op mode for preliminary tuning of the follower PID coefficients (located in the drive base
 * classes). The robot drives back and forth in a straight line indefinitely. Utilization of the
 * dashboard is recommended for this tuning routine. To access the dashboard, connect your computer
 * to the RC's WiFi network. In your browser, navigate to https://192.168.49.1:8080/dash if you're
 * using the RC phone or https://192.168.43.1:8080/dash if you are using the Control Hub. Once
 * you've successfully connected, start the program, and your robot will begin moving forward and
 * backward. You should observe the target position (green) and your pose estimate (blue) and adjust
 * your follower PID coefficients such that you follow the target position as accurately as possible.
 * If you are using SampleMecanumDrive, you should be tuning TRANSLATIONAL_PID and HEADING_PID.
 * If you are using SampleTankDrive, you should be tuning AXIAL_PID, CROSS_TRACK_PID, and HEADING_PID.
 * These coefficients can be tuned live in dashboard.
 *
 * This opmode is designed as a convenient, coarse tuning for the follower PID coefficients. It
 * is recommended that you use the FollowerPIDTuner opmode for further fine tuning.
 */
@Config
@Autonomous(group = "drive")
public class Blue_1_Signal extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "WEBCAM");
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        FTCLibRobotFunctions bot = new FTCLibRobotFunctions();
        bot.initBot(hardwareMap);

        final ConeDetectionPipeline.Color[] color = new ConeDetectionPipeline.Color[1];

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                ConeDetectionPipeline coneDetectionPipeline = new ConeDetectionPipeline();
                camera.setPipeline(coneDetectionPipeline);
                color[0] = coneDetectionPipeline.getColorGuess();
            }
            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        Pose2d startPose = new Pose2d(-35.00, 60.00, Math.toRadians(270));

        TrajectorySequence parkZone1 = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(24)
                .forward(24)
                .build();

        TrajectorySequence parkZone2 = drive.trajectorySequenceBuilder(startPose)
                .forward(24)
                .build();

        TrajectorySequence parkZone3 = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(24)
                .forward(24)
                .build();

        waitForStart();

        switch (color[0]) {
            case Yellow:
                drive.followTrajectorySequence(parkZone1);
            case Cyan:
                drive.followTrajectorySequence(parkZone2);
            case Magenta:
                drive.followTrajectorySequence(parkZone3);
        }

        while (opModeIsActive() && !isStopRequested()) {
            bot.motorUpdate();
        }
    }
}