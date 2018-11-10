package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.tests.BoxyNavXTurn;

@Autonomous(name="BoxyRed2SamplingAndParking",group="tests")
@Disabled
public class BoxyRed2 extends BoxyHardwareMap {
   // SamplingOrderDetector sampler;
   private ElapsedTime runtime = new ElapsedTime();
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        //initialize the bot
        //sampler = new SamplingOrderDetector();
        // passes data to sampler to allow it to access the camera.
       // sampler.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
       // sampler.useDefaults();
        //sampler.enable();
        telemetry.update();
        int state = 0;
        if (state == 0){
            //motors, navX, and DogeCV are already initialized
            state = 10;
        }
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
            if (state == 10){
                //would do all that sampling stuff here
                state = 20;
            }

            if (state == 20){
                //more sampling
                state = 30;
            }

            //would have states 21-23 here

            if (state == 30) {
                navxTurnRel(45);
                encoderDrive(0.5,24,24,24,24,10);
                navxTurnRel(90);
                encoderDrive(0.7,72,72,72,72,15);
                }

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
            face(-45)
            Forwards
            face(-135 degrees)
            Forwards
            Stop
            */

            // STATE 22
            /*
            face(45)
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
