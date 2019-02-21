package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AlanAutonomousHardwareMap;
import org.firstinspires.ftc.teamcode.NextGenAutonomousHardwareMap;

@Autonomous(name="Centered Drive Test",group="tests")
public class CenteredDetectorDriveTest extends AlanAutonomousHardwareMap {
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        center = new HawkeyeCenterDetector();
        center.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        center.enable();
        waitForStart();
        encoderDriveCenter(0.1,-100,-100,-100,-100,3);
    }
}
