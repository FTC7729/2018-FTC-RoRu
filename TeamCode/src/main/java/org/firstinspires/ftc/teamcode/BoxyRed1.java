package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;

@Autonomous(name="BoxyRed1SamplingAndParking",group="tests")
public class BoxyRed1 extends LinearOpMode {
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
        Initialize variables
        State 1
         */



        //wait till start here in the this place
        waitForStart();
        // continues to run until you run out of time or hit stop
        while(opModeIsActive()) {

            // STATE 1
            /*
            change allign with picture, angles are awkward and won't be convenient
            AllignWithPicture
            }
            }
            State 2
            */

            // STATE 2
            /*
            Initialize NavX
            State 3
            */

            //STATE 3
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

            // STATE 4
            /*
            face(-45)
            Forwards
            face(0 degrees)
            Forwards
            Stop
            */

            // STATE 5
            /*
            face(45)
            Forwards
            face(0 degrees)
            Forwards
            Stop
            */

            // STATE 6
            /*
            Forwards
            Stop
            */


            //get reason for idle() later
            idle();
        }
    }
}
