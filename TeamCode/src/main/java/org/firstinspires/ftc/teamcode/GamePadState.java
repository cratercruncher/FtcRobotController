package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamePadState {

    public double leftStickX = 0.0; // drivetrain
    public double leftStickY = 0.0;
    public double rightStickX  =  0.0;
    public double rightStickY = 0.0;

    public double rightTrigger = 0.0; // duck spinner
    public double leftTrigger = 0.0;

    public boolean a;  //
    public boolean b;
    public boolean y;
    public boolean x;
    public boolean dPadUp;
    public boolean dPadDown;
    public boolean dPadLeft;
    public boolean dPadRight;
    public boolean leftBumper;
    public boolean rightBumper;
    public boolean start;
    public boolean back;
    public boolean leftStickButton;
    public boolean rightStickButton;

    public boolean altMode = false;

    Gamepad.RumbleEffect customRumbleEffect;

    public  void initializeRumble() {
        customRumbleEffect = new Gamepad.RumbleEffect.Builder()
                .addStep(1.0, 1.0, 500)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.0, 0.0, 500)  //  Pause for 300 mSec
                .addStep(1.0, 1.0, 500)  //  Rumble left motor 100% for 250 mSec
                .addStep(0.0, 0.0, 500)  //  Pause for 250 mSec
                .addStep(1.0, 1.0, 500)  //  Rumble left motor 100% for 250 mSec
                .build();
    }

    public void update(Gamepad gamepad){
        leftStickX = gamepad.left_stick_x;
        leftStickY = gamepad.left_stick_y;
        rightStickX = gamepad.right_stick_x;
        rightStickY = gamepad.right_stick_y;

        rightTrigger = gamepad.right_trigger;
        leftTrigger = gamepad.left_trigger;

        a = gamepad.a;  // X
        b = gamepad.b;  // O
        y = gamepad.y;  // Triangle
        x = gamepad.x;  // Square

        dPadUp = gamepad.dpad_up;
        dPadDown = gamepad.dpad_down;
        dPadLeft = gamepad.dpad_left;
        dPadRight = gamepad.dpad_right;
        leftBumper = gamepad.left_bumper;
        rightBumper = gamepad.right_bumper;
        start = gamepad.start;
        back = gamepad.back;
        leftStickButton = gamepad.left_stick_button;
        rightStickButton = gamepad.right_stick_button;

        if (altMode && back) {
            altMode = false;
        }
        else if (!altMode && back) {
            altMode = true;
            //customRumbleEffect
        }
    }

    public void clearAll(){
        leftStickX = 0.0;
        leftStickY = 0.0;
        rightStickX = 0.0;
        rightStickY = 0.0;

        rightTrigger = 0.0;
        leftTrigger = 0.0;

        a = false;  // X
        b = false;  // O
        y = false;  // Triangle
        x = false;  // Square

        dPadUp = false;
        dPadDown = false;
        dPadLeft = false;
        dPadRight = false;
        leftBumper = false;
        rightBumper = false;
        start = false;
        back = false;
        leftStickButton = false;
        rightStickButton = false;

        if (altMode && back) {
            altMode = false;
        }
        else if (!altMode && back) {
            altMode = true;
            //customRumbleEffect
        }
    }
}
