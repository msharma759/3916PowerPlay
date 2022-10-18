package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Blue_1_1 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35.00, 63.00, 0))
                                .lineToConstantHeading(new Vector2d(-12.00, 60.00))
                                .lineToConstantHeading(new Vector2d(-12.00, 23.0))
                                //Theoretical Pause
                                .lineToLinearHeading(new Pose2d(-12.00, 12, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(-55.00, 12, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(-24.00, 12, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(-35.50, 12, Math.toRadians(90)))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}