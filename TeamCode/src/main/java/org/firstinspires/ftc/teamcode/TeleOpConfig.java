package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.config.Config;

@Config
public class TeleOpConfig {
    public static double PRECISION_POWER_MULTIPLIER = 0.5; //Multiplier for motor power (for precision mode)
    public static double PRECISION_TURN_MULTIPLIER = 0.5; // Multiplier for turning speed (for precision mode)
    public static double STICK_DEAD_ZONE = 0.03;
    public static double ALLEN_SERVO_MAX = 0;
    public static double ALLEN_SERVO_MIN = 0.7;
    public static double SLIDE_MOTOR_COEFFICIENT = 0.05;
    public static double SLIDE_MOTOR_TOLERANCE = 10;
    public static int SLIDE_MOTOR_TEST_POS = 3500;
//    public static double SLIDE_POS_LOW = 100;
//    public static double SLIDE_POS_MIDDLE = 200;
//    public static double SLIDE_POS_TOP = 300;
//    public static double SLIDE_ACCURACY_RANGE = 20;
}
