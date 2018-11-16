package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "CollectorTestByLogan", group = "Teleop")
//@Disabled
public class CollectorTestByLogan extends ServoTestHardwareBap {
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
        double position = hookServo.getPosition();

        if (isButtonA) {
            position += INCREMENT;
            hookServo.setPosition(position);
            //backServo.setDirection(DcMotorSimple.Direction.REVERSE);
            //backServo.setPower(1);
        } else if (isButtonB) {
            position -= INCREMENT;
            hookServo.setPosition(position);
            //backServo.setDirection(DcMotorSimple.Direction.FORWARD);
            //backServo.setPower(1);
        }
        telemetry.addData("Hook Servo Position",String.format("%.2f", hookServo.getPosition()));
    }

}
