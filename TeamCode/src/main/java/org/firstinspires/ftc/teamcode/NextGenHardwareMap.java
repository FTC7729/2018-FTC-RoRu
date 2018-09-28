package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DigitalChannel;
public class NextGenHardwareMap {
    public DcMotor LFMotor;
    public DcMotor RFMotor;
    public DcMotor LBMotor;
    public DcMotor RBMotor;
    public DcMotor Collector;
    public DcMotor LiftRotation;
    public DcMotor LiftHeight;
    //public DigitalChannel LimitSwitch;
    //public DigitalChannel LimitSwitchTilt;
    public void init(HardwareMap hardwareMap) {
        // grab wheels
        LFMotor = hardwareMap.dcMotor.get("LFMotor");
        RFMotor = hardwareMap.dcMotor.get("RFMotor");
        LBMotor = hardwareMap.dcMotor.get("LBMotor");
        RBMotor = hardwareMap.dcMotor.get("RBMotor");
        Collector = hardwareMap.dcMotor.get("Collector");
        LiftRotation = hardwareMap.dcMotor.get("LiftRotation");
        LiftHeight = hardwareMap.dcMotor.get("LiftHeight");
        //LimitSwitch = hardwareMap.digitalChannel.get("LimitSwitch");
        //LimitSwitchTilt = hardwareMap.digitalChannel.get("LimitSwitchTilt");
    }
}
