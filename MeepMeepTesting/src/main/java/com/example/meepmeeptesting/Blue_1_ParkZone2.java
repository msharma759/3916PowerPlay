package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.entity.TrajectorySequenceEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

/**
     * @author Jason Armbruster
     */

    public class Blue_1_ParkZone2 {
        public static void main(String[] args) {

            MeepMeep meepMeep = new MeepMeep(800);

            Pose2d startPose = new Pose2d(-35.00, 60.00, Math.toRadians(270));

            TrajectorySequence zone1 = drive.trajectorySequenceBuilder()
                    .strafeRight(24)
                    .forward(30)
                    .build();

            RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                    .followTrajectorySequence(zone1);

            meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
    }
}
