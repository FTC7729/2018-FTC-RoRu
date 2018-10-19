package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class JonServoTestHardwareBap extends OpMode {
    public CRServo leftServo;
    public CRServo rightServo;


    public void init (HardwareMap hardwaremap)
    {
            leftServo = hardwaremap.crservo.get("leftServo");
            rightServo = hardwaremap.crservo.get("rightServo");
    }

}
