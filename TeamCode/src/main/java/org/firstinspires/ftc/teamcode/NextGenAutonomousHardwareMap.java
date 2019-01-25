package org.firstinspires.ftc.teamcode;
import com.disnodeteam.dogecv.Dogeforia;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;


public abstract class NextGenAutonomousHardwareMap extends LinearOpMode{
    public DcMotor LFMotor;
    public DcMotor RFMotor;
    public DcMotor LBMotor;
    public DcMotor RBMotor;
    public DcMotor liftMotor;
    public DcMotor collectorLift;
    public Servo hookServo;
    public Servo collectorServo;
    public Servo mineralBox;
    public DigitalChannel LimitSwitchCollector;
    //public NavxMicroNavigationSensor navx;

    private final int LIFT_HOLD_POSITION = -109; // minimum start for automnomous
    /**
     * I2C Pin order: Red, Black, Yellow, White
     *
     * On Board Pin order: White, Yellow, Black, Red
     */
    public NavxMicroNavigationSensor navx;

    //Elapsed time and measurement constants
    private ElapsedTime runtime = new ElapsedTime();
    private static final float mmPerInch        = 25.4f;
    private static final float mmFTCFieldWidth  = (12*6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight   = (5.75f) * mmPerInch;          // the height of the center of the target image above the floor

    // Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
    // Valid choices are:  BACK or FRONT
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    //Vuforia variables
    private OpenGLMatrix lastLocation = null;
    boolean targetVisible;
    Dogeforia vuforia;
    WebcamName webcamName;
    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();


    //Detector object
    GoldAlignDetector detector;

    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxMicro;
    public double degrees;
    ElapsedTime timer = new ElapsedTime();
    //Boxy         robot   = new Boxy();
    //NavXMicro Navx = new NavXMicro();
    //private ElapsedTime     runtime = new ElapsedTime();
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
    /**
     * This value here from {@link AlanBlue1}
     */
    static final int        LIFT_EXTEND_MAX         = -4032; //extend va;ue for autonomous from lander to ground
    /**
     * This value here from {@link AlanBlue1}
     */
    static final int        LIFT_DOWN_END_POS       = -157;
    static final double     HOOK_CLOSE              = 1;
    static final double     HOOK_OPEN               = 0.37;

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
        collectorServo = hardwareMap.servo.get("collectorServo");
        collectorServo.setDirection(Servo.Direction.FORWARD);
        collectorServo.setPosition(0);

        mineralBox = hardwareMap.servo.get("mineralBox");
        mineralBox.setDirection(Servo.Direction.FORWARD);
        mineralBox.setPosition(0);

        collectorLift = hardwareMap.dcMotor.get("collectorLift");
        collectorLift.setDirection(DcMotor.Direction.FORWARD);
        collectorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        collectorLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        collectorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LimitSwitchCollector = hardwareMap.digitalChannel.get("LimitSwitch");
        // grab navx sensor
        navx = hardwareMap.get(NavxMicroNavigationSensor.class,"navx");
        gyro = (IntegratingGyroscope)navx;
        while (navx.isCalibrating())  {
            telemetry.addData("Gyro", "calibrating %s", Math.round(runtime.seconds())%2==0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }
        // Setup camera and Vuforia parameters
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        // Set Vuforia parameters
        parameters.vuforiaLicenseKey = "Aa0ODYT/////AAABmbrSV1REZUlljIiCpPNPvdlR2T8o80Rzjt2HYPF1L/yMqgYtiPiS2wISQ/Hl5yWPn8/BGo9Gxj4Ik583p3trh7q4Yaw/l4F0+MhN6ApKPq6vYImunRyDouiULcV+Bd/haLM+G754/3Srfw11SdWKJjFfjYHafrNOkTqEZhyCQAza2xSWKHKtAMZot93WU6YM4wXlCrosFHWc4/YQZJb0BPi6m7R/7dJSVm8PR7jUfT8mnlW0A+q0K151xQtfxbj0CMGqIilLihCP1x8rYaWiAAaCwJU3slDaR22x9DqpRQ6E8+IryNxrZW2kX14rzCEax2EdnsrNi3SXdswXS+LsH3UfjtxU/sxLtjmFA8ekk0sQ";
        parameters.fillCameraMonitorViewParent = true;

        // Init Dogeforia
        vuforia = new Dogeforia(parameters);
        vuforia.enableConvertFrameToBitmap();

        // Set target names
        VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables.addAll(targetsRoverRuckus);

        // Set trackables' location on field
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);


        //Set camera displacement
        final int CAMERA_FORWARD_DISPLACEMENT  = 140;   // eg: Camera is 110 mm in front of robot center
        final int CAMERA_VERTICAL_DISPLACEMENT = 174;   // eg: Camera is 200 mm above ground
        final int CAMERA_LEFT_DISPLACEMENT     = 20;     // eg: Camera is ON the robot's center line

        // Set phone location on robot
        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

        //Set info for the trackables
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        }

        //Activate targets
        targetsRoverRuckus.activate();

        //detector = new GoldAlignDetector(); // Create a gold aligndetector
        //detector.init(hardwareMap.appContext,CameraViewDisplay.getInstance(), 0, true);

        //detector.yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 100); // Create new filter
        //detector.useDefaults(); // Use default settings
        //detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // Uncomment if using PERFECT_AREA scoring

        //Setup Vuforia
        // We need to have access to the detector in the opmode so the initialization happens in-opmode
        /*
        vuforia.setDogeCVDetector(detector); // Set the Vuforia detector
        vuforia.enableDogeCV(); //Enable the DogeCV part
        vuforia.showDebug(); // Show debug info
        vuforia.start(); // Start the whole thing
        */
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
            LFMotor.setPower(0);
            RFMotor.setPower(0);
            LBMotor.setPower(0);
            RBMotor.setPower(0);

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
