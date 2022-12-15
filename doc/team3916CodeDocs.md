# TEAM 3916 APEX ROBOTICS CODE BIBLE
## Blazingly Clear Code
### By Andrew Lynch

This document will cover the basics of Team 3916 code structure, including where critical files are located in the project, and what information new programmers might need to know.

## Quick Start
so you have the main project, FreightFrenzy, and within the main project is FTCLibRobotics, MeepMeepTesting, and TeamCode, which is a larger project. TeamCode, in turn, contains a subdirectory named teamcode.
The 80/20 rule is a maxim that states that 80% of time, money, or effort is spent on 20% of the work. Nowhere could that be more true than at Apex Robotics, where the following pieces of code are most important:
### Auto Paths (found in /teamcode)
Auto paths are how our robot moves during autonomous. Autonomous is 1/3 of the game, so auto paths are very important! Robot-ready auto paths are located in the main /TeamCode/src/main/java/org/firstinspires/ftc/teamcode directory (/teamcode), while those undergoing testing wait in /MeepMeepTesting/src/main/java/com/example/meepmeeptasting (/meepmeepmeeptesting).
### Using the Phone

Auto paths
### Vision Code

## Best Practices
### Variable and File Naming
Autopaths should be named as such: Color\_PositionNumber\_Version, where the color (red or blue) corresponds to the alliance for which the robot competes in the code, the position number (1 or 2) corresponds to the corner in which the robot starts, and the version (any number) corresponds to additional information, such as date of creation or speciffic strategy.
### Code Commenting
Code commenting is important so that future programmers, and, more importantly, judges, can understand our code. That said, your code commenting needn't be excessive. One brief comment to explain every "chunk" of code which is part of the same process should suffice.
### Usage of Git
Commit and branch names should be concise and clear as to their purpose.
Real world examples of what *not* to do:
- Branch named "AHHHHHH"
- Commit named "ughhhhhhhhhhhhh"
- Commit named "IT WORKS AHHHHHH"
  Real world examples of what *to* do:
- Commit named "blue 2 1 made to face the corner", which changed an auto path to make path blue\_2\_1 face the corner.
- Commit named "MeepMeep added back", which added back the MeepMeep testing tool after it had been removed
- Branch named "MeepMeepTestingRedo", which reimplemented testing using the MeepMeep tool
  Avoid vague terms like "updated" or "improved," discuss what material changge your work made.

## Off-Limits Code

## General Operation