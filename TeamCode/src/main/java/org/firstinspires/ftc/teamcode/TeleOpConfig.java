package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
public class TeleOpConfig {
    public static double PRECISION_POWER_MULTIPLIER = 0.5; //Multiplier for motor power (for precision mode)
    public static double PRECISION_TURN_MULTIPLIER = 0.5; // Multiplier for turning speed (for precision mode)
    public static double STICK_DEAD_ZONE = 0.03;
    public static double CLAW_SERVO_MAX = 240;
    public static double CLAW_SERVO_MIN = 165;
    public static double LEFT_SLIDE_MOTOR_COEFFICIENT = 0.05;
    public static double LEFT_SLIDE_MOTOR_TOLERANCE = 10;

    public static double BRAKE_POWER = 0;

    public static double RIGHT_SLIDE_MOTOR_COEFFICIENT = 0.05;
    public static double RIGHT_SLIDE_MOTOR_TOLERANCE = 10;

    public static double SLIDE_POWER = .4;
    public static double SLIDE_POWER_DOWN = .2;

    public static int SLIDE_MOTOR_TEST_POS = 5000;
    public static int SLIDE_MOTOR_TOP_POS = 5000;
    public static PIDFCoefficients PIDF_COEFFICIENTS_LEFT_SLIDE_MOTOR = new PIDFCoefficients(0, 0, 0, 0);
    public static PIDFCoefficients PIDF_COEFFICIENTS_RIGHT_SLIDE_MOTOR = new PIDFCoefficients(0, 0, 0, 0);
//    public static double SLIDE_POS_LOW = 100;
//    public static double SLIDE_POS_MIDDLE = 200;
//    public static double SLIDE_POS_TOP = 300;
//    public static double SLIDE_ACCURACY_RANGE = 20;
}
