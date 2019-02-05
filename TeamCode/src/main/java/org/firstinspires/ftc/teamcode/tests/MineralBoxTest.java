package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class MineralBoxTest extends OpMode {
    public Servo boxServo;
    public static final double START_WEEBLE = 0;
    public static final double END_WEEBLE = 0.40;

    static final double     BOT_SPEED = 0.3;
    static final double     COUNTS_PER_MOTOR_REV_NEVEREST40    = 1120 ;    // eg: NEVEREST 40 Motor Encoder https://www.servocity.com/neverest-40-gearmotor
    static final double     DRIVE_GEAR_REDUCTION    = 1 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV_NEVEREST40
            * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    public void init (HardwareMap hardwaremap)
    {
        //front is 1 back is 2
        boxServo = hardwareMap.servo.get("boxServo");
        boxServo.setDirection(Servo.Direction.FORWARD);
        boxServo.setPosition(0);

    }

}