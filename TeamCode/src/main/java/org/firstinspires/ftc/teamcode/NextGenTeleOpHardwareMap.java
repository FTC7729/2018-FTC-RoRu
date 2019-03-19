package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public abstract class NextGenTeleOpHardwareMap extends OpMode {
    public DcMotor LFMotor;
    public DcMotor RFMotor;
    public DcMotor LBMotor;
    public DcMotor RBMotor;
    public DcMotor liftMotor;
    //public DcMotor collectorLift;
    public Servo hookServo;
    //public CRServo collectorServo;
    public Servo boxServo;
    public DigitalChannel LimitSwitchCollector;
    public NavxMicroNavigationSensor navx;


    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxMicro;
    public double degrees;
    ElapsedTime timer = new ElapsedTime();
    //Boxy         robot   = new Boxy();
    //NavXMicro Navx = new NavXMicro();

    private ElapsedTime     runtime = new ElapsedTime();
 //   public NavxMicroNavigationSensor navx;
 //   IntegratingGyroscope gyro;
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
    public final int LIFT_HOLD_POSITION = -100; // minimum start for automnomous
    public final int LIFT_RUN_POSITION = -5743; //lift for endgame
    public final int LIFT_DOWN_POSITION = -1000;
    public final int LIFT_DUMP_MAX_POSITION = -6470;
    static final double     HOOK_CLOSE              = 0;
    static final double     HOOK_OPEN               = 0;


    static final int COLLECTOR_LIFT_START = 0;
    static final int COLLECTOR_LIFT_UPRIGHT = -330;
    static final int COLLECTOR_LIFT_CRATER = -889;


    /**
     * Initialize the hardware
     *
     * @param hardwareMap configuration from FTC application
     */



    public void init(HardwareMap hardwareMap) {
        navx = hardwareMap.get(NavxMicroNavigationSensor.class,"navx");
        gyro = (IntegratingGyroscope)navx;
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

        boxServo = hardwareMap.servo.get("boxServo");
        boxServo.setDirection(Servo.Direction.FORWARD);
        boxServo.setPosition(0); //max down
        //1 = max up (dump)
//        collectorServo = hardwareMap.crservo.get("collectorServo");
//        collectorServo.setDirection(CRServo.Direction.FORWARD);
        //collectorServo.setPower(0);


//        collectorLift = hardwareMap.dcMotor.get("collectorLift");
//        collectorLift.setDirection(DcMotor.Direction.FORWARD);
//        collectorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        collectorLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        collectorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        collectorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //LimitSwitchCollector = hardwareMap.digitalChannel.get("LimitSwitch");
        if (!navx.isCalibrating()) telemetry.addData("Gyro","Calibrated!");
    }
    public void turnLeft(double power) {
        LFMotor.setPower(-power);
        RFMotor.setPower(power);
        LBMotor.setPower(-power);
        RBMotor.setPower(power);
    }
    public void turnRight(double power) {
        LFMotor.setPower(power);
        RFMotor.setPower(-power);
        LBMotor.setPower(power);
        RBMotor.setPower(-power);
    }
    public void stopMotors() {
        LFMotor.setPower(0);
        RFMotor.setPower(0);
        LBMotor.setPower(0);
        RBMotor.setPower(0);
    }
    public void navxTurn(double target) {
        Orientation angles;
        while(true) {
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading",angles.firstAngle+" degrees");
            if(angles.firstAngle < target - THRESHOLD) {
                telemetry.addData("Status","Turning Left");
                turnLeft(BOT_SPEED);
            } else if(angles.firstAngle > target + THRESHOLD) {
                telemetry.addData("Status","Turning Right");
                turnRight(BOT_SPEED);
            } else {
                stopMotors();
                break;
            }
            telemetry.update();
            //idle();
        }
    }

}
