package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class MotorController {


    // Gear Ratio Ratio = 19.2:1
    // Encoder Shaft: 28 pulses per revolution
    // Gearbox output: 537.7 pulses per revolution
    public int frontLeftTicks = 0;
    public int frontRightTicks = 0;
    public int backRightTicks = 0;
    public int backLeftTicks = 0;

    public double frontLeft;
    public double frontRight;
    public double backRight;
    public double backLeft;

    // Gear Ratio = 188:1
    // Encoder Shaft = 28 pulses per revolution
    // Gearbox Output = 5281.1 pulses per revolution
    public int grabberTicks = 0;
    public int upperTicks = 0;
    public int lowerTicks = 0;
    public int baseTicks = 0;

    public ArrayList<Double> baseAddAngleQue = new ArrayList<>();

    public double addGrabberAngle;
    public double addUpperAngle;
    public double addLowerAngle;
    public double addBaseAngle;

    public boolean grabberRelease;

    public final static int BASE_TICKS = 200;

    private Telemetry telemetry;

    private boolean dpu;
    private boolean dpd;
    private boolean dpl;
    private boolean dpr;
    private boolean lb;
    private boolean rb;
    private double lt;
    private double rt;
    private boolean a;

    public void initialize(Telemetry telemetry){
        this.telemetry = telemetry;
    }

    public void simpleMechanumUpdate(GamePadState gamePadState, Sensors sensorState, boolean verbose){

        double xL = flatten(gamePadState.leftStickX);
        double yL = flatten(gamePadState.leftStickY);
        double xR = flatten(gamePadState.rightStickX);
        double yR = flatten(gamePadState.rightStickY);

        // get the magnitude of the stick deflection
        double magnitude = Math.sqrt(xL*xL + yL*yL + xR*xR);

        // add the vector components, rescaled to a maximum of the magnitude given
        if(magnitude > .001) {
            frontLeft = -(-yL + xL + xR);//magnitude;
            frontRight = (-yL - xL - xR);//magnitude;
            backRight = (-yL + xL - xR);//magnitude;
            backLeft = -(-yL - xL + xR);//magnitude;
        }
        else{
            frontLeft = 0.0;
            frontRight = 0.0;
            backRight = 0.0;
            backLeft = 0.0;
        }

        if (frontLeft > 1) { frontLeft = 1; }
        else if (frontLeft < -1) { frontLeft = -1; }

        if (frontRight > 1) { frontRight = 1; }
        else if (frontRight < -1) { frontRight = -1; }

        if (backRight > 1) { backRight = 1; }
        else if (backRight < -1) { backRight = -1; }

        if (backLeft > 1) { backLeft = 1; }
        else if (backLeft < -1) { backLeft = -1; }

        frontLeftTicks = sensorState.oldFrontLeftPosition + (int)(BASE_TICKS*frontLeft);
        frontRightTicks = sensorState.oldFrontRightPosition + (int)(BASE_TICKS*frontRight);
        backRightTicks = sensorState.oldBackRightPosition + (int)(BASE_TICKS*backRight);
        backLeftTicks = sensorState.oldBackLeftPosition + (int)(BASE_TICKS*backLeft);

        if(verbose){
            telemetry.addData("frontLeftTicks: ", frontLeftTicks);
            telemetry.addData("frontRightTicks: ", frontRightTicks);
            telemetry.addData("backRightTicks: ", backRightTicks);
            telemetry.addData("backLeftTicks: ", backLeftTicks);
        }
    }

    public void godrickArmUpdate(GamePadState gamePadState, Sensors sensorState, SafetyMonitor safetyState, boolean verbose) {
        dpu = gamePadState.dPadUp;
        dpd = gamePadState.dPadDown;
        dpl = gamePadState.dPadLeft;
        dpr = gamePadState.dPadRight;
        lb = gamePadState.leftBumper;
        rb = gamePadState.rightBumper;
        lt = gamePadState.leftTrigger;
        rt = gamePadState.rightTrigger;
        a = gamePadState.a;

        addGrabberAngle = 0;
        addUpperAngle = 0;
        addLowerAngle = 0;
        addBaseAngle = 0;

        if (gamePadState.altMode) {
            if (dpu) {
                addBaseAngle = 2;
            }
            else if (dpd) {
                addBaseAngle = -2;
            }

            if (dpr) {
                addLowerAngle = 2;
            }
            else if (dpl) {
                addLowerAngle = -2;
            }

            if (rb && !lb) {
                addUpperAngle = 2;
            }
            else if (lb && !rb) {
                addUpperAngle = -2;
            }

            if (rt != 0 && lt == 0) {
                addGrabberAngle = rt*2;
            }
            else if (lt != 0 && rt == 0) {
                addGrabberAngle = lt*-2;
            }

            if (a) {
                grabberRelease = true;
            }
            else {
                grabberRelease = false;
            }
        }

        // 1 deg = 14.669 ticks
        grabberTicks += (int)(addGrabberAngle *5281.1/360);
        upperTicks += (int)(addUpperAngle *5281.1/360);
        lowerTicks += (int)(addLowerAngle *5281.1/360);
        baseTicks += (int)(addBaseAngle *5281.1/360);

        if (safetyState.grabberStop && grabberTicks > sensorState.grabberSegmentPosition-14) {
            grabberTicks = sensorState.grabberSegmentPosition-14;
        }
        if (safetyState.upperStop && upperTicks > sensorState.upperSegmentPosition-14) {
            upperTicks = sensorState.upperSegmentPosition-14;
        }
        if (safetyState.lowerStop && lowerTicks > sensorState.lowerSegmentPosition-14) {
            lowerTicks = sensorState.lowerSegmentPosition-14;
        }
        if (safetyState.baseStop && baseTicks > sensorState.baseSegmentPosition-14) {
            baseTicks = sensorState.baseSegmentPosition-14;
        }

        if (verbose) {
            telemetry.addData("grabberTicks:", grabberTicks);
            telemetry.addData("upperTicks:", upperTicks);
            telemetry.addData("lowerTicks:", lowerTicks);
            telemetry.addData("baseTicks:", baseTicks);
            telemetry.addData("grabberRelease:", grabberRelease);
        }
    }

    public double flatten(double value){
        if(value > 1.0){
            value = 1.0;
        }
        else if(value < -1.0){
            value = -1.0;
        }
        return value * Math.abs(value);
    }

    public void servoUpdate(GamePadState gamePadState) {
        a = gamePadState.a;

        if (a) {
            grabberRelease = true;
        }
        else {
            grabberRelease = false;
        }
    }
}
