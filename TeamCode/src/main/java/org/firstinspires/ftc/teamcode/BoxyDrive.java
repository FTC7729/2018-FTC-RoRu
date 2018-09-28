
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="BoxyDrive", group="TeleOp")
public class BoxyDrive extends OpMode
{
    BoxyHardwareMap robot = new BoxyHardwareMap();
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
            if (gamepad1.left_stick_y > 0.1 || gamepad1.left_stick_y < -0.1) {
                robot.RFMotor.setPower(gamepad1.left_stick_y * 0.75);
            } else {
                robot.RFMotor.setPower(0);
            }
            if (gamepad1.right_stick_y > 0.1 || gamepad1.right_stick_y < -0.1) {
                robot.LFMotor.setPower(-1* (gamepad1.right_stick_y * 0.75));
            } else {
                robot.LFMotor.setPower(0);
            }

    }


}
