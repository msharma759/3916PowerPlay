package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.TeleOpConfig.CLAW_SERVO_MAX;
import static org.firstinspires.ftc.teamcode.TeleOpConfig.CLAW_SERVO_MIN;
import static org.firstinspires.ftc.teamcode.TeleOpConfig.PIDF_COEFFICIENTS_LEFT_SLIDE_MOTOR;
import static org.firstinspires.ftc.teamcode.TeleOpConfig.PIDF_COEFFICIENTS_RIGHT_SLIDE_MOTOR;

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
 * @author Anna Lynch
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
    public double leftSlideMotorTargetVelocity = 0;
    public double rightSlideMotorTargetVelocity = 0;
    private double slideMotorCurrentTarget = 0;
    public PIDFController leftPidSlideMotor;
    public PIDFController rightPidSlideMotor;

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
        leftPidSlideMotor = new PIDFController(PIDF_COEFFICIENTS_LEFT_SLIDE_MOTOR.p, PIDF_COEFFICIENTS_LEFT_SLIDE_MOTOR.i, PIDF_COEFFICIENTS_LEFT_SLIDE_MOTOR.d, PIDF_COEFFICIENTS_LEFT_SLIDE_MOTOR.f);
        leftPidSlideMotor.setTolerance(TeleOpConfig.LEFT_SLIDE_MOTOR_TOLERANCE);

        rightPidSlideMotor = new PIDFController(PIDF_COEFFICIENTS_RIGHT_SLIDE_MOTOR.p, PIDF_COEFFICIENTS_RIGHT_SLIDE_MOTOR.i, PIDF_COEFFICIENTS_RIGHT_SLIDE_MOTOR.d, PIDF_COEFFICIENTS_RIGHT_SLIDE_MOTOR.f);
        rightPidSlideMotor.setTolerance(TeleOpConfig.RIGHT_SLIDE_MOTOR_TOLERANCE);

        clawServo = new SimpleServo(hw, "claw servo", 0, 300);

        rightSlideMotor = new MotorEx(hw, "right slide motor");
        rightSlideMotor.setRunMode(Motor.RunMode.VelocityControl);
        rightSlideMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightSlideMotor.encoder.reset();

        rightSlideMotor.setInverted(true);

        leftSlideMotor = new MotorEx(hw, "left slide motor");
        leftSlideMotor.setRunMode(Motor.RunMode.VelocityControl);
        leftSlideMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftSlideMotor.encoder.reset();

        leftSlideMotor.setPositionCoefficient(TeleOpConfig.LEFT_SLIDE_MOTOR_COEFFICIENT);
        leftSlideMotor.setPositionTolerance(TeleOpConfig.LEFT_SLIDE_MOTOR_TOLERANCE);

        rightSlideMotor.setPositionCoefficient(TeleOpConfig.RIGHT_SLIDE_MOTOR_COEFFICIENT);
        rightSlideMotor.setPositionTolerance(TeleOpConfig.RIGHT_SLIDE_MOTOR_TOLERANCE);

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
        if (!motorAtPos(leftSlideMotor, slideMotorCurrentTarget, TeleOpConfig.LEFT_SLIDE_MOTOR_TOLERANCE) && slideBusy) {
            slideBusy = true;
            if (curPos < slideMotorCurrentTarget) {
                leftSlideMotor.set(1);
            } else {
                leftSlideMotor.set(-1);
            }
        } else {
            slideBusy = false;
            leftSlideMotor.set(0);
        }

        curPos = rightSlideMotor.getCurrentPosition();
        if(!motorAtPos(rightSlideMotor, slideMotorCurrentTarget, TeleOpConfig.RIGHT_SLIDE_MOTOR_TOLERANCE) && slideBusy) {
            slideBusy = true;
            if (curPos < slideMotorCurrentTarget) {
                rightSlideMotor.set(1);
            } else {
                rightSlideMotor.set(-1);
            }
        } else {
            slideBusy = false;
            rightSlideMotor.set(0);
        }
    }

    public void motorUpdate(double pos){
        leftPidSlideMotor.setSetPoint(pos);
        double output = 0;
        if(!leftPidSlideMotor.atSetPoint()){
             output = leftPidSlideMotor.calculate(
                    leftSlideMotor.getCurrentPosition()  // the measured value
            );
        }
        leftSlideMotor.setVelocity(output);
        leftSlideMotorTargetVelocity = output;

        rightPidSlideMotor.setSetPoint(pos);
        output = 0;
        if(!rightPidSlideMotor.atSetPoint()){
            output = rightPidSlideMotor.calculate(
                    rightSlideMotor.getCurrentPosition()  // the measured value
            );
        }
        rightSlideMotor.setVelocity(output);
        rightSlideMotorTargetVelocity = output;
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
    public void moveSlide(double speed) {
        leftSlideMotor.setVelocity(speed);
        rightSlideMotor.setVelocity(speed);
    }

//    Refer to FTCLib docs here: https://docs.ftclib.org/ftclib/features/hardware

}
