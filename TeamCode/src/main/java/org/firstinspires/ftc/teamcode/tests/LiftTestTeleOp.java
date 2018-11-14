package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "LiftTestByRobert", group = "Teleop")
public class LiftTestTeleOp extends LiftTest {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        init(hardwareMap);

    }

    @Override
    public void loop() {
        final double INCREMENT = 0.01;
        boolean isButtonB= gamepad1.b;
        boolean isButtonA = gamepad1.a;
        double speed = 0.5;
        float leftStickY = Range.clip(-gamepad1.left_stick_y, -1, 1);

        if (isButtonA) {
           liftMotor.setPower(speed);
        } else if (isButtonB) {
            liftMotor.setPower(-speed);
        }
        telemetry.addData("Lift Position",String.format("%7d", liftMotor.getCurrentPosition()));
        telemetry.update();
    }

}
