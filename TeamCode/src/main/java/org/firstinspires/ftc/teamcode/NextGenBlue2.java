package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.dogecv.HawkeyeDetector;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

@Autonomous(name = "NextGen Blue2 Depot",group = "Autonomous")
//@Disabled
public class NextGenBlue2 extends NextGenAutonomousHardwareMap {


    VectorF translation;
    Orientation rotation;
        // BoxyHardwareMap robot = new BoxyHardwareMap();
        private ElapsedTime runtime = new ElapsedTime();
        GoldAlignDetector align;

        private static final float mmPerInch        = 25.4f;
        private OpenGLMatrix lastLocation = null;
        boolean targetVisible;
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
            align.alignSize = 240;
            hawkeye = new HawkeyeDetector();
            hawkeye.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
            //align.isNextGen = true;
            /*vuforia.setDogeCVDetector(align);
            vuforia.enableDogeCV();
            vuforia.showDebug();
            vuforia.start();*/

            align.enable();
            sleep(60);
            int state = 9;
            //wait till start here in the this place
            waitForStart();
            targetVisible = false;

            //Loop through trackables - if we find one, get the location


            // Provide feedback as to where the robot is located (if we know).

            // Update telemetry

            if (state == 9) {
                telemetry.addData("State", "9");
                telemetry.update();
                setCollectorPosition(COLLECTOR_LIFT_UPRIGHT, 0.3);
                state = 10;
            }

            telemetry.update();
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if(state == 10) {

                telemetry.addData("State","10");
                telemetry.update();
                setLiftPosition(LIFT_RUN_POSITION, 0.3);
                hookServo.setPosition(1);
                sleep(100);
                setLiftPosition(LIFT_DOWN_POSITION, 0.3);
                hookServo.setPosition(0.15);

                state = 11;
            }
            if (state == 11)  {
                telemetry.addData("State","11");
                telemetry.update();
                setCollectorPosition(COLLECTOR_LIFT_START, 0.3);
                state = 20;
            }

            //State 20
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
                hawkeye.enable();
            }

            // STATE 22 RIGHT
            if (state == 22) {
                telemetry.addData("State","22");
                telemetry.addData("Status","Turning Right");
                telemetry.update();
                navxTurnRel(-46);

                sleep(500);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, -25, -25, -25, -25, 3);
                telemetry.addData("Status","Turning Left");
                telemetry.update();
                navxTurnRel(53);
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.2, -17, -17, -17, -17, 3);
                telemetry.addData("Status","Moving Back");
                telemetry.update();
                encoderDrive(0.2, 20, 20, 20,  20, 3);
                //setCollectorPosition(COLLECTOR_LIFT_UPRIGHT, 0.3); //sampling ends
                navxTurnRel(75);

///////////// fix values
                encoderDriveHawk(0.2, -65, -65, -65, -65, 5);
                navxTurnRel(45);
                encoderDriveHawk(0.2, -17, -17, -17, -17, 3);
                setCollectorPosition(COLLECTOR_LIFT_CRATER, 0.3);
                stopMotors();
                telemetry.addLine("Done");
                telemetry.update();
            }


            // STATE 21 (the left one)
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
                telemetry.addData("Status","Moving ");
                telemetry.update();
                encoderDrive(0.2, -7,  -7, -7, -7, 3);
                telemetry.addData("Status","Moving Back");
                telemetry.update();
                encoderDrive(0.2, 7,  7, 7, 7, 3);
                //setCollectorPosition(COLLECTOR_LIFT_UPRIGHT, 0.3);
                navxTurnRel(90);
                encoderDriveHawk(0.2, -26, -26, -26, -26, 3);
                navxTurnRel(45);
                encoderDriveHawk(0.2, -24, -24, -24, -24, 3);
                setCollectorPosition(COLLECTOR_LIFT_CRATER, 0.3);
                // telemetry.addData("Status","Parking");
                //telemetry.update();
                //navxTurnRel(90);
                //state = 30;
            }



            // CENTER
            if (state == 23) {
                // CHANGE THESE VALUES
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.3, -30, -30, -30, -30, 2); // drive ito crator
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.3, 30, 30, 30, 30 , 2);
                //setCollectorPosition(COLLECTOR_LIFT_UPRIGHT, 0.3);
                navxTurnRel(60);
                encoderDriveHawk(0.2, -36, -36, -36, -36, 3);
                navxTurnRel(60);
                encoderDriveHawk(0.2, -17, -17, -17, -17, 3);
                setCollectorPosition(COLLECTOR_LIFT_CRATER, 0.3);
                stopMotors();
            }

            if (state == 30) {

            }
            align.disable();

            //translation.get(0) gets the x-coordinate


        }
    public void goForward(double power) {
        LFMotor.setPower(power);
        RFMotor.setPower(power);
        LBMotor.setPower(power);
        RBMotor.setPower(power);
    }
    public void goToX(int Xcoord, double speed) {
        while(opModeIsActive() && (translation.get(0) != Xcoord)) {
            goForward(speed);
        }
        stopMotors();
    }
    public void goToY(int Ycoord,double speed) {
        while(opModeIsActive() && (translation.get(1) != Ycoord)) {
            goForward(speed);
        }
        stopMotors();
    }
    public void getVuforiaTargetAngle(double targetHeading, double currentHeading) {
        double newnavxTarget = targetHeading - currentHeading;
        //125 - 90 =
        navxTurnRel(newnavxTarget);
    }
    public void updateVuforia() {
        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                //We found a target! Print data to telemetry
                telemetry.addData("Visible Target", trackable.getName());
                targetVisible = true;

                // getUpdatedRobotLocation() will return null if no new information is available since the last time that call was made, or if the trackable is not currently visible.
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break;
            }
        }
        if (targetVisible) {
            // Express position (translation) of robot in inches.
            translation = lastLocation.getTranslation();
            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);
            rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            // Express the rotation of the robot in degrees.
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
        }
        else {
            //No visible target
            telemetry.addData("Visible Target", "none");
        }
    }
    }