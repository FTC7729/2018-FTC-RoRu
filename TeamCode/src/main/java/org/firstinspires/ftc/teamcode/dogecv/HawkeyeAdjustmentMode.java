package org.firstinspires.ftc.teamcode.dogecv;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.opencv.core.Scalar;
@TeleOp(name ="Hawkeye Adjustment Mode")
@Disabled
public class HawkeyeAdjustmentMode extends OpMode {
    public HawkeyeDetector hawk;
    public double Hue = 0;
    public double Sat = 0;
    public double Val = 0;
    public void init() {
        hawk = new HawkeyeDetector();
        hawk.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        hawk.enable();
    }
    public void loop() {
        handleGamePad1(gamepad1);
        hawk.colorFilter.updateSettings(new Scalar(Hue,Sat,Val), new Scalar(25,75,75));
        telemetry.addData("Hue",Hue);
        telemetry.addData("Saturation",Sat);
        telemetry.addData("Value",Val);
        telemetry.addData("Status", hawk.isFound() ? "Found!":"Not found");
    }
    public void handleGamePad1(Gamepad gamepad) {
        boolean hueUp = gamepad.right_bumper;
        boolean hueDown = gamepad.left_bumper;
        boolean satUp = gamepad.dpad_up;
        boolean satDown = gamepad.dpad_down;
        boolean valDown = gamepad.dpad_left;
        boolean valUp = gamepad.dpad_right;
        if(hueUp) {
            Hue += 0.1;
        } else if(hueDown) {
            Hue -= 0.1;
        }
        if(satUp) {
            Sat += 0.1;
        } else if(satDown) {
            Sat -= 0.1;
        }
        if(valUp) {
            Val += 0.1;
        } else if(valDown) {
            Val -= 0.1;
        }
    }
}
