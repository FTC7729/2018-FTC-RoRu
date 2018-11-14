package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Placeholder Blue 1 Test", group = "tests")
public class PlaceholderBlue1 extends PlaceholderAutonomousHardware {
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
                    telemetry.addData("Loading State", "32");
                } else {
                    // if the gold is already aligned; in the center
                    state = 33;
                    telemetry.addData("Loading State", "33");
                }
                telemetry.update();
                sleep(200);
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
                encoderDrive(0.2, -26, -26, -26, -26, 3);
                telemetry.addData("Status","Turning Left");
                telemetry.update();
                navxTurnRel(53);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, -22, -22, -22, -22, 3);
                stopMotors();
                telemetry.addLine("Done");
                telemetry.update();
            }


            // STATE 32
            if (state == 32) {
                telemetry.addData("State","32");
                telemetry.addData("Status","Turning Left");
                telemetry.update();
                // Relative
                navxTurnRel(46);
                telemetry.addData("Status","Moving");
                telemetry.update();
                // CHANGE THESE VALUES
                encoderDrive(0.2, 26, 26, 26, 26, 3);
                telemetry.addData("Status","Turning Right");
                telemetry.update();
                navxTurnRel(-53);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, 22, 22, 22, 22, 3);
                stopMotors();
            }

            // STATE 33
            if (state == 33) {
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, 35, 35, 35, 35, 2);
                stopMotors();
            }
        align.disable();


    }
}
