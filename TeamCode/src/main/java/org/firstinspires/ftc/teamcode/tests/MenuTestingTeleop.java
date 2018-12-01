package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import com.lasarobotics.library.options.*;
import com.lasarobotics.library.util.*;

import org.firstinspires.ftc.teamcode.Apollo13TeleOpHandler;

@TeleOp(name="Options Menu Test",group="tests")
public class MenuTestingTeleop extends Apollo13TeleOpHandler {
    OptionMenu menu;
    boolean getSpeed = false;
    double selectedSpeed = 0.09;
    @Override
    public void init() {
        init(hardwareMap);
        telemetry.addLine("Initializing! :)");
        OptionMenu.Builder builder = new OptionMenu.Builder();
        NumberCategory speed = new NumberCategory("Bot Speed");
        builder.addCategory(speed);
        menu = builder.create();
        menu.show();
    }
    public void loop() {
        if(!getSpeed) {
            selectedSpeed = MathUtil.coerce(0.01,0.99,Double.parseDouble(menu.getCategories().get(0).getResult()));
            getSpeed = true;
        }
        handleGamePad1(gamepad1);
        handleGamePad2(gamepad2);
    }
    @Override
    public void handleGamePad1(Gamepad gamepad) {
        float leftStickY = Range.clip(-gamepad.left_stick_y, -1, 1);
        float rightStickY = Range.clip(-gamepad.right_stick_y, -1, 1);

        double SPEED_MULTIPIER = selectedSpeed;
        float leftPower = leftStickY;
        float rightPower = rightStickY;

        LBMotor.setPower(leftPower* SPEED_MULTIPIER);
        LFMotor.setPower(leftPower * SPEED_MULTIPIER);
        RFMotor.setPower(rightPower * SPEED_MULTIPIER);
        RBMotor.setPower(rightPower * SPEED_MULTIPIER);
    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {

    }
}
