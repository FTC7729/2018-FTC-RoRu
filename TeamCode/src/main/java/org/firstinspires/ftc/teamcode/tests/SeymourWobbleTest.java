package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="WEEBLE test",group="tests")
public class SeymourWobbleTest  extends LinearOpMode {
    public static final double START_WEEBLE = 0;
    public static final double END_WEEBLE = 0.45;
    public Servo weebleServ;
    public void runOpMode() {
        init(hardwareMap);
        waitForStart();
        weebleServ.setPosition(START_WEEBLE);
        sleep(700);
        weebleServ.setPosition(END_WEEBLE);
        sleep(750);
        weebleServ.setPosition(START_WEEBLE);
    }
    public void init(HardwareMap hardwareMap) {
        weebleServ = hardwareMap.servo.get("weeble");
    }
}
