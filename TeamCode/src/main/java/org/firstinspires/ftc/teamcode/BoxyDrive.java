
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="BoxyDrive", group="TeleOp")
public class BoxyDrive extends BoxyHardwareHandler
{
    //Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void handleGamePad1(Gamepad gamepad) {
        telemetry.addData("We Made It", "We Made it !");
        //Range.clip clips off the values -1 and 1 from the possible locations on the joysticks
        //Floats for more accuracy, Joysticks return floats.
        float leftStickY = Range.clip(-gamepad.left_stick_y, -1, 1);
        float rightStickY = Range.clip(-gamepad.right_stick_y, -1, 1);

        //Cannot go over 1
        double SPEED_MULTIPIER = 0.75;
        float leftPower = leftStickY;
        float rightPower = rightStickY;
        telemetry.addData("gamepad1","Left1 %.2f , Right1 %.2f", leftStickY, rightStickY);

        LBMotor.setPower(leftPower* SPEED_MULTIPIER);
        LFMotor.setPower(leftPower * SPEED_MULTIPIER);
        RFMotor.setPower(rightPower * SPEED_MULTIPIER);
        RBMotor.setPower(rightPower * SPEED_MULTIPIER);
    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {

    }


}
