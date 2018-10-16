package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="BoxyBlue1SamplingAndParking",group="tests")
public class BoxyBlue1 extends BoxyHardwareMap {
        // BoxyHardwareMap robot = new BoxyHardwareMap();
       private ElapsedTime runtime = new ElapsedTime();
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        //initialize the bot
        SamplingOrderDetector sampler = new SamplingOrderDetector();
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
        int state = 10;


        //wait till start here in the this place
        waitForStart();
        // continues to run until you run out of time or hit stop
        while (opModeIsActive()) {
            encoderDrive(0.5,5,5,3,3,2);
            // STATE 1
            /*
            change align with picture, angles are awkward and won't be convenient
            AlignWithPicture
            }
            }
            State 2
            */

            // STATE 2
            /*
            Initialize NavX
            State 3
            */
            //state 2
            navx.initialize();
            state = 30;
            //end state 2
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

            //State 30
            while (state == 30){

            }

            // STATE 31
            /*
            face(-45)
            Forwards
            face(0 degrees)
            Forwards
            Stop
            */

            // STATE 32
            /*
            face(45)
            Forwards
            face(0 degrees)
            Forwards
            Stop
            */

            // STATE 33
            /*
            Forwards
            Stop
            */


            //get reason for idle() later
            idle();
        }
    }

}
