package org.firstinspires.ftc.teamcode.tomlpaths;

import android.annotation.SuppressLint;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.tomlj.Toml;
import org.tomlj.TomlTable;

import java.io.File;
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
        File file = new File("~/TeamCode/java/tomlpaths/Blue_1_1.toml");
        tomlSequences.add(Toml.parse(file.getPath()).getTable("sequence"));
    }
}