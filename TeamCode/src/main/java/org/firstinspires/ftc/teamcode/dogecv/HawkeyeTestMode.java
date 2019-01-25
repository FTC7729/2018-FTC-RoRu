package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Hawkeye Test Mode",group = "tests")
@Disabled
public class HawkeyeTestMode extends LinearOpMode {
    private HawkeyeDetector collisionDetect;
    public void runOpMode() {
        collisionDetect = new HawkeyeDetector();
        collisionDetect.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        collisionDetect.enable();
        waitForStart();
        while(opModeIsActive()) {
            if(collisionDetect.isFound()) {
                telemetry.addData("Status","Stop the bot! Proximity alert!");
                telemetry.addData("Area", String.format("%2f", collisionDetect.getFoundRect().area()));
            } else {
                telemetry.addData("Status","All clear!");
                telemetry.addData("Area","N/A");
            }
            telemetry.update();
        }
    }
}
