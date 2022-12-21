package org.firstinspires.ftc.teamcode.util;

public class ArmJoint {
    public String jointName;
    private final UnitOfAngle unitOfAngle;
    private final ArmReference referenceA;
    private final double angleLimitA;
    private final ArmReference referenceB;
    private final double angleLimitB;
    private double currentAngle;
    private double oldAngle;
    private double deltaAngle;
    private double currentSpeed;
    private double oldSpeed;
    private double deltaSpeed;

    public double angleOffset; // Should be set to the default position

    public ArmJoint(String jointName, UnitOfAngle unitOfAngle, double angleA, ArmReference referenceA, double angleB, ArmReference referenceB, double angleOffset) {
        this.jointName = jointName;
        this.unitOfAngle = unitOfAngle;
        this.angleLimitA = angleA;
        this.referenceA = referenceA;
        this.angleLimitB = angleB;
        this.referenceB = referenceB;
        this.angleOffset = angleOffset;
    }

    public int getTicksLimit(ArmReference target) {
        return UtilityKit.armDegreesToTicks(getAngleLimit(target, UnitOfAngle.DEGREES));
    }

    public double getAngleLimit(ArmReference target, UnitOfAngle targetUnitOfAngle) {
        double output;
        if (referenceA == target) {
            output = angleLimitA;
        }
        else if (referenceB == target) {
            output = angleLimitB;
        }
        else {
            return 0;
        }

        if (unitOfAngle != targetUnitOfAngle) {
            if (unitOfAngle == UnitOfAngle.DEGREES) {
                output = Math.toRadians(output);
            }
            else {
                output = Math.toDegrees(output);
            }
        }
        return output;
    }

    public int getCurrentTicks() {return UtilityKit.armDegreesToTicks(getCurrentAngle(UnitOfAngle.DEGREES));}

    public double getCurrentAngle(UnitOfAngle unit) {
        double output = currentAngle;
        if (unitOfAngle != unit) {
            if (unitOfAngle == UnitOfAngle.DEGREES) {
                output = Math.toRadians(output);
            }
            else {
                output = Math.toDegrees(output);
            }
        }
        return output;
    }

    public int getDeltaTicks() {return UtilityKit.armDegreesToTicks(getDeltaAngle(UnitOfAngle.DEGREES));}

    public double getDeltaAngle(UnitOfAngle unit) {
        double output = deltaAngle;
        if (unitOfAngle != unit) {
            if (unitOfAngle == UnitOfAngle.DEGREES) {
                output = Math.toRadians(output);
            }
            else {
                output = Math.toDegrees(output);
            }
        }
        return output;
    }

    public int getOldTicks() {return UtilityKit.armDegreesToTicks(getOldAngle(UnitOfAngle.DEGREES));}

    public double getOldAngle(UnitOfAngle unit) {
        double output = oldAngle;
        if (unitOfAngle != unit) {
            if (unitOfAngle == UnitOfAngle.DEGREES) {
                output = Math.toRadians(output);
            }
            else {
                output = Math.toDegrees(output);
            }
        }
        return output;
    }

    public void updateAngles(int currentTicks) {
        //TODO: Determine should/how angleOffset applies here
        oldAngle = currentAngle;
        currentAngle = UtilityKit.armTicksToDegrees(currentTicks);
        deltaAngle = currentAngle - oldAngle;
    }

    public int getCurrentSpeed() {return UtilityKit.armDegreesToTicks(getCurrentSpeed(UnitOfAngle.DEGREES));}

    public double getCurrentSpeed(UnitOfAngle unit) {
        double output = currentSpeed;
        if (unitOfAngle != unit) {
            if (unitOfAngle == UnitOfAngle.DEGREES) {
                output = Math.toRadians(output);
            }
            else {
                output = Math.toDegrees(output);
            }
        }
        return output;
    }

    public int getDeltaSpeedTicks() {return UtilityKit.armDegreesToTicks(getDeltaSpeed(UnitOfAngle.DEGREES));}

    public double getDeltaSpeed(UnitOfAngle unit) {
        double output = deltaSpeed;
        if (unitOfAngle != unit) {
            if (unitOfAngle == UnitOfAngle.DEGREES) {
                output = Math.toRadians(output);
            }
            else {
                output = Math.toDegrees(output);
            }
        }
        return output;
    }

    public int getOldSpeedTicks() {return UtilityKit.armDegreesToTicks(getOldSpeed(UnitOfAngle.DEGREES));}

    public double getOldSpeed(UnitOfAngle unit) {
        double output = oldSpeed;
        if (unitOfAngle != unit) {
            if (unitOfAngle == UnitOfAngle.DEGREES) {
                output = Math.toRadians(output);
            }
            else {
                output = Math.toDegrees(output);
            }
        }
        return output;
    }

    public void updateSpeed(double currentSpeedTicks) {
        oldSpeed = currentSpeed;
        currentSpeed = UtilityKit.armTicksToDegrees(currentSpeedTicks);
        deltaSpeed = currentSpeed - oldSpeed;
    }
}
