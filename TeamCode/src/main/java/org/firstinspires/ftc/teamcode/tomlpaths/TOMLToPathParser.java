package org.firstinspires.ftc.teamcode.tomlpaths;

import android.annotation.SuppressLint;

import org.tomlj.Toml;
import org.tomlj.TomlTable;

import java.io.File;
import java.util.Vector;

public class TOMLToPathParser {
    Vector<TomlTable> tomlPaths = new Vector();

    @SuppressLint("NewApi")
    public TOMLToPathParser() {
        File dir = new File(".");
        for (File file : dir.listFiles()) {
            if(file.getName().endsWith(".toml")) {
                tomlPaths.add(Toml.parse(file.getPath()).getTable("moves"));
            }
        }
    }
}