package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.util.Vector2D;
import org.firstinspires.ftc.teamcode.util.Vector3D;

// Values recorded here are directly observed from sensors
public class Sensors {
    Telemetry telemetry;

    private boolean initialized = false;

    public Vector3D orientation = new Vector3D();
    public Vector2D position2D = new Vector2D();

    // 9-DOF:
    // Magnetometer (indicates magnetic north, can be useful to correct gyroscope, but is subject to false magnetic fields)
    // Accelerometer (indicates robot acceleration)
    // Gyroscope (indicates robot orientation but may drift)
    private BNO055IMU gyro;
    private BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    private Orientation angles;
    private Acceleration acceleration;
    private AngularVelocity angularVelocity;
    private Position position;

    // Time (time passed since the last data snapshot is useful for physics calculations)
    private ElapsedTime runtime = new ElapsedTime();

    // Touch Sensors
    private DigitalChannel magnetSwitchGripper;
    private TouchSensor touchUpper;
    private TouchSensor touchLowerA;
    private TouchSensor touchLowerB;
    private TouchSensor touchBaseA;
    private TouchSensor touchBaseB;
    public boolean mG;
    public boolean bU;
    public boolean bLA;
    public boolean bLB;
    public boolean bBA;
    public boolean bBB; // this is absurd

    // Create delta position of drivetrain dc motors
    public int deltaFrontLeftPosition;
    public int deltaFrontRightPosition;
    public int deltaBackRightPosition;
    public int deltaBackLeftPosition;

    // Create delta angles of arm dc motors
    public double deltaGrabberSegmentAngle;
    public double deltaUpperSegmentAngle;
    public double deltaLowerSegmentAngle;
    public double deltaBaseSegmentAngle;

    // Create old positions of drivetrain dc motors
    public int oldFrontLeftPosition;
    public int oldFrontRightPosition;
    public int oldBackRightPosition;
    public int oldBackLeftPosition;

    // Create old angles of arm dc motors
    public double oldGrabberSegmentAngle;
    public double oldUpperSegmentAngle;
    public double oldLowerSegmentAngle;
    public double oldBaseSegmentAngle;

    // Create old positions of arm dc motors
    public int oldGrabberSegmentPosition;
    public int oldUpperSegmentPosition;
    public int oldLowerSegmentPosition;
    public int oldBaseSegmentPosition;

    // Create current positions of drivetrain dc motors
    public int frontLeftPosition;
    public int frontRightPosition;
    public int backRightPosition;
    public int backLeftPosition;

    // Create current positions of arm dc motors
    public int grabberSegmentPosition;
    public int upperSegmentPosition;
    public int lowerSegmentPosition;
    public int baseSegmentPosition;

    // Create current angles of arm dc motors
    public double grabberSegmentAngle;
    public double upperSegmentAngle;
    public double lowerSegmentAngle;
    public double baseSegmentAngle;

    private double time = 0;
    private double lastTime = 0;
    private double dt = 0; // the time interval since the last update function call

