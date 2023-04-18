# Team 3916 TOML Paths

### A project to make auto paths editable in TOML from *ftc-dashboard*

## Project Overview
In FTC robotics, programmers must often edit their configurations and auto paths. This process is often slow and cumbersome, and one that can be improved by using interpreted

## Design Structure
+ Programmer will edit a TOML file to define a trajectory sequence for their robot
- This TOML file will live in the team codebase, while being editable through the *ftc-dashboard* during testing.

## Design Goals

## Specifications

### TOML Structure

Each TOML file represents an entire autonomous period during a single match.
In this way, each file is directly comprable to an autonomous file written in Java in standard RoadRunner.
Each TOML file contains an array named "sequence", the members of which will be objects which each correspond to a trajectory or marker. For example:
```toml
        sequence = [traj1, traj2, mark1, traj3]
```
Trajectories follow their own syntax, in which a string "type" and array "args" are contained.
Key type may be any of the following values: [TODO].
Key args contains a list of arguments which are passed to the trajectory parser and which are simply the same arguments passed into a trajectory being built using RoadRunner.
All vectors, poses, and other objects composed of multiple numbers should be represented by an array of their values, in the same order in which they are used in typical RoadRunner.
```toml
        traj1 = {type="splineToSplineHeading", args=[[40, -32], 90]}
```

Markers simply consist of string "type" and integer "callback".

### Translating to RoadRunner

In order to convert the user-generated TOML to a RoadRunner path we can actually use, we will, at each runtime, have a Java class iterate through the TOML structure and submit the values to the trajectorySequenceBuilder() method in RoadRunner. This will return trajectory sequences which the robot will then follow in a typical way.

TOMLToPathParser myParser = new TOMLToPathParser();
TrajectorySequence traj = myParser.getSequence("file");