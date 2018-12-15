package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Alan Blue 1",group = "Autonomous")
public class AlanBlue1 extends AlanAutonomousHardwareMap {
        // BoxyHardwareMap robot = new BoxyHardwareMap();
        private ElapsedTime runtime = new ElapsedTime();
        GoldAlignDetector align;
        public final int LIFT_RUN_POSITION = -2310;
        public final int LIFT_DOWN_POSITION = -1000;
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
            align.alignSize = 180;
            int state = 10;
            //wait till start here in the this place
            align.enable();
            waitForStart();
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if(state == 10) {
                telemetry.addData("State","10");
                telemetry.update();
                setLiftPosition(LIFT_RUN_POSITION, 0.3);
                hookServo.setPosition(1);
                sleep(100);
                setLiftPosition(LIFT_DOWN_POSITION, 0.3);
                hookServo.setPosition(0.15);
                state = 20;
            }

            //State 30
            if (state == 20) {
                sleep(500);
                //if the gold IS on screen
                //if (align.isFound()) {
                if ((align.getXPosition() < align.xMax() && align.getXPosition() > align.xMin())||(align.getAligned())) {
                    // if already aligned (in center), just go straight
                    state = 23;
                    telemetry.addData("Loading State", "23");
                } else if (align.getXPosition() < align.xMin()) {
                    // if the gold is to the left of the window of "aligned";
                    // if the robot needs to turn left to be aligned with the gold
                    state = 21;
                    telemetry.addData("Loading State", "21");
                } else {
                    // if the gold is on the right (blindspot), turn right
                    state = 22;
                    telemetry.addData("Loading State", "22");
                }
                telemetry.update();
                sleep(200);
                align.disable();
            }

            // STATE 31
            if (state == 22) {
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
            if (state == 21) {
                telemetry.addData("State","21");
                telemetry.addData("Status","Turning Left");
                telemetry.update();
                // Relative
                navxTurnRel(46);
                telemetry.addData("Status","Moving");
                telemetry.update();
                // CHANGE THESE VALUES
                encoderDrive(0.2, -26, -26, -26, -26, 3);
                telemetry.addData("Status","Turning Right");
                telemetry.update();
                navxTurnRel(-53);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, -7,  -7, -7, -7, 3);
                telemetry.addData("Status","Turning Left");
                telemetry.update();
                navxTurnRel(90);
                state = 30;
            }

            // STATE 33
            if (state == 23) {
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, -35, -35, -35, -35, 2);
                stopMotors();
            }

            if (state == 30) {
                telemetry.addData("Status","Move Towards Wall");
                telemetry.update();
                encoderDrive(0.2, -5, -5, -5, -5, 3);
                telemetry.addData("Status","Turning Towards Depot");
                telemetry.update();
                navxTurnRel(20);
                telemetry.addData("Status","Move Towards Depot");
                telemetry.update();
                encoderDrive(0.2, -30, -30, -30, -30, 3);
                telemetry.addData("Status","Realign for Depot");
                telemetry.update();
                navxTurnRel(20);
                telemetry.addData("Status","Move Towards Depot");
                telemetry.update();
                encoderDrive(0.2, -20, -20, -20, -20, 3);
                telemetry.addData("Status","Align for Dropping");
                telemetry.update();
                navxTurnRel(-90);
                weebleServ.setPosition(START_WEEBLE);
                sleep(700);
                weebleServ.setPosition(END_WEEBLE);
                sleep(750);
                weebleServ.setPosition(START_WEEBLE);
                telemetry.addData("Status","Turning Towards Crater");
                telemetry.update();
                navxTurnRel(-70);
                telemetry.addData("Status","Move Towards Crater");
                telemetry.update();
                encoderDrive(0.2, -50, -50, -50, -50, 3);
                telemetry.addData("Status","Adjust For Crater");
                telemetry.update();
                navxTurnRel(-20);
                telemetry.addData("Status","Move Towards Crater");
                telemetry.update();
                encoderDrive(0.2, -50, -50, -50, -50, 3);
                stopMotors();
            }
            align.disable();

        }
    }