    public double getDt(){
        return dt;
    }
    public double getTime() { return time; }

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){

        this.telemetry = telemetry;

        try {
            gyro = hardwareMap.get(BNO055IMU.class, "imu");
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "BNO055IMUCalibration.json";
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            gyro.initialize(parameters);

            //magnetSwitchGripper = hardwareMap.get(DigitalChannel.class, "magneticGripper");
            //touchUpper = hardwareMap.get(TouchSensor.class, "touchUpper");
            touchLowerA = hardwareMap.get(TouchSensor.class, "touchLowerA");
            touchLowerB = hardwareMap.get(TouchSensor.class, "touchLowerB");
            //touchBaseA = hardwareMap.get(TouchSensor.class, "touchBaseA");
            //touchBaseB = hardwareMap.get(TouchSensor.class, "touchBaseB");

            runtime.reset();

            initialized = true;
        }
        catch (Exception e){
            telemetry.addData("Sensor Init Failed! ", e.toString());
            telemetry.update();
        }
    }

    public void update(Actuators actuators, boolean verbose) {
        try {
            time = runtime.seconds();
            dt = time - lastTime;
            lastTime = time;

           // mG = !magnetSwitchGripper.getState();
            //bU = touchUpper.isPressed();
            bLA = touchLowerA.isPressed();
            bLB = touchLowerB.isPressed();
           // bBA = touchBaseA.isPressed();
            //bBB = touchBaseB.isPressed();



            // Set current positions of drivetrain dc motors
            frontLeftPosition = actuators.getFrontLeftPosition();
            frontRightPosition = actuators.getFrontRightPosition();
            backRightPosition = actuators.getBackRightPosition();
            backLeftPosition = actuators.getBackLeftPosition();

            // Set delta positions of drivetrain dc motors
            deltaFrontLeftPosition = frontLeftPosition - oldFrontLeftPosition;
            deltaFrontRightPosition = frontRightPosition - oldFrontRightPosition;
            deltaBackRightPosition = backRightPosition - oldBackRightPosition;
            deltaBackLeftPosition = backLeftPosition - oldBackLeftPosition;

            // Set old positions for drivetrain dc motors
            oldFrontLeftPosition = frontLeftPosition;
            oldFrontRightPosition = frontRightPosition;
            oldBackRightPosition = backRightPosition;
            oldBackLeftPosition = backLeftPosition;

            /*
            // Set current positions of arm dc motors
            grabberSegmentPosition = actuators.getGrabberSegmentPosition();
            upperSegmentPosition = actuators.getUpperSegmentPosition();
            lowerSegmentPosition = actuators.getLowerSegmentPosition();
            baseSegmentPosition = actuators.getBaseSegmentPosition();

            // Set delta angles of arm dc motors
            deltaGrabberSegmentAngle = grabberSegmentAngle-oldGrabberSegmentAngle;
            deltaUpperSegmentAngle = upperSegmentAngle-oldUpperSegmentAngle;
            deltaLowerSegmentAngle = lowerSegmentAngle-oldLowerSegmentAngle;
            deltaBaseSegmentAngle = baseSegmentAngle-oldBaseSegmentAngle;

            // Set old angles of arm dc motors
            oldGrabberSegmentAngle = grabberSegmentAngle;
            oldUpperSegmentAngle = upperSegmentAngle;
            oldLowerSegmentAngle = lowerSegmentAngle;
            oldBaseSegmentAngle = baseSegmentAngle;

            // Set old positions for arm dc motors;
            oldGrabberSegmentPosition = grabberSegmentPosition;
            oldUpperSegmentPosition = upperSegmentPosition;
            oldLowerSegmentPosition = lowerSegmentPosition;
            oldBaseSegmentPosition = baseSegmentPosition;

            // Set current angles of arm dc motors
            grabberSegmentAngle = 360/5281.1*grabberSegmentPosition; // I should probably make a variable for this
            upperSegmentAngle = 360/5281.1*upperSegmentPosition;
            lowerSegmentAngle = 360/5281.1*lowerSegmentPosition;
            baseSegmentAngle = 360/5281.1*baseSegmentPosition;
            */

            angles   = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double heading = formatAngle(angles.angleUnit, angles.firstAngle);
            double roll  = formatAngle(angles.angleUnit, angles.secondAngle);
            double pitch = formatAngle(angles.angleUnit, angles.thirdAngle);
            orientation.set(pitch, roll, heading);

            acceleration = gyro.getOverallAcceleration();
            angularVelocity = gyro.getAngularVelocity();
            position = gyro.getPosition();//.toUnit(DistanceUnit.INCH);
            position2D.set(position.x, position.y);

            if(verbose) {
               // telemetry.addData("Magnet Switch Gripper: ", mG);
               // telemetry.addData("Touch Upper: ", bU);
                telemetry.addData("Touch Lower A: ", bLA);
                telemetry.addData("Touch Lower B: ", bLB);
                //telemetry.addData("Touch Base A", bBA);
               // telemetry.addData("Touch Base B", bBB);

                telemetry.addData("Position: ", position.toString());
                telemetry.addData("Orientation: ", angles.toString());
            }
        }

        catch (Exception e) {
            telemetry.addData("Sensor Test Update Ouch!", e.toString());
        }
    }

    Double formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    Double formatDegrees(double degrees){
        return AngleUnit.DEGREES.normalize(degrees);
    }
}
