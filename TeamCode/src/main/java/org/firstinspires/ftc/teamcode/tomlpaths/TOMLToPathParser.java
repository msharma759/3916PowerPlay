package org.firstinspires.ftc.teamcode.tomlpaths;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryMarker;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.trajectorysequence.sequencesegment.SequenceSegment;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * TOML Parser method to use to avoid compiling every time a path is changed.
 *
 * @author Jude Naramor
 * @author Maulik Verma
 * @version May 2023
 *
 */

public class TOMLToPathParser {
    ArrayList<TomlTable> tomlSequences = new ArrayList<>();

    @SuppressLint("NewApi")
//    public TOMLToPathParser() {
//        File dir = new File(".");
//        for (File file : dir.listFiles()) {
//            if(file.getName().endsWith(".toml")) {
//                tomlSequences.add(Toml.parse(file.getPath()).getTable("sequence");
//
//            }
//        }
//    }
    public TOMLToPathParser() {
        //gets the file
        File file = new File("~/TeamCode/java/tomlpaths/Blue_1_1.toml");
        //parses the file through TOML
        tomlSequences.add(Toml.parse(file.getPath()).getTable("sequence"));
    }

    public TrajectorySequenceBuilder Parse(String filename)
    {
        File file = new File(filename);

        tomlSequences.add(Toml.parse(file.getPath()).getTable("sequence"));
        tomlSequences.add(Toml.parse(file.getPath()).getTable("initialPosition"));
        double lastHeading = (double) ((tomlSequences.get(1).getArray("initialPosition")).toList()).get(2);
        double lastX = (double) ((tomlSequences.get(1).getArray("initialPosition")).toList()).get(0);
        double lastY = (double) ((tomlSequences.get(1).getArray("initialPosition")).toList()).get(1);

        Pose2d startPose = new Pose2d(lastX, lastY, Math.toRadians(lastHeading));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequenceBuilder trajSeq = drive.trajectorySequenceBuilder(startPose);
        for (int i = 0; i < tomlSequences.get(0).getArray("sequence").toList().size() - 1; i++)
        {
            List list = (tomlSequences.get(0)).getArray("sequence.traj" + (i+1) + ".args").toList();
            double[] array = (double[]) list.get(0); // x, y

            //handle types of paths (i.e. constant, linear, spline, etc.
            switch  ((String)(tomlSequences.get(0)).getArray("sequence.traj" + (i+1) + ".args").toList().get(0)) {
                case "lineToLinearHeading":
                    trajSeq.lineToLinearHeading(new Pose2d(array[0], array[1], Math.toRadians((int)list.get(1))));
                    break;
                case "lineToConstantHeading":
                    trajSeq.lineToConstantHeading(new Vector2d(array[0], array[1]));
                    break;
                case "lineToSplineHeading":
                    trajSeq.lineToSplineHeading(new Pose2d(array[0], array[1], Math.toRadians((int)list.get(1))));
                    break;
                default:
                    break;
                case "splineToConstantHeading":
                    trajSeq.splineToConstantHeading(new Vector2d(array[0], array[1]), Math.toRadians((int)list.get(1)));
                    break;
                case "splineToLinearHeading":
                    trajSeq.splineToLinearHeading(new Pose2d(array[0], array[1]), Math.toRadians((int)list.get(1)));
                    break;
                case "splineToSplineHeading":
                    trajSeq.splineToSplineHeading(new Pose2d(array[0], array[1]), Math.toRadians((int)list.get(1)));
                    break;
                case "splineTo":
                    trajSeq.splineTo(new Vector2d(array[0], array[1]), Math.toRadians((int)list.get(1)));
                    break;
                case "lineTo":
                    trajSeq.lineTo(new Vector2d(array[0], array[1]));
                    break;
                case "strafeTo":
                    trajSeq.strafeTo(new Vector2d(array[0], array[1]));
                    break;
                case "strafeRight":
                    trajSeq.strafeRight(array[0]);
                    break;
                case "strafeLeft":
                    trajSeq.strafeLeft(array[0]);
                    break;
                case "back":
                    trajSeq.back(array[0]);
                    break;
                case "forward":
                    trajSeq.forward(array[0]);
                    break;



            }
        }

        return trajSeq;
    }
}
