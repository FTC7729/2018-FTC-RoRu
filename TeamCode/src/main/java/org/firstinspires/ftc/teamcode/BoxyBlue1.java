package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "BoxySampling", group = "tests")
public class BoxyBlue1 extends BoxyHardwareMap {
    // BoxyHardwareMap robot = new BoxyHardwareMap();
    private ElapsedTime runtime = new ElapsedTime();
    GoldAlignDetector align;
    // BoxyHardwareMap robot = new BoxyHardwareMap();
    // private ElapsedTime runtime = new ElapsedTime();
    //GoldAlignDetector align;

    public void runOpMode() throws InterruptedException {
        // STATE 0
        //initialize the motors and variables
        // This MUST be here or else there are errors in loading the motors
        // which end up causing encoderDrive to loop.
        init(hardwareMap);
        align = new GoldAlignDetector();
        align.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        align.alignSize = 120;
        align.enable();

        int state = 10;


        //wait till start here in the this place
        waitForStart();

        // STATE 1

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
        //state 2
        state = 30;
        //end state 2
        //STATE 3
            /*
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
        //State 30
        if (state == 30) {
            //navxTurn(0,1);
            //if the gold IS on screen
            //if (align.isFound()) {
                // if the gold is to the right of the window of "aligned";
                // if the robot needs to turn right to be aligned with the gold
                if (align.getXPosition() > align.xMax()) {
                    state = 31;
                    telemetry.addData("Loading State", "31");
                } else if (align.getXPosition() < align.xMin()) {
                    // if the gold is to the left of the window of "aligned";
                    // if the robot needs to turn left to be aligned with the gold
                    state = 32;
                    telemetry.addData("State", "32");
                } else {
                    // if the gold is already aligned; in the center
                    state = 33;
                    telemetry.addData("State", "33");
                }
                telemetry.update();
            //}

//
//                //if the gold is NOT on screen
//                else {
//                    //figure this out later
//                }
//
        }

            // STATE 31
            if (state == 31) {
                telemetry.addData("State","31");
                telemetry.addData("Status","Turning Right");
                telemetry.update();
                navxTurnRel(-43);
                //while(!align.getAligned() && opModeIsActive()) {
                   // navxTurnRel(-2,1);
                    //sleep(50);
                //}
                // Relative
                sleep(500);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, -28, -28, -28, -28, 3);
                telemetry.addData("Status","Turning Left");
                telemetry.update();
                navxTurnRel(50);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Parking");
                telemetry.update();
                encoderDrive(0.2, -22, -22, -22, -22, 3);
                stopMotors();
                telemetry.addLine("Done");
                telemetry.update();
            }


            // STATE 32
            if (state == 32) {
                // Relative
                telemetry.addData("State","32");
                telemetry.addData("Status","Turning Left");
                telemetry.update();
                navxTurnRel(43);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, -28, -28, -28, -28, 2);
                telemetry.addData("Status","Turning Right");
                telemetry.update();
                navxTurnRel(-50);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Parking");
                telemetry.update();
                encoderDrive(0.2, -22, -22, -22, -22, 3);
                stopMotors();
            }

            // STATE 33
            if (state == 33) {
                // CHANGE THESE VALUES
                telemetry.addLine("Parking...");
                telemetry.update();
                sleep(50);
                encoderDrive(0.2, -45, -45, -45, -45, 4);
                stopMotors();
            }
        align.disable();


    }
}
