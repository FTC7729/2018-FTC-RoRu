package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AlanAutonomousHardwareMap;


@Autonomous(name="Go to test",group="tests")
public class LiftGoToTest extends AlanAutonomousHardwareMap {
    public final int LIFT_LOWER_TARGET = 1000;
    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();
        setLiftPosition(LIFT_LOWER_TARGET,0.25);
        sleep(2000);
        setLiftPosition(0,0.2);
    }
}
