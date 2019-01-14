package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AlanAutonomousHardwareMap;

@Autonomous(name = "Hawkeye EncoderDrive Stop Demo",group = "tests")
public class HawkeyeStopEncoderDemo extends AlanAutonomousHardwareMap {
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        hawkeye.enable();
        waitForStart();
        sleep(1000);
        encoderDriveHawk(0.15,-100,-100,-100,-100,8);
    }
}
