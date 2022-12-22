package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.JointController;
import org.firstinspires.ftc.teamcode.util.UtilityKit;

public class Actuators {
    public Telemetry telemetry;

    // Declare drivetrain dc motors
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backRight;
    public DcMotorEx backLeft;

    // Declare joint controllers
    public JointController turnTable;
    public JointController baseSegment;
    public JointController baseSegment2;
    public JointController lowerSegment;

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
            turnTable = new JointController(hardwareMap, "turnTable");
            lowerSegment = new JointController(hardwareMap, "lowerSegment");
            baseSegment = new JointController(hardwareMap, "baseSegment");
            baseSegment2 = new JointController(hardwareMap, "baseSegment2");
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

    public void updateDrivetrainMotors(MotorController motorController) {
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
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void updateArm(Sensors sensors, double grabberBend, double grabberRotation, int turnTicks, int lowerTicks, int baseTicks) {
        // GoBilda 2000-0025-0002 300 degree max rotation
        grabberRotation = UtilityKit.limitToRange(grabberRotation, -120, 120);
        grabberBend = UtilityKit.limitToRange(grabberBend, -120, 120);
        grabberRotationServo.setPosition((1/150.0) * grabberRotation);
        grabberBendServo.setPosition((1/150.0) * grabberBend);

        //TODO: Create our own (smarter) run to position control system

        turnTable.setPower(1);
        baseSegment.setPower(1);
        baseSegment2.setPower(1);
        lowerSegment.setPower(1);

        turnTable.setVelocity(UtilityKit.ticksPerDegreeAtJoint*30);
        baseSegment.setVelocity(UtilityKit.ticksPerDegreeAtJoint*30);
        baseSegment2.setVelocity(UtilityKit.ticksPerDegreeAtJoint*30);
        lowerSegment.setVelocity(UtilityKit.ticksPerDegreeAtJoint*30);

        turnTable.setTarget(turnTicks);
        baseSegment.setTarget(baseTicks);
        baseSegment2.setTarget(baseTicks);
        lowerSegment.setTarget(lowerTicks);
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
