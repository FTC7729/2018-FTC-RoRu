package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MineralBoxTestTeleOp", group = "Teleop")
//@Disabled
public class MineralBoxTestTeleOp extends MineralBoxTest {
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
        double position = boxServo.getPosition();

        if (isButtonA) {
            position += INCREMENT;
            boxServo.setPosition(position);
        } else if (isButtonB) {
            position -= INCREMENT;
            boxServo.setPosition(position);
        }
        telemetry.addData("Box Servo Position",String.format("%.2f", boxServo.getPosition()));
    }

}
