package org.firstinspires.ftc.teamcode.tomlpaths;

import android.annotation.SuppressLint;

import org.tomlj.Toml;
import org.tomlj.TomlTable;

import java.io.File;
import java.nio.file.Path;
import java.util.Vector;

public class TomlToPathParser {
    Vector<TomlTable> tomlPaths = new Vector();

    @SuppressLint("NewApi")
    public TomlToPathParser() {
        File dir = new File(".");
        for (File file : dir.listFiles()) {
            if(file.getName().endsWith(".toml")) {
                tomlPaths.add(Toml.parse(file.getPath()).getTable("moves"));
            }
        }
    }
}