package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class NextGenTeleOpHardwareMap {
    public DcMotor LFMotor;
    public DcMotor RFMotor;
    public DcMotor LBMotor;
    public DcMotor RBMotor;
    public DcMotor liftMotor;
    public Servo hookServo;
    //public NavxMicroNavigationSensor navx;


    //IntegratingGyroscope gyro;
    //NavxMicroNavigationSensor navxMicro;
    //public double degrees;
    ElapsedTime timer = new ElapsedTime();
    //Boxy         robot   = new Boxy();
    //NavXMicro Navx = new NavXMicro();

    private ElapsedTime     runtime = new ElapsedTime();
    static final double     BOT_SPEED = 0.3;
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: NEVEREST 40 Motor Encoder https://www.servocity.com/neverest-40-gearmotor
    static final double     ROTATIONS_PER_MINUTE    = 160 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    static final double THRESHOLD = 2;
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double     FORWARD_SPEED           = 0.6;
    static final double     BACKWARDS_SPEED         = -0.6;
    static final int        LIFT_EXTEND_MAX         = 2628;
    static final int        LIFT_DOWN_START_POS     = 97;
    static final int        LIFT_EXTEND_LATCH       = 1511;
    static final int        LIFT_EXTEND_LAND        = 1511;
    static final int        LIFT_DOWN_END_POS       = 97;
    static final double     HOOK_CLOSE              = 0;
    static final double     HOOK_OPEN               = 0;





    /**
     * Initialize the hardware
     *
     * @param hardwareMap configuration from FTC application
     */



    public void init(HardwareMap hardwareMap) {
        // grab wheels
        LFMotor = hardwareMap.dcMotor.get("LFMotor");
        RFMotor = hardwareMap.dcMotor.get("RFMotor");
        LBMotor = hardwareMap.dcMotor.get("LBMotor");
        RBMotor = hardwareMap.dcMotor.get("RBMotor");
        LFMotor.setDirection(DcMotor.Direction.FORWARD);
        LBMotor.setDirection(DcMotor.Direction.FORWARD);
        RFMotor.setDirection(DcMotor.Direction.REVERSE);
        RBMotor.setDirection(DcMotor.Direction.REVERSE);
        //front is 1 back is 2
        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //front is 1 back is 2
        hookServo = hardwareMap.servo.get("hookServo");
        hookServo.setDirection(Servo.Direction.FORWARD);
        hookServo.setPosition(0);

    }
    public void stopMotors() {
        LFMotor.setPower(0);
        RFMotor.setPower(0);
        LBMotor.setPower(0);
        RBMotor.setPower(0);
    }
}
