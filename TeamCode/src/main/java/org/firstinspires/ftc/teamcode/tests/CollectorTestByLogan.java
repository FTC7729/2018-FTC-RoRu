package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "CollectorTestByLogan", group = "Teleop")
public class CollectorTestByLogan extends ServoTestHardwareBap {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        init(hardwareMap);

    }

    @Override
    public void loop() {

        boolean isButtonB= gamepad1.b;
        boolean isButtonA = gamepad1.a;

        if (isButtonA) {
            frontServo.setDirection(DcMotorSimple.Direction.FORWARD);
            frontServo.setPower(1);
            backServo.setDirection(DcMotorSimple.Direction.REVERSE);
            backServo.setPower(1);
        }
        if (isButtonB) {
            frontServo.setDirection(DcMotorSimple.Direction.REVERSE);
            frontServo.setPower(1);
            backServo.setDirection(DcMotorSimple.Direction.FORWARD);
            backServo.setPower(1);
        }
        if(!isButtonA || !isButtonB){
            frontServo.setPower(0);
            backServo.setPower(0);
        }
    }

}
