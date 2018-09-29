package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;

@Autonomous(name="BoxyBlue2SamplingAndParking",group="tests")
public class BoxyBlue2 extends LinearOpMode {
    SamplingOrderDetector sampler;
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        //initialize the bot
        sampler = new SamplingOrderDetector();
        // passes data to sampler to allow it to access the camera.
        sampler.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        sampler.useDefaults();
        sampler.enable();
        telemetry.update();

        // STATE 0
        /*
        Initialize motors
        Initialize NavX
        Initialize DogeCV
         */



        //wait till start here in the this place
        waitForStart();
        // continues to run until you run out of time or hit stop
        while(opModeIsActive()) {

            // STATE 10
            /*
            While(goldNotInView){
            Forward
            if(gold.inView){
            Stop
            Break
            }
            }
            State 20
            */

            // STATE 20
            /*
            Find Location
            if(goldposition == right){
            State 21
            }
            if(goldposition == left){
            State 22
            }
            if(goldposition == middle){
            State 23
            }
            */

            // STATE 21
            /*
            TurnRightUntil(gold.Straight);
            Forwards
            face(-135 degrees)
            Forwards
            Stop
            */

            // STATE 22
            /*
            TurnLeftUntil(gold.Straight);
            Forwards
            face(135 degrees)
            Forwards
            Stop
            */

            // STATE 23
            /*
            Forwards
            BackSome
            face(-45 degrees)
            ForwardSome
            face(-135)
            Forwards
            Stop
            */


            //get reason for idle() later
            idle();
        }
    }
}
