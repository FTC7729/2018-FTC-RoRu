package org.firstinspires.ftc.teamcode.tests;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Gold Alignment Test Mode",group = "tests")
// @Disabled // Comment out to enable
public class GoldAlignTest extends LinearOpMode {
    GoldAlignDetector align;
    public void runOpMode() {
        telemetry.addData("Status","Ready");
        align = new GoldAlignDetector();
        align.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        align.useDefaults();
        align.enable();
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if(align.getAligned()) {
                telemetry.addData("Status","Aligned!");
            } else {
                telemetry.addData("Status","Not Aligned.");
            }
            telemetry.update();
        }
    }
}
