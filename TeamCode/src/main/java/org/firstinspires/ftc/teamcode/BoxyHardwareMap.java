package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public abstract class BoxyHardwareMap extends LinearOpMode{
    public DcMotor LFMotor;
    public DcMotor RFMotor;
    public NavxMicroNavigationSensor navx;


    /**
     * Initialize the hardware
     *
     * @param hardwareMap configuration from FTC application
     */
    public void init(HardwareMap hardwareMap) {
        // grab wheels
        LFMotor = hardwareMap.dcMotor.get("LFMotor");
        RFMotor = hardwareMap.dcMotor.get("RFMotor");
        // grab navx sensor
        navx = hardwareMap.get(NavxMicroNavigationSensor.class,"navx");
    }
    public void turnLeft(double power) {
        LFMotor.setPower(power);
        RFMotor.setPower(power);
    }
    public void turnRight(double power) {
        LFMotor.setPower(-power);
        RFMotor.setPower(-power);
    }
    public void commitStopMoving() {
        LFMotor.setPower(0);
        RFMotor.setPower(0);
    }

}
