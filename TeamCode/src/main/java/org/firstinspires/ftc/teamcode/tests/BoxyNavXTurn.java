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
    NavxMicroNavigationSensor navxMicro;
    IntegratingGyroscope gyro;
    private ElapsedTime runtime = new ElapsedTime();
    final double BOT_SPEED = 0.4;
    final int THRESHOLD = 2;
    @Override
    public void runOpMode() throws InterruptedException {
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxMicro;
        init(hardwareMap);

        LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        runtime.reset();
        while (navxMicro.isCalibrating())  {
            telemetry.addData("Gyro", "Calibrating... %s", Math.round(runtime.seconds())%2==0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }
        telemetry.addData("Gyro","Calibrated! Press Start!");
        telemetry.update();
        waitForStart();
        sleep(3000);
        navxTurn(90);
        sleep(3000);
        navxTurn(-90);
    }
    public void navxTurn(double target) {
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        while(opModeIsActive()) {
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading",angles.firstAngle+" degrees");
            if(angles.firstAngle < target - THRESHOLD) {
                telemetry.addData("Status","Turning Left");
                turnLeft(BOT_SPEED);
            } else if(angles.firstAngle > target + THRESHOLD) {
                telemetry.addData("Status","Turning Right");
                turnRight(BOT_SPEED);
            } else {
                stopMotors();
                break;
            }
            telemetry.update();
            idle();
        }
    }
}
