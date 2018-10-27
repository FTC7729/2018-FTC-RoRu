package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "BoxyBlue1SamplingAndParking", group = "tests")
public class BoxyBlue1 extends BoxyHardwareMap {
<<<<<<< HEAD
        // BoxyHardwareMap robot = new BoxyHardwareMap();
       private ElapsedTime runtime = new ElapsedTime();
       GoldAlignDetector align;
=======
    // BoxyHardwareMap robot = new BoxyHardwareMap();
    private ElapsedTime runtime = new ElapsedTime();
    GoldAlignDetector align;
>>>>>>> a5360a771ac01b956d0a7b8635b0ab1e12fd0f70

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        // STATE 0
        //initialize the motors and variables
        align = new GoldAlignDetector();
        align.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        align.useDefaults();
        align.alignSize = 120;
        align.enable();
        telemetry.update();
        init(hardwareMap);

        // The parameters for a gold piece to be considered "aligned".
        double xMaxAlign;
        double xMinAlign;

        int state = 10;


        //wait till start here in the this place
        waitForStart();
        // continues to run until you run out of time or hit stop
        while (opModeIsActive()) {
<<<<<<< HEAD
            encoderDrive(0.5,20,20,20,20,2);
            // STATE 1
            /*
//            change align with picture, angles are awkward and won't be convenient
//            AlignWithPicture
//            }
//            }
//            State 2
//
//
//            // STATE 2
//            /*
//            Initialize NavX
//            State 3
//            */
//            //state 2
//            navx.initialize();
//            state = 30;
//            //end state 2
//            //STATE 3
//            /*
//            Find Location
//            if(goldposition == right){
//            State 21
//            }
//            if(goldposition == left){
//            State 22
//            }
//            if(goldposition == middle){
//            State 23
//            }
//            */
//
//            //State 30
//            while (state == 30){
//
//                //if the gold IS on screen
//                if (align.isFound()) {
//                    // if the gold is to the right of the window of "aligned";
//                    // if the robot needs to turn right to be aligned with the gold
//                    if (align.getXPosition() > align.xMax()){
//                            state = 21;
//                    }
//
//                    // if the gold is to the left of the window of "aligned";
//                    // if the robot needs to turn left to be aligned with the gold
//                    else if (align.getXPosition() < align.xMin()){
//                        state = 22;
//                    }
//
//                    // if the gold is already aligned; in the center
//                    else {
//                        state = 23;
//                    }
//                }
//
//                //if the gold is NOT on screen
//                else {
//                    //figure this out later
//                }
//
//            }

=======
            telemetry.addLine("We begin in state " + state);
            // STATE 10
            if (state == 10)
            {
                // FORWARD (CHANGE)
                encoderDrive(0.5, 1, 1, 1, 1, 3);
                state = 20;
            }
            telemetry.addLine("State made it to the end; We are in state " + state);

            // STATE 20
            if (state == 20) {
                navx.initialize();
                state = 30;
            }
            telemetry.addLine("State made it to the end; We are in state " + state);

            // STATE 30
            while (state == 30) {

                //If the gold IS on screen
                if (align.isFound()) {
                    // If the gold is to the right of the window of "aligned";
                    // If the robot needs to turn right to be aligned with the gold
                    if (align.getXPosition() > align.xMax()) {
                        state = 31;
                    }

                    // If the gold is to the left of the window of "aligned";
                    // If the robot needs to turn left to be aligned with the gold
                    else if (align.getXPosition() < align.xMin()) {
                        state = 32;
                    }

                    // If the gold is already aligned; in the center
                    else {
                        state = 33;
                    }
                }

                // If the gold is NOT on screen
                else {
                    // PLACEHOLDER
                }
            }
>>>>>>> a5360a771ac01b956d0a7b8635b0ab1e12fd0f70

            // STATE 31
            if (state == 31)
            {
                // Relative
                navxTurnRel(-45);
                // CHANGE THESE VALUES
                encoderDrive(0.5,1,1,1,1,2);
                navxTurnRel(45);
                // CHANGE THESE VALUES
                encoderDrive(0.5, 1, 1, 1, 1, 2);
                stopMotors();
            }

            // STATE 32
            if (state == 32)
            {
                // Relative
                navxTurnRel(45);
                // CHANGE THESE VALUES
                encoderDrive(0.5,1,1,1,1,2);
                navxTurnRel(-45);
                // CHANGE THESE VALUES
                encoderDrive(0.5, 1, 1, 1, 1, 2);
                stopMotors();
            }

            // STATE 33
            if (state == 33)
            {
                // CHANGE THESE VALUES
                encoderDrive(0.5,1,1,1,1,2);
                stopMotors();
            }
            stopMotors();

        }
    }

}
