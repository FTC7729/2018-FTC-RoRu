package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AlanAutonomousHardwareMap;

@Autonomous(name = "Hawkeye Forwards Test",group = "tests")
public class HawkeyeForwardDemo extends AlanAutonomousHardwareMap {
    public HawkeyeDetector hawkeye;
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        hawkeye = new HawkeyeDetector();
        hawkeye.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        hawkeye.enable();
        waitForStart();
        while(opModeIsActive()) {
            if (!hawkeye.isFound() && opModeIsActive()) {
                goForward(-0.1);
            } else {
                stopMotors();
            }
        }
    }
}
