package org.firstinspires.ftc.teamcode;


import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * -- TEAM 3916 --
 * Robot functions using FTCLib library. Used to program season-specific robot functionality.
 * NOTICE: all dimensions are measured in meters (unless specified). all measurements should be taken in meters or else calculations will absolutely mess up
 *
 * NOTICE: all angles are measured in radians (unless specified). please don't ever use degrees for angles
 *
 * @author Aman Anas
 * @author Gabrian Chua
 * @author Jason Armbruster
 * @author Vikram Krishnakumar
 *
 * @since November 2020
 * @version October 2021
 *
 */

public class FTCLibRobotFunctions extends FTCLibMecanumBot {
    /*
       Put extra game-specific robot functionality here,
       such as additional motors, servos, and sensors for arms, claws, and lifts.
     */


    //Set motors and servos

    //Example:
    //public MotorEx flywheelMotor;
    public MotorEx slideMotor;
    public ServoEx allenServo;
//    public SlidePosition currentSlidePosition = SlidePosition.BOTTOM;
//    public enum SlidePosition{
//        BOTTOM,
//        LOW,
//        MIDDLE,
//        TOP;
//    }

    //initialize motors and servos
    public void initBot(HardwareMap hw) {
        super.init(hw);

        allenServo = new SimpleServo(hw, "allen servo", 0,180);

        slideMotor = new MotorEx(hw, "slide motor");
        slideMotor.setRunMode(Motor.RunMode.RawPower);
        slideMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

    }

    /*
               ////////////////////////// Methods for extra components //////////////////////////
    */

    public void openClaw() {
        allenServo.setPosition(TeleOpConfig.ALLEN_SERVO_MAX);
    }

    public void closeClaw() {
        allenServo.setPosition(TeleOpConfig.ALLEN_SERVO_MIN);
    }

//    public boolean inRange(double num, double range) {
//        return
//    }
//
//    public void moveSlide(double speed) {
//
//        switch (currentSlidePosition) {
//            case BOTTOM:
//                if(slideMotor.encoder.getPosition() != 0) {
//
//                }
//                else {
//                    slideMotor.set(0);
//                }
//                break;
//            case LOW:
//
//                break;
//            case MIDDLE:
//
//                break;
//            case TOP:
//
//                break;
//        }
//    }

    /* Example:

    //Flywheel
    public void setFlywheelMotor(double speed) {
       //Set PID Coefficients
       flywheelMotor.setVeloCoefficients(TeleOpConfig.FLYWHEEL_KP, TeleOpConfig.FLYWHEEL_KI, TeleOpConfig.FLYWHEEL_KD);
       //Set Velocity
       flywheelMotor.setVelocity(speed * MAX_TICKS_PER_SECOND);
    }

    Refer to FTCLib docs here: https://docs.ftclib.org/ftclib/features/hardware
    */

}