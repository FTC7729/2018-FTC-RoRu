package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "JonBtest", group = "Teleop")
public class JonsServoTest extends JonServoTestHardwareBap{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        init(hardwareMap);

    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop()
    {
        if (gamepad1.a == true)
        {
            rightServo.setDirection(DcMotorSimple.Direction.FORWARD);
            leftServo.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if (gamepad1.b == true)
        {
            rightServo.setDirection(DcMotorSimple.Direction.REVERSE);
            leftServo.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

}
