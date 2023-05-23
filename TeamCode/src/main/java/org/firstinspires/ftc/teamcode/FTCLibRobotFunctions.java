package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.TeleOpConfig.CLAW_SERVO_MAX;
import static org.firstinspires.ftc.teamcode.TeleOpConfig.CLAW_SERVO_MIN;
import static org.firstinspires.ftc.teamcode.TeleOpConfig.PIDF_COEFFICIENTS_SLIDE_MOTOR;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.checkerframework.checker.units.qual.C;

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
 * @author Maulik Verma
 * @author Jude Naramor
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
    public MotorEx leftSlideMotor;
    public MotorEx rightSlideMotor;
    public ServoEx clawServo;
    public double slideMotorTargetVelocity = 0;
    private double slideMotorCurrentTarget = 0;
    public PIDFController pidSlideMotor;

    public boolean slideBusy = false;
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
        pidSlideMotor = new PIDFController(PIDF_COEFFICIENTS_SLIDE_MOTOR.p, PIDF_COEFFICIENTS_SLIDE_MOTOR.i, PIDF_COEFFICIENTS_SLIDE_MOTOR.d, PIDF_COEFFICIENTS_SLIDE_MOTOR.f);
        pidSlideMotor.setTolerance(TeleOpConfig.SLIDE_MOTOR_TOLERANCE);
        clawServo = new SimpleServo(hw, "claw servo", 0, 300);

        rightSlideMotor = new MotorEx(hw, "right slide motor");
        rightSlideMotor.setRunMode(Motor.RunMode.RawPower);
        rightSlideMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightSlideMotor.encoder.reset();

        leftSlideMotor = new MotorEx(hw, "left slide motor");
        leftSlideMotor.setRunMode(Motor.RunMode.RawPower);
        leftSlideMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftSlideMotor.encoder.reset();

        leftSlideMotor.setPositionCoefficient(TeleOpConfig.SLIDE_MOTOR_COEFFICIENT);
        leftSlideMotor.setPositionTolerance(TeleOpConfig.SLIDE_MOTOR_TOLERANCE);

    }

    /*
               ////////////////////////// Methods for extra components //////////////////////////
    */

    /**
     * Check if the provided motor
     * @param motor motor to be checked
     * @param target target position for the motor
     * @param tolerance tolerance for the target
     * @return if it is at the position or not
     */
    private boolean motorAtPos(MotorEx motor, double target, double tolerance) {
        double motorPos = motor.getCurrentPosition();
        return motorPos - tolerance < target && motorPos + tolerance > target;
    }


    /**
     * Call this method every loop, runs the slide motor
     */


    public void motorUpdate() {
        int curPos = leftSlideMotor.getCurrentPosition();
        if (!motorAtPos(leftSlideMotor, slideMotorCurrentTarget, TeleOpConfig.SLIDE_MOTOR_TOLERANCE) && slideBusy) {
            slideBusy = true;
            if (curPos < slideMotorCurrentTarget) {
                leftSlideMotor.set(1);
                rightSlideMotor.set(1);
            } else {
                leftSlideMotor.set(-1);
                rightSlideMotor.set(-1);
            }
        } else {
            slideBusy = false;
            leftSlideMotor.set(0);
            rightSlideMotor.set(0);
        }
    }

    public void motorUpdate(double pos){
        pidSlideMotor.setSetPoint(pos);
        double output = 0;
        if(!pidSlideMotor.atSetPoint()){
             output = pidSlideMotor.calculate(
                    leftSlideMotor.getCurrentPosition()  // the measured value
            );
        }
        leftSlideMotor.setVelocity(output);
        rightSlideMotor.setVelocity(output);
        slideMotorTargetVelocity = output;
    }

    public boolean isSlideBusy() {return slideBusy;}

    public void motorTo(int pos) {
        slideMotorCurrentTarget = pos;
    }

    public void openClaw() {
        clawServo.turnToAngle(TeleOpConfig.CLAW_SERVO_MAX);
        //clawServo.set(1);
    }

    public void closeClaw() {
        clawServo.turnToAngle(TeleOpConfig.CLAW_SERVO_MIN);
        //clawServo.set(-1);
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
