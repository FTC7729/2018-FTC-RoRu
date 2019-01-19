package org.firstinspires.ftc.teamcode.Seymour;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.dogecv.HawkeyeDetector;

@Autonomous(name = "SEEMORE",group = "Hawkeye")
public class SeymourHawkeye extends SeymourHardwareMap {
    public HawkeyeDetector hawkeye;
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        hawkeye = new HawkeyeDetector();
        hawkeye.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        hawkeye.enable();
        waitForStart();
        while(opModeIsActive()) {
            if (!hawkeye.isFound() && opModeIsActive()) {
                seymourMotor.setPower(1);
                telemetry.addData("Status","Running, all clear!");
            } else {
                seymourMotor.setPower(0);
                telemetry.addData("Status","Target detected, bot stopped!");
            }
        }
    }
}
