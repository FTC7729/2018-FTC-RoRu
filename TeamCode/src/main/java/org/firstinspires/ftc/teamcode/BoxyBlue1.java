package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="BoxyBlue1SamplingAndParking",group="tests")
public class BoxyBlue1 extends BoxyHardwareMap {
        // BoxyHardwareMap robot = new BoxyHardwareMap();
       private ElapsedTime runtime = new ElapsedTime();
       GoldAlignDetector align;
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        //initialize the bot
        align = new GoldAlignDetector();
        align.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        align.useDefaults();
        align.alignSize = 120;
        align.enable();
        telemetry.update();
        init(hardwareMap);
        // STATE 0
        /*
        Initialize motors
        Initialize variables
        State 1
         */
        double xMaxAlign;
        double xMinAlign;
        // The parameters for a gold piece to be considered "aligned".
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

                //if the gold IS on screen
                if (align.isFound()) {
                    // if the gold is to the right of the window of "aligned";
                    // if the robot needs to turn right to be aligned with the gold
                    if (align.getXPosition() > align.xMax()){
                            state = 31;
                    }

                    // if the gold is to the left of the window of "aligned";
                    // if the robot needs to turn left to be aligned with the gold
                    else if (align.getXPosition() < align.xMin()){
                        state = 32;
                    }

                    // if the gold is already aligned; in the center
                    else {
                        state = 33;
                    }
                }

                //if the gold is NOT on screen
                else {
                    //figure this out later
                }
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

            // gold in center
            if (state == 33) {
                double timeSecs = ElapsedTime.SECOND_IN_NANO / 1000000000;
                LFMotor.setPower(0.75);
                LBMotor.setPower(0.75);
                RFMotor.setPower(0.75);
                RBMotor.setPower(0.75);
                while (state == 33) {
                    //wait for 5 seconds
                    if ((ElapsedTime.SECOND_IN_NANO/1000000000) - timeSecs > 5){
                        state = 40;
                    }
                }
            }
            stopMotors();
            if (state != 40){
                telemetry.addLine("State made it to the end; state is not 40.");
            }
            idle();
        }
    }

}
