package org.firstinspires.ftc.teamcode.util;

public class UtilityKit {
    static double wheelCirCm = Math.PI*9.6; // cm
    static double wheelCirIn = Math.PI*3.779; // in

    static public int driveDistanceToTicks (double distance, UnitOfDistance unitOfDistance) { // cm
        if (unitOfDistance == UnitOfDistance.CM) {
            return driveDegreesToTicks(distance * (360 / wheelCirCm));
        }
        else if (unitOfDistance == UnitOfDistance.IN) {
            return driveDegreesToTicks(distance * (360 / wheelCirIn));
        }
        else {
            return 0;
        }
    }

    static public double driveTicksToDegrees(int ticks) {
        return 360/537.7*ticks;
    }

    static public int driveDegreesToTicks(double degrees) {
        return (int)(537.7/360*degrees);
    }

    static public int armDegreesToTicks(double degrees) { return (int)((5281.1*1.4)/360*degrees);}

    static public int grabberDegreesToTicks(double degrees) { return (int)((5281.1)/360*degrees);}

    static public double sin(double n, UnitOfAngle unitOfAngle) {
        if (unitOfAngle == UnitOfAngle.DEGREES) {
            return Math.sin(Math.toRadians(n));
        }
        else {
            return Math.sin(n);
        }
    }

    static public double cos(double n, UnitOfAngle unitOfAngle) {
        if (unitOfAngle == UnitOfAngle.DEGREES) {
            return Math.cos(Math.toRadians(n));
        }
        else {
            return Math.cos(n);
        }
    }

    static public double tan(double n, UnitOfAngle unitOfAngle) {
        if (unitOfAngle == UnitOfAngle.DEGREES) {
            return Math.tan(Math.toRadians(n));
        }
        else {
            return Math.tan(n);
        }
    }

    static public double asin(double n, UnitOfAngle unitOfAngle) {
        if (unitOfAngle == UnitOfAngle.DEGREES) {
            return Math.toDegrees(Math.asin(n));
        }
        else {
            return Math.asin(n);
        }
    }

    static public double acos(double n, UnitOfAngle unitOfAngle) {
        if (unitOfAngle == UnitOfAngle.DEGREES) {
            return Math.toDegrees(Math.acos(n));
        }
        else {
            return Math.acos(n);
        }
    }

    static public double atan(double n, UnitOfAngle unitOfAngle) {
        if (unitOfAngle == UnitOfAngle.DEGREES) {
            return Math.toDegrees(Math.atan(n));
        }
        else {
            return Math.atan(n);
        }
    }
}
