package org.firstinspires.ftc.teamcode.tomlpaths;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.sequencesegment.SequenceSegment;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TOMLToPathParser {
    Vector<TomlTable> tomlSequences = new Vector();

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

    public TrajectorySequence Parse(String filename)
    {
        File file = new File(filename);

        tomlSequences.add(Toml.parse(file.getPath()).getTable("sequence"));
        tomlSequences.add(Toml.parse(file.getPath()).getTable("initialPosition"));
        ArrayList<SequenceSegment> segments = new ArrayList<SequenceSegment>();
        double lastHeading = (double) ((tomlSequences.get(1).getArray("initialPosition")).toList()).get(2);
        double lastX = (double) ((tomlSequences.get(1).getArray("initialPosition")).toList()).get(0);
        double lastY = (double) ((tomlSequences.get(1).getArray("initialPosition")).toList()).get(1);

        for (int i = 0; i < tomlSequences.size() - 1; i++)
        {
            List list = (tomlSequences.get(0)).getArray("sequence.traj" + (i+1) + ".args").toList();
            double[] array = (double[]) list.get(0);

            Pose2d start = new Pose2d(lastX, lastY, lastHeading);
            switch () {

            }
            Pose2d end = new Pose2d((array[0], array[1], 0);
            SequenceSegment seg = new SequenceSegment(1, start, end, null); //fix later
            segments.add(seg);
            lastX = end.getX();
            lastY = end.getY();
            lastHeading = end.getHeading();
        }
        TrajectorySequence trajSeq = new TrajectorySequence(segments);
        return trajSeq;
    }
}