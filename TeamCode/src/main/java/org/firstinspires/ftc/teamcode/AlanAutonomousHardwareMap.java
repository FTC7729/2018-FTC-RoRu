package org.firstinspires.ftc.teamcode;
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.dogecv.HawkeyeDetector;


public abstract class AlanAutonomousHardwareMap extends LinearOpMode{
    public DcMotor LFMotor;
    public DcMotor RFMotor;
    public DcMotor LBMotor;
    public DcMotor RBMotor;
    public Servo weebleServ;
    public DcMotor liftMotor;
    public Servo hookServo;
    public HawkeyeDetector hawkeye;
    private final int LIFT_HOLD_POSITION = -5;
    /**
     * I2C Pin order: Red, Black, Yellow, White
     *
     * On Board Pin order: White, Yellow, Black, Red
     */
    public NavxMicroNavigationSensor navx;


    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxMicro;
    public double degrees;
    ElapsedTime timer = new ElapsedTime();
    //Boxy         robot   = new Boxy();
    //NavXMicro Navx = new NavXMicro();
    private ElapsedTime     runtime = new ElapsedTime();
    static final double     BOT_SPEED = 0.2;
    static final double     COUNTS_PER_MOTOR_REV_NEVEREST40    = 1120 ;    // eg: NEVEREST 40 Motor Encoder https://www.servocity.com/neverest-40-gearmotor
    static final double     COUNTS_PER_MOTOR_REV_NEVEREST20    = 560 ;
    static final double     ROTATIONS_PER_MINUTE    = 160 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV_NEVEREST20
            * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    static final double THRESHOLD = 2;
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double     FORWARD_SPEED           = 0.6;
    static final double     BACKWARDS_SPEED         = -0.6;
    static final double START_WEEBLE = 0;
    static final double END_WEEBLE = 0.45;
    /**
     * This value here from {@link AlanBlue1}
     */
    static final int        LIFT_EXTEND_MAX         = -2310;
    /**
     * This value here from {@link AlanBlue1}
     */
    static final int        LIFT_DOWN_END_POS       = -57;
    static final double     HOOK_CLOSE              = 0;
    static final double     HOOK_OPEN               = 0;

