package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class ServoTestHardwareBap extends OpMode {
    public Servo hookServo;
    public static final double START_WEEBLE = 0;
    public static final double END_WEEBLE = 0.40;

    public void init (HardwareMap hardwaremap)
    {
        //front is 1 back is 2
            hookServo = hardwaremap.servo.get("hookServo");
            hookServo.setDirection(Servo.Direction.FORWARD);
            hookServo.setPosition(0);
    }

}
