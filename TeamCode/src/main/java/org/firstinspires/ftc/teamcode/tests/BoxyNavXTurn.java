package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.BoxyHardwareMap;

@Autonomous(name="NavX Drive RoRu",group = "tests")
//@Disabled //comment out to enable opmode
public class BoxyNavXTurn extends BoxyHardwareMap {
   // BoxyHardwareMap robot = new BoxyHardwareMap();
    private ElapsedTime runtime = new ElapsedTime();
    final double BOT_SPEED = 0.4;
    final int THRESHOLD = 2;
    @Override
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
