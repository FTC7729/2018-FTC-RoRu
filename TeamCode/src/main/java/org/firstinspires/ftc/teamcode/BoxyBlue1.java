package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "BoxyBlue1SamplingAndParking", group = "tests")
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
                BOT_SPEED = 0.2;
                telemetry.addData("State","31");
                telemetry.addData("Status","Turning Right");
                telemetry.update();
                navxTurnRel(-46);
                //while(!align.getAligned() && opModeIsActive()) {
                   // navxTurnRel(-2,1);
                    //sleep(50);
                //}
                // Relative
                sleep(500);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.5, -26, -26, -26, -26, 3);
                telemetry.addData("Status","Turning Left");
                telemetry.update();
                navxTurnRel(53);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.5, -18, -18, -18, -18, 2);
                stopMotors();
                telemetry.addLine("Done");
                telemetry.update();
            }


            // STATE 32
            if (state == 32) {
                // Relative
                navxTurnRel(46);
                // CHANGE THESE VALUES
                encoderDrive(0.5, -26, -26, -26, -26, 2);
                navxTurnRel(-53);
                // CHANGE THESE VALUES
                encoderDrive(0.5, -18, -18, -18, -18, 2);
                stopMotors();
            }

            // STATE 33
            if (state == 33) {
                // CHANGE THESE VALUES
                encoderDrive(0.5, -30, -30, -30, -30, 2);
                stopMotors();
            }
        align.disable();


    }
}
