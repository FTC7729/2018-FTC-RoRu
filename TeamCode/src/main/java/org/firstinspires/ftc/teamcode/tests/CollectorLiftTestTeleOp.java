package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "CollectorLiftTestTeleOp", group = "Teleop")
//@Disabled
public class CollectorLiftTestTeleOp extends CollectorLiftTest {
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
        boolean isButtonX = gamepad1.x;
        double speed = 0.3;
        float leftStickY = Range.clip(-gamepad1.left_stick_y, -1, 1);

        if (isButtonA) {
           collectorLift.setPower(speed);
           telemetry.addData("Button","A");
           //A is retract
        } else if (isButtonB) {
            collectorLift.setPower(-speed);
            telemetry.addData("Button","B");
            //B is extend
        } else if (isButtonX) {
            collectorLift.setPower(1);
            telemetry.addData("Button","X");
            //X is retract, but with full power
        } else {
            telemetry.addData("Button","None");
            collectorLift.setPower(0);
        }
        telemetry.addData("Lift Position",String.format("%7d", collectorLift.getCurrentPosition()));
        telemetry.update();
    }

}
