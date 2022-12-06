package com.example.meepmeeptesting;
/**
 * @author Jude Naramor
 * **/
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Red_1_1 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -65, Math.toRadians(90)))
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
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}