    /**
     * Initialize the hardware
     *
     * @param hardwareMap configuration from FTC application
     */
    public void  init(HardwareMap hardwareMap) throws InterruptedException {
        // grab wheels
        LFMotor = hardwareMap.dcMotor.get("LFMotor");
        RFMotor = hardwareMap.dcMotor.get("RFMotor");
        LBMotor = hardwareMap.dcMotor.get("LBMotor");
        RBMotor = hardwareMap.dcMotor.get("RBMotor");
        LFMotor.setDirection(DcMotor.Direction.FORWARD);
        LBMotor.setDirection(DcMotor.Direction.FORWARD);
        RFMotor.setDirection(DcMotor.Direction.REVERSE);
        RBMotor.setDirection(DcMotor.Direction.REVERSE);
        LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //front is 1 back is 2
        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setTargetPosition(LIFT_HOLD_POSITION);
        liftMotor.setPower(0.25);
        //liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //liftMotor.setPower(0);
        //front is 1 back is 2
        hookServo = hardwareMap.servo.get("hookServo");
        hookServo.setDirection(Servo.Direction.FORWARD);
        hookServo.setPosition(0);
        weebleServ = hardwareMap.servo.get("weeble");
        // grab navx sensor
        navx = hardwareMap.get(NavxMicroNavigationSensor.class,"navx");
        gyro = (IntegratingGyroscope)navx;
        while (navx.isCalibrating())  {
            telemetry.addData("Gyro", "calibrating %s", Math.round(runtime.seconds())%2==0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }
        hawkeye = new HawkeyeDetector();
        hawkeye.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
    }
    public void goForward(double power) {
        LFMotor.setPower(power);
        RFMotor.setPower(power);
        LBMotor.setPower(power);
        RBMotor.setPower(power);
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
    public void encoderDrive(double speed, double leftInches, double rightInches, double leftBackInches, double rightBackInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        if (opModeIsActive()) {
            newLeftTarget = LFMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = RFMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newLeftBackTarget = LBMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightBackTarget = RBMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            LFMotor.setTargetPosition(newLeftTarget);
            RFMotor.setTargetPosition(newRightTarget);
            LBMotor.setTargetPosition(newLeftBackTarget);
            RBMotor.setTargetPosition(newRightBackTarget);

            LFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            LFMotor.setPower(Math.abs(speed));
            RFMotor.setPower(Math.abs(speed));
            LBMotor.setPower(Math.abs(speed));
            RBMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (LFMotor.isBusy() && RFMotor.isBusy() && LBMotor.isBusy() && RBMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Going to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Currently at %7d :%7d",
                        LFMotor.getCurrentPosition(),
                        RFMotor.getCurrentPosition(),
                        LBMotor.getCurrentPosition(),
                        RBMotor.getCurrentPosition()
                );
                telemetry.update();
            }

            // Stop all motion;
            stopMotors();

            // Turn off RUN_TO_POSITION
            LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void encoderDriveHawk(double speed, double leftInches, double rightInches, double leftBackInches, double rightBackInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newLeftBackTarget;
        int newRightBackTarget;
        double pausedSeconds = 0;

        if (opModeIsActive()) {
            if(!hawkeye.getEnabled()) hawkeye.enable();
            newLeftTarget = LFMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = RFMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newLeftBackTarget = LBMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightBackTarget = RBMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            LFMotor.setTargetPosition(newLeftTarget);
            RFMotor.setTargetPosition(newRightTarget);
            LBMotor.setTargetPosition(newLeftBackTarget);
            RBMotor.setTargetPosition(newRightBackTarget);

            LFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            LFMotor.setPower(Math.abs(speed));
            RFMotor.setPower(Math.abs(speed));
            LBMotor.setPower(Math.abs(speed));
            RBMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS + pausedSeconds) &&
                    (LFMotor.isBusy() && RFMotor.isBusy() && LBMotor.isBusy() && RBMotor.isBusy())) {
                if(hawkeye.isFound()) {
                    stopMotors();
                    pausedSeconds += 0.1;
                    telemetry.addData("Status","Paused.");
                    sleep(100);
                } else {
                    telemetry.addData("Status","Running...");
                    LFMotor.setPower(Math.abs(speed));
                    RFMotor.setPower(Math.abs(speed));
                    LBMotor.setPower(Math.abs(speed));
                    RBMotor.setPower(Math.abs(speed));
                }
                // Display it for the driver.
                telemetry.addData("Path1",  "Going to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Currently at %7d :%7d",
                        LFMotor.getCurrentPosition(),
                        RFMotor.getCurrentPosition(),
                        LBMotor.getCurrentPosition(),
                        RBMotor.getCurrentPosition()
                );
                telemetry.update();
            }

            // Stop all motion;
            stopMotors();

            // Turn off RUN_TO_POSITION
            LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Turns the robot using the NavX Micro Navigational Sensor.
     * @param target The target number of degrees for the bot to reach.
     */
    public void navxTurn(double target) {
        Orientation angles;
        while(opModeIsActive()) {
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
            idle();
        }
    }

    /**
     * Turns the robot using the NavX Micro Navigational Sensor to determine heading. Uses a non-hardcoded threshold.
     * @param target The target number of degrees for the robot to reach.
     * @param threshold The threshold to be allowed for the robot heading.
     */
    public void navxTurn(double target, double threshold) {
        Orientation angles;
        while(opModeIsActive()) {
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading",angles.firstAngle+" degrees");
            if(angles.firstAngle < target - threshold) {
                telemetry.addData("Status","Turning Left");
                turnLeft(BOT_SPEED);
            } else if(angles.firstAngle > target + threshold) {
                telemetry.addData("Status","Turning Right");
                turnRight(BOT_SPEED);
            } else {
                stop();
                break;
            }
            telemetry.update();
            idle();
        }
    }
    /**
     * Turns the robot using the NavX Micro Navigational Sensor to determine heading. Turns relative to the current heading of the robot.
     * @param target The target number of degrees for the robot to reach, relative to the robot's current heading.
     */
    public void navxTurnRel(double target) {
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        target = angles.firstAngle + target;
        while(opModeIsActive()) {
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
            idle();
        }
    }
    /**
     * Turns the robot using the NavX Micro Navigational Sensor to determine heading. Uses a non-hardcoded threshold. Turns relative to the current heading of the robot.
     * @param target The target number of degrees away from current heading for the robot to reach
     * @param threshold The threshold to be allowed for the robot heading.
     */
    public void navxTurnRel(double target, double threshold) {
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        target = angles.firstAngle + target;
        while(opModeIsActive()) {
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading",angles.firstAngle+" degrees");
            if(angles.firstAngle < target - threshold) {
                telemetry.addData("Status","Turning Left");
                turnLeft(BOT_SPEED);
            } else if(angles.firstAngle > target + threshold) {
                telemetry.addData("Status","Turning Right");
                turnRight(BOT_SPEED);
            } else {
                stop();
                break;
            }
            telemetry.update();
            idle();
        }
    }
    public void setLiftPosition(int pos,double speed) {
        liftMotor.setTargetPosition(pos);
        liftMotor.setPower(speed);
        while((liftMotor.getCurrentPosition() > liftMotor.getTargetPosition() + 1||liftMotor.getCurrentPosition() < liftMotor.getTargetPosition() - 1) && opModeIsActive()) {
            telemetry.addData("Encoder Position",liftMotor.getCurrentPosition());
            telemetry.update();
            idle();
        }
        liftMotor.setPower(0);
    }
}
