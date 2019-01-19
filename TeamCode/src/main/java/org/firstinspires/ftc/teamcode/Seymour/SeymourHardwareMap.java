package org.firstinspires.ftc.teamcode.Seymour;


import com.disnodeteam.dogecv.filters.LeviColorFilter;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;



public abstract class SeymourHardwareMap extends LinearOpMode {
        public DcMotor seymourMotor;


        /**
         * I2C Pin order: Red, Black, Yellow, White
         *
         * On Board Pin order: White, Yellow, Black, Red
         */


        /**
         * Initialize the hardware
         *
         * @param hardwareMap configuration from FTC application
         */
        public void  init(HardwareMap hardwareMap) throws InterruptedException {
            // grab wheels
            //front is 1 back is 2
            seymourMotor = hardwareMap.dcMotor.get("seymourMotor");
            seymourMotor.setDirection(DcMotor.Direction.FORWARD);
    }




}
