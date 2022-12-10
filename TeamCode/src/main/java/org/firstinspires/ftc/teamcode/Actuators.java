package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.UtilityKit;

public class Actuators {
    public Telemetry telemetry;

    // Declare drivetrain dc motors
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backRight;
    public DcMotorEx backLeft;

    // Declare arm dc motors
    public DcMotorEx grabberSegment;
    public DcMotorEx turnTable;
    public DcMotorEx lowerSegment;
    public DcMotorEx baseSegment;
    public DcMotorEx baseSegment2;

    // Declare servo control for arm
    public Servo grabberServo;
    public Servo grabberRotationServo;
    public Servo grabberBendServo;

    // Initialize GodrickBot
    public void initializeGodrick(HardwareMap hardwareMap, Telemetry telemetry) {

        this.telemetry = telemetry;

        try {
            // Initialize drivetrain dc motors
            frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
            backRight = hardwareMap.get(DcMotorEx.class, "backRight");
            backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");

            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        catch (Exception drivetrainInitException) {
            System.out.println("Drivetrain dc motors failed to initialize");
        }

        try {
            // Initialize arm dc motors
//            grabberSegment = hardwareMap.get(DcMotorEx.class, "grabberSegment");
            turnTable = hardwareMap.get(DcMotorEx.class, "turnTable");
            lowerSegment = hardwareMap.get(DcMotorEx.class, "lowerSegment");
            baseSegment = hardwareMap.get(DcMotorEx.class, "baseSegment");

//            grabberSegment.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turnTable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lowerSegment.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            baseSegment.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        catch (Exception armInitException) {
            System.out.println("Arm dc motors failed to initialize");
        }

        try {
            // Initialize arm servo motors
            grabberServo = hardwareMap.get(Servo.class, "grabberServo");
            grabberRotationServo = hardwareMap.get(Servo.class, "grabberRotationServo");
            grabberBendServo = hardwareMap.get(Servo.class, "grabberBendServo");

        }
        catch (Exception servoInitException) {
            System.out.println("Servos failed to initialize");
        }
    }

    // get methods for drivetrain dc motors
    public int getFrontLeftPosition() {return frontLeft.getCurrentPosition();}
    public int getFrontRightPosition() {return frontRight.getCurrentPosition();}
    public int getBackRightPosition() {return backRight.getCurrentPosition();}
    public int getBackLeftPosition() {return backLeft.getCurrentPosition();}

    // get methods for arm dc motors
    public int getGrabberSegmentPosition() {return grabberSegment.getCurrentPosition();}
    public int getUpperSegmentPosition() {return turnTable.getCurrentPosition();}
    public int getLowerSegmentPosition() {return lowerSegment.getCurrentPosition();}
    public int getBaseSegmentPosition() {return baseSegment.getCurrentPosition();}

    public void updateMotors(MotorController motorController) {
        // set target positions for drivetrain dc motors
        frontLeft.setTargetPosition(motorController.frontLeftTicks);
        frontRight.setTargetPosition(motorController.frontRightTicks);
        backRight.setTargetPosition(motorController.backRightTicks);
        backLeft.setTargetPosition(motorController.backLeftTicks);

        // set power for drivetrain dc motors
        frontLeft.setPower(motorController.frontLeft);
        frontRight.setPower(motorController.frontRight);
        backRight.setPower(motorController.backRight);
        backLeft.setPower(motorController.backLeft);

        // set run mode for drivetrain dc motors
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void updateArm(MotorController motorController) {

        // Set target positions for arm dc motors
        grabberSegment.setTargetPosition(motorController.grabberTicks);
        turnTable.setTargetPosition(motorController.upperTicks);
        lowerSegment.setTargetPosition(motorController.lowerTicks);
        baseSegment.setTargetPosition(motorController.baseTicks);

        // Set power for arm dc motors
        grabberSegment.setPower(1);
        turnTable.setPower(1);
        lowerSegment.setPower(1);
        baseSegment.setPower(1);

        // Set run more for arm dc motors
        grabberSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turnTable.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lowerSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        baseSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (motorController.grabberRelease) {
            grabberServo.setPosition(1);
        }
        else {
            grabberServo.setPosition(0);
        }
    }

    public void updateArm(ArmController armController) {
        // Set target positions for arm dc motors
//        grabberSegment.setTargetPosition(armController.grabberTicks);
        turnTable.setTargetPosition(armController.turnTableTicks);
        lowerSegment.setTargetPosition(armController.lowerTicks);
        baseSegment.setTargetPosition(armController.baseTicks);

        telemetry.addData("baseTicks", armController.baseTicks);
        telemetry.addData("baseSegment.currentPosition", baseSegment.getCurrentPosition());

        // Set power for arm dc motors
//        grabberSegment.setPower(.5);
        turnTable.setPower(.5);
        lowerSegment.setPower(.5);
        baseSegment.setPower(.5);

        // Set run more for arm dc motors
//        grabberSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turnTable.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lowerSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        baseSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void updateArm(double grabberBend, double grabberRotation, int turnTableTicks, int lowerTicks, int baseTicks) {
        // Set target positions for arm dc motors
//        grabberSegment.setTargetPosition(grabberTicks);
        turnTable.setTargetPosition(turnTableTicks);
        lowerSegment.setTargetPosition(lowerTicks);
        baseSegment.setTargetPosition(baseTicks);
        baseSegment2.setTargetPosition(baseTicks);

        //        GoBilda 2000-0025-0002 300 degree max rotation
        grabberRotation = UtilityKit.limitToRange(grabberRotation, -150, 150);
        grabberBend = UtilityKit.limitToRange(grabberBend, -150, 150);
        grabberRotationServo.setPosition((1/150) * grabberRotation);
        grabberBendServo.setPosition((1/150) * grabberBend);

        /*telemetry.addData("Running to",  "%7d :%7d :%7d :%7d",
                baseTicks,  lowerTicks, turnTableTicks, grabberTicks);
        telemetry.addData("Current at",  "%7d :%7d :%7d :%7d",
                baseSegment.getCurrentPosition(),  lowerSegment.getCurrentPosition(),
                turnTable.getCurrentPosition(), grabberSegment.getCurrentPosition());*/

        // Set power for arm dc motors
//        grabberSegment.setPower(.5);
        turnTable.setPower(1);
        lowerSegment.setPower(1);
        baseSegment.setPower(1);
        baseSegment2.setPower(1);

        // Set run more for arm dc motors
//        grabberSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turnTable.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lowerSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        baseSegment.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        baseSegment2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void updateServos(MotorController motorController) {
        if (motorController.grabberRelease) {
            grabberServo.setPosition(1);
        }
        else {
            grabberServo.setPosition(0);
        }
    }
}
