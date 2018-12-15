package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name="Apollo13Drive", group="TeleOp")
public class Apollo13Drive extends Apollo13TeleOpHandler{

    //Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private boolean liftUp = false;
    private boolean hookOpen = false;
    /**
     * Value here from {@link AlanBlue1}
     */
    public final int LIFT_MAX_POS = -2310;
    /**
     * Value here from {@link AlanBlue1}
     */
    public final int LIFT_MIN_POS = -57;
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
            telemetry.addData("Lift Position",String.format("%7d", liftMotor.getCurrentPosition()));
            telemetry.update();
            if(gamepad.left_bumper && liftMotor.getCurrentPosition() < LIFT_MIN_POS) {
                liftMotor.setPower(0.4);
            } else if(gamepad.right_bumper && liftMotor.getCurrentPosition() > LIFT_MAX_POS) {
                liftMotor.setPower(-0.4);
            } else {
                liftMotor.setPower(0);
            }
            if(gamepad.a && hookServo.getPosition() < 1) {
                hookServo.setPosition(hookServo.getPosition() + 0.01);
            } else if (gamepad.b && hookServo.getPosition() > 0.1) {
                hookServo.setPosition(hookServo.getPosition() - 0.01);
            }
        }

    }


