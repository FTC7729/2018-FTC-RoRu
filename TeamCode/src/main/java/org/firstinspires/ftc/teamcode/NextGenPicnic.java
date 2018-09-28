
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.NextGenHardwareMap;

@TeleOp(name="NextGenPicnic", group="TeleOp")
public class NextGenPicnic extends OpMode
{
    NextGenHardwareMap robot = new NextGenHardwareMap();
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        this.robot.init(hardwareMap);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        final int ELEVATE_HIGH = 5010;
        final int ELEVATE_LOW = 50;
        final int LIFT_TILT_LOW = 9446;
        final int LIFT_TILT_MID = 6262;
        final int LIFT_TILT_HIGH = 50;
        if (gamepad1.left_stick_y > 0.1 || gamepad1.left_stick_y < -0.1) {
            robot.LFMotor.setPower(gamepad1.left_stick_y * 0.75);
            robot.LBMotor.setPower(gamepad1.left_stick_y * 0.75);
        } else {
            robot.LFMotor.setPower(0);
            robot.LBMotor.setPower(0);
        }   
        if (gamepad1.right_stick_y > 0.1 || gamepad1.right_stick_y < -0.1) {
            robot.RFMotor.setPower(-1* (gamepad1.right_stick_y * 0.75));
            robot.RBMotor.setPower(-1* (gamepad1.right_stick_y * 0.75));
        } else {
            robot.RFMotor.setPower(0);
            robot.RBMotor.setPower(0);
        }
        if (gamepad1.left_trigger > .2) {
            robot.Collector.setPower(0.5);

        } else if (gamepad1.right_trigger > .2) {
            robot.Collector.setPower(-0.5);
        } else {
            robot.Collector.setPower(0);
        }
        if (gamepad1.right_bumper) {
            robot.LiftRotation.setPower(0.5);
        } else if (gamepad1.left_bumper) {
            robot.LiftRotation.setPower(-0.5);
        } else {
            robot.LiftRotation.setPower(0);
        }
        if (gamepad1.dpad_up) {
            robot.LiftHeight.setPower(0.5);
        } else if (gamepad1.dpad_down) {
            robot.LiftHeight.setPower(-0.5);
        } else {
            robot.LiftHeight.setPower(0);
        }

    }


}
