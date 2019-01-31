package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name="NextGenDrive", group="TeleOp")
//@Disabled
public class NextGenDrive extends NextGenTeleopHandler{
    //Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private boolean liftUp = false;
    private boolean hookOpen = false;

    /**
     * Value here from {@link AlanBlue1}
     */
 //   public final int LIFT_MAX_POS = -5743;
    //this is slightly higher (or lower; it is technically a lower number) than what is in AlanBlue1
    /**
     * Value here from {@link AlanBlue1}
     */
//    public final int LIFT_MIN_POS = -1000;
    public final double COLLECTOR_LIFT_SPEED = 0.3;
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


        if (gamepad.left_bumper){
            turnLeft(SPEED_MULTIPIER);
        }
        else if (gamepad.right_bumper){
            turnRight(SPEED_MULTIPIER);
        }
        else {
            LBMotor.setPower(leftPower* SPEED_MULTIPIER);
            LFMotor.setPower(leftPower * SPEED_MULTIPIER);
            RFMotor.setPower(rightPower * SPEED_MULTIPIER);
            RBMotor.setPower(rightPower * SPEED_MULTIPIER);
        }

        /*if (gamepad.dpad_left){
            navxTurn(-90);
            //dpad left turns to absolute -90
        }
        else if (gamepad.dpad_right){
            navxTurn(90);
            //dpad right turns to absolute 90
        }
        else if (gamepad.dpad_up) {
            navxTurn(0);
            //dpad up turns to absolute 0
        }
        else if (gamepad.dpad_down){
            navxTurn(180);
            //dpad down turns to absolute 180
        }*/
    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {
        float leftStickY = Range.clip(-gamepad.left_stick_y, -1, 1);
        telemetry.update();
        if(gamepad.left_bumper && liftMotor.getCurrentPosition() < LIFT_HOLD_POSITION) {
            liftMotor.setPower(0.4);
            //liftMotor.setTargetPosition(LIFT_MAX_POS);
            //retracts
        } else if(gamepad.right_bumper && liftMotor.getCurrentPosition() > LIFT_RUN_POSITION) {
            liftMotor.setPower(-0.4);
            //liftMotor.setTargetPosition(LIFT_MIN_POS);
            //extends
        } else if(gamepad.x && liftMotor.getCurrentPosition() < LIFT_HOLD_POSITION) {
            liftMotor.setPower(1);
            //retracts real fast
        } else if(gamepad.y && liftMotor.getCurrentPosition() > LIFT_DUMP_MAX_POSITION) {
            liftMotor.setPower(-0.4);
        }
        else {
            liftMotor.setPower(0);
        }
        //move left stick up and down
        if(leftStickY < -0.1 && hookServo.getPosition() < 1) {
            hookServo.setPosition(hookServo.getPosition() + 0.01);
            //opens hook
            //stick down
        }
        else if (leftStickY > 0.1 && hookServo.getPosition() > 0.1) {
            hookServo.setPosition(hookServo.getPosition() - 0.01);
            //closes hook
            //stick up
        }
      /*  if(gamepad.y) {
            // Do *NOT* press this button in the last 5 seconds or so of the match or else it might still be turning at the buzzer!
            navxTurn(0);
        }
        */
        if(gamepad.right_trigger > 0.1) {
            collectorServo.setPower(0.3);
        }
        else if(gamepad.left_trigger > 0.1) {
            collectorServo.setPower(-0.3);
        }
        else{
            collectorServo.setPower(0);
        }
        //RTrigger spins the collector servo clockwise (in)
        //LTrigger spins the collector servo counterclockwise (out)
        if (gamepad.dpad_up){
            collectorLift.setPower(COLLECTOR_LIFT_SPEED);
        }
        else if (gamepad.dpad_down){
            collectorLift.setPower(-COLLECTOR_LIFT_SPEED);
        }
        else{
            collectorLift.setPower(0);
        }
        //UpDPad moves the collector lift counterclockwise (in)
        //DownDPad moves the collector lift clockwise (out)
        if (gamepad.a){
            boxServo.setPosition(boxServo.getPosition() + 0.01);
        }else if (gamepad.b){
            boxServo.setPosition(boxServo.getPosition() - 0.01);
        }
        //A moves the Mineral Box servo counterclockwise (in/ dumps mineral(s) in lander)
        //B moves the Mineral Box servo clockwise (out/ brings servo back)

    }

}
