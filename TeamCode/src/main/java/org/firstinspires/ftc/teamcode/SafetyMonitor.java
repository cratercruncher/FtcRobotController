package org.firstinspires.ftc.teamcode;

public class SafetyMonitor {

    public boolean grabberStop;
    public boolean upperStop;
    public boolean lowerStop;
    public boolean baseStop;

    public void safetyCheck(MotorController motorController, Sensors sensorState) {
        // Check if the limiter switches for each segment are triggered
        boolean grabberSwitch;
        boolean baseSwitch;
        boolean lowerSwitch;
        boolean upperSwitch;

        grabberSwitch = sensorState.mG;
        upperSwitch = sensorState.bU;
        lowerSwitch = sensorState.bLA || sensorState.bLB;
        baseSwitch = sensorState.bBA || sensorState.bBB;

        // If a switch is activated, prevent the arm from going further in that direction
        if (grabberSwitch && motorController.grabberTicks > sensorState.oldGrabberSegmentPosition) {
            grabberStop = true;
        }
        else {
            grabberStop = false;
        }
        if (upperSwitch && motorController.grabberTicks > sensorState.oldGrabberSegmentPosition) {
            upperStop = true;
        }
        else {
            upperStop = false;
        }
        if (lowerSwitch && motorController.grabberTicks > sensorState.oldGrabberSegmentPosition) {
            lowerStop = true;
        }
        else {
            lowerStop = false;
        }
        if (baseSwitch && motorController.grabberTicks > sensorState.oldGrabberSegmentPosition) {
            baseStop = true;
        }
        else {
            baseStop = false;
        }
    }
}

 /*
 We can dance if we want to
We can leave your friends behind
'Cause your friends don't dance and if they don't dance
Well, they're no friends of mine

Say, we can go where we want to
A place where they will never find
And we can act like we come from out of this world
Leave the real one far behind

And we can dance
(Dancé!)

We can go when we want to
The night is young and so am I
And we can dress real neat from our hats to our feet
And surprise 'em with the victory cry

Say, we can act if we want to
If we don't, nobody will
And you can act real rude and totally removed
And I can act like an imbecile

And say, we can dance, we can dance
Everything's out of control
We can dance, we can dance
They're doing it from pole to pole
We can dance, we can dance
Everybody look at your hands
We can dance, we can dance
Everybody's taking the chance
Safety dance
Oh well, the safety dance
Ah yes, the safety dance

We can dance if we want to
We've got all your life and mine
As long as we abuse it, never gonna lose it
Everything'll work out right

I say, we can dance if we want to
We can leave your friends behind
Because your friends don't dance and if they don't dance
Well, they're no friends of mine

I say, we can dance, we can dance
Everything's out of control
We can dance, we can dance
We're doing it from pole to pole
We can dance, we can dance
Everybody look at your hands
We can dance, we can dance
Everybody's taking the chance

Oh well, the safety dance
Ah yes, the safety dance
Oh well, the safety dance
Oh well, the safety dance
Oh yes, the safety dance
Oh, the safety dance, yeah
Well, it's the safety dance
It's the safety dance
Well, it's the safety dance
Oh, it's the safety dance
Oh, it's the safety dance
Oh, it's the safety dance
  */