package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Alan Blue 1 States Psue",group = "Autonomous")
@Disabled
public class AlanBlue1States_psuedo extends AlanAutonomousHardwareMapStates {

        public void runOpMode() throws InterruptedException {
            // STATE 0
            //initialize DogeCV
            //initialize NavX
            //initialize hardwareMap
            //set state to 10
            int state = 0;
            init(hardwareMap);



                 waitForStart();

                 //state10
                 //land with LiftMotor
                 //Unhook Hook
                 //Move LiftMotor back down
                 //Zero NavX
                 //set state to 20
            if(state == 10) {


                state = 20;
            }


            //state 20
            //Is the gold:
            //On the left?
            //Yes, Set state to 21
            //No, proceed
            //On the right?
            //Yes, set state to 23
            //No, proceed
            //Turn left 15 degrees


            if (state == 20) {

            }
            //NavXTurn left until you face gold
            //Move forward 29"
            //Move backward 29"
            //Set state to 30
            if (state == 21) {

            }

            //STATE 22
            //NavXTurn right until you face gold
            //Move forward 29"
            //Move backward 29"
            //Set state to 30
            if (state == 22) {
            }

            if (state == 23) {

            }


                //state 30
                //turn off DogeCV
                //initalize Vuforia
                //NavXTurn left until you face Vuforia image (blue rover)
                //
            if (state == 30){

            }

        }

    }