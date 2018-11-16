package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

//cool

//import chawks.hardware.Boxy;
//import chawks.hardware.NavXMicro;

/**
 * Created on 06,October,2018
 * FTC 7729
 * c.hawks.ftc7729@gmail.com
 */

@Autonomous(name="EncoderDriveTest", group="safezone")
@Disabled
public class EncoderDriveTest extends BoxyHardwareMap{
    @Override
    public void runOpMode() throws InterruptedException {

        //.init(hardwareMap);

        init(hardwareMap); //must have this
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();



        //  LFMotor.setPower(left);
        //  RFMotor.setPower(right);
        //  LBMotor.setPower(left);
        //  RBMotor.setPower(right);

       LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      
       LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       RFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       

        idle();

        LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        

        telemetry.addData("Path0",  "Starting at %7d :%7d",
                LFMotor.getCurrentPosition(),
                RFMotor.getCurrentPosition()
               
        );
        telemetry.update();
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxMicro;
        telemetry.log().add("Gyro Calibrating. Do Not Move!");

        // Wait until the gyro calibration is complete
        timer.reset();
        while (navxMicro.isCalibrating())  {
            telemetry.addData("calibrating", "%s", Math.round(timer.seconds())%2==0 ? "|.." : "..|");
            telemetry.update();
            sleep(50);
        }
        telemetry.log().clear(); telemetry.log().add("Gyro Calibrated. Press Start.");
        telemetry.clear(); telemetry.update();

        // Navx.initAndCalibrate(hardwareMap);
        //ON KBOT WAAAAAAAAAAY TOO SPEEDY
        waitForStart();
        telemetry.log().clear();
        //Start Code after here
        encoderDrive(.5,-.5,.5,-.5,.5,4);
        //speed 5 is too fast, less than 7 dist is too short.
        encoderDrive(.5,-7,-7,-7,-7,4);
        // Navx.turn(90.0, 0.1, robot);
        navxTurn(90.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }


}


