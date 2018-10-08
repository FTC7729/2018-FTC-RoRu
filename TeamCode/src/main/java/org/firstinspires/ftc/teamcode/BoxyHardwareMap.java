package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public abstract class BoxyHardwareMap extends LinearOpMode{
    public DcMotor LFMotor;
    public DcMotor RFMotor;
    public NavxMicroNavigationSensor navx;


    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxMicro;
    public double degrees;
    ElapsedTime timer = new ElapsedTime();
    //Boxy         robot   = new Boxy();
    //NavXMicro Navx = new NavXMicro();
    private ElapsedTime     runtime = new ElapsedTime();
    static final double     BOT_SPEED = 0.1;
    static final double     COUNTS_PER_MOTOR_REV    = 280 ;    // eg: NEVEREST 40 Motor Encoder
    static final double     ROTATIONS_PER_MINUTE    = 160 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.93701 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);


    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double     FORWARD_SPEED           = 0.6;
    static final double     BACKWARDS_SPEED         = -0.6;




    /**
     * Initialize the hardware
     *
     * @param hardwareMap configuration from FTC application
     */



    public void init(HardwareMap hardwareMap) {
        // grab wheels
        LFMotor = hardwareMap.dcMotor.get("LFMotor");
        RFMotor = hardwareMap.dcMotor.get("RFMotor");
        // grab navx sensor
        navx = hardwareMap.get(NavxMicroNavigationSensor.class,"navx");
    }
    public void turnLeft(double power) {
        LFMotor.setPower(power);
        RFMotor.setPower(power);
    }
    public void turnRight(double power) {
        LFMotor.setPower(-power);
        RFMotor.setPower(-power);
    }
    public void commitStopMoving() {
        LFMotor.setPower(0);
        RFMotor.setPower(0);
    }


    public void encoderDrive(double speed, double leftInches, double rightInches, double leftBackInches, double rightBackInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        if (opModeIsActive()) {

            newLeftTarget = LFMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = RFMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            LFMotor.setTargetPosition(newLeftTarget);
            RFMotor.setTargetPosition(newRightTarget);

            LFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            LFMotor.setPower(Math.abs(speed));
            RFMotor.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (LFMotor.isBusy() && RFMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Going to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Currently at %7d :%7d",
                        LFMotor.getCurrentPosition(),
                        RFMotor.getCurrentPosition()
                );
                telemetry.update();
            }

            // Stop all motion;
            LFMotor.setPower(0);
            RFMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }
    String formatRate(float rate) {
        return String.format("%.3f", rate);
    }


    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }


    String formatDegrees(double degrees){
        getNumDegrees(degrees);
        return String.format("%.1f", AngleUnit.DEGREES.normalize(degrees));
    }


    void getNumDegrees(double stuff) {
        degrees = stuff;
    }


    void navxTurn(double target) {
        AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        String heading = formatAngle(angles.angleUnit, angles.firstAngle);

        while (degrees > target + 2 || degrees < target - 2) {
            if (!opModeIsActive())
            {
                return;
            }

            rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            if (degrees > target + 2) {
                RFMotor.setPower(-BOT_SPEED);
                LFMotor.setPower(BOT_SPEED);
            } else if (degrees < target - 2) {
                RFMotor.setPower(BOT_SPEED);
                LFMotor.setPower(-BOT_SPEED);
            } else {

                RFMotor.setPower(0);
                LFMotor.setPower(0);
            }
            idle();
        }
        telemetry.addData("Done Turning","");

        sleep(1500);

        telemetry.log().clear();
    }


}
