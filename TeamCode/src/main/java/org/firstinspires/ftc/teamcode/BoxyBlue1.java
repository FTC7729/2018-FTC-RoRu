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


        //wait till start here in the this place
        waitForStart();
        // continues to run until you run out of time or hit stop
        while (opModeIsActive()) {
            encoderDrive(0.5,5,5,3);
            // STATE 1
            /*
            change allign with picture, angles are awkward and won't be convenient
            AllignWithPicture
            }
            }
            State 2
            */

            // STATE 2
            /*
            Initialize NavX
            State 3
            */

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

            // STATE 4
            /*
            face(-45)
            Forwards
            face(0 degrees)
            Forwards
            Stop
            */

            // STATE 5
            /*
            face(45)
            Forwards
            face(0 degrees)
            Forwards
            Stop
            */

            // STATE 6
            /*
            Forwards
            Stop
            */


            //get reason for idle() later
            idle();
        }
    }
        public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
            int newLeftTarget;
            int newRightTarget;

            if (opModeIsActive()) {

                newLeftTarget = LFMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
                newRightTarget = RFMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);

                LFMotor.setTargetPosition(newLeftTarget);
                RFMotor.setTargetPosition(newRightTarget);

                LFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                RFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // reset the timeout time and start motion.
                runtime.reset();
                LFMotor.setPower(Math.abs(speed));
                RFMotor.setPower(Math.abs(speed));


                // keep looping while we are still active, and there is time left, and both motors are running.
                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (LFMotor.isBusy() && RFMotor.isBusy())) {

                    // Display it for the driver.
                    telemetry.addData("Path1",  "Going to %7d :%7d :%7d :%7d", newLeftTarget,  newRightTarget);
                    telemetry.addData("Path2",  "Currently at %7d :%7d :%7d :%7d",
                            LFMotor.getCurrentPosition(),
                            RFMotor.getCurrentPosition()
                    );
                    telemetry.update();
                }

                // Stop all motion;
                LFMotor.setPower(0);
                RFMotor.setPower(0);

                // Turn off RUN_TO_POSITION
                LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            }
        }
}
