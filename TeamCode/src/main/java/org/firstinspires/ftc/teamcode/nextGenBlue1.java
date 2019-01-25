package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

@Autonomous(name = "nextGenBlue1",group = "Autonomous")
public class nextGenBlue1 extends NextGenAutonomousHardwareMap {


    VectorF translation;
    Orientation rotation;
        // BoxyHardwareMap robot = new BoxyHardwareMap();
        private ElapsedTime runtime = new ElapsedTime();
        GoldAlignDetector align;
        public final int LIFT_RUN_POSITION = -4032;
        public final int LIFT_DOWN_POSITION = -1000;
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
            align.init(hardwareMap.appContext, CameraViewDisplay.getInstance(),0,true);
            align.alignSize = 240;
            align.yellowFilter = new LeviColorFilter(LeviColorFilter.ColorPreset.YELLOW, 100);
            vuforia.setDogeCVDetector(align);
            vuforia.enableDogeCV();
            vuforia.showDebug();
            vuforia.start();
            sleep(60);
            int state = 10;
            //wait till start here in the this place
            waitForStart();
            targetVisible = false;

            //Loop through trackables - if we find one, get the location


            // Provide feedback as to where the robot is located (if we know).

            // Update telemetry
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


            // STATE 32 (the left one)
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

            // STATE 33 (the center one)
            if (state == 23) {
                // CHANGE THESE VALUES
                updateVuforia();
                telemetry.addData("Status","Moving");
                telemetry.update();
                encoderDrive(0.15, -20,  -20, -20, -20, 3);
                updateVuforia();
                encoderDrive(0.15, 20,  20, 20, 20, 3);
                updateVuforia();
                navxTurnRel(50);
                updateVuforia();
                sleep(80);
              //  double thirdAngleVuphoria = 0;

                //        thirdAngleVuphoria = rotation.thirdAngle;

               // getVuforiaTargetAngle(125, thirdAngleVuphoria);
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
                encoderDrive(0.2, -35, -35, -35, -35, 3);
                telemetry.addData("Status","Realign for Depot");
                telemetry.update();
                navxTurnRel(20);
                telemetry.addData("Status","Move Towards Depot");
                telemetry.update();
                encoderDrive(0.2, -20, -20, -20, -20, 3);
                telemetry.addData("Status","Align for Dropping");
                telemetry.update();
                navxTurnRel(-90);
                sleep(700);
                sleep(750);
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