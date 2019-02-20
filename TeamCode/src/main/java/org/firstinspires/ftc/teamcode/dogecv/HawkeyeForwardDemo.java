package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.AlanAutonomousHardwareMap;
import org.firstinspires.ftc.teamcode.NextGenAutonomousHardwareMap;

@Autonomous(name = "Hawkeye EncoderDriveHawk Test",group = "tests")
//@Disabled
public class HawkeyeForwardDemo extends NextGenAutonomousHardwareMap {
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        hawkeye.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        hawkeye.enable();
        waitForStart();
        while(opModeIsActive()) {
            encoderDriveHawk(0.2,100,100,100,100,5);
        }
    }
}
