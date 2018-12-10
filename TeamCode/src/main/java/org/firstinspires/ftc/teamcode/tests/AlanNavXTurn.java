package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AlanAutonomousHardwareMap;

@Autonomous(name="Alan NavX Drive",group="tests")
public class AlanNavXTurn extends AlanAutonomousHardwareMap {
    ElapsedTime runtime = new ElapsedTime();
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);

        LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        runtime.reset();
        telemetry.addData("Gyro", "Calibrated! Press Start!");
        telemetry.update();
        waitForStart();
        sleep(2000);
        navxTurn(90);
        sleep(2000);
        navxTurn(-90,5);
        sleep(2000);
        navxTurnRel(90);
    }
}
