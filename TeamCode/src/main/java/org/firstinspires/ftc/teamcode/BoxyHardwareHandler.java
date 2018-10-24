package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public abstract class BoxyHardwareHandler extends BoxyHardwareMapTeleop {

    @Override
    public void init() {
        init(hardwareMap);
        telemetry.addLine("Initializing! :)");
    }

    @Override
    public void loop() {
        handleGamePad1(gamepad1);
        handleGamePad2(gamepad2);
        telemetry.addLine("In loop!");
    }

    @Override
    public void start() {
        telemetry.addLine("Starting!");
    }

    @Override
    public void stop() {
        telemetry.addLine("Stopping!");
    }

    public abstract void handleGamePad1(Gamepad gamepad);

    /**
     * Handle all Game Pad 2 controller input
     * When creating a new Tele-Op user must extend this class then add in custom gamepad2 code
     */
    public abstract void handleGamePad2(Gamepad gamepad);



}
