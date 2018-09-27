package org.firstinspires.ftc.teamcode.tests;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;

@Autonomous(name="Sampling Order Detection Test",group="tests")
public class MineralOrderDetectionTest extends LinearOpMode {
    SamplingOrderDetector sampler;
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        sampler = new SamplingOrderDetector();
        // passes data to sampler to allow it to access the camera.
        sampler.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        sampler.useDefaults();
        sampler.enable();

        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {
            if (sampler.isFound()) {
                telemetry.addData("Status", sampler.getCurrentOrder());
            } else {
                telemetry.addData("Status", "Searching...");
            }
            telemetry.update();
            idle();
        }
    }
}
