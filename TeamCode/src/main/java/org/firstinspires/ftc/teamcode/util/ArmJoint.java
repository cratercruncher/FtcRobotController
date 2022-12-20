package org.firstinspires.ftc.teamcode.util;

public class ArmJoint {
    public String jointName;
    private UnitOfAngle unitOfAngle;
    private ArmReference referenceA;
    private double angleA;
    private ArmReference referenceB;
    private double angleB;
    //UnitOfDistance unitOfDistance;
    //Vector3D position;

    private double angleOffset; // Should be set to the default position

    public ArmJoint(String jointName, UnitOfAngle unitOfAngle, double angleA, ArmReference referenceA, double angleB, ArmReference referenceB, double angleOffset) {
        this.jointName = jointName;
        this.unitOfAngle = unitOfAngle;
        this.angleA = angleA;
        this.referenceA = referenceA;
        this.angleB = angleB;
        this.referenceB = referenceB;
        this.angleOffset = angleOffset;
    }

    public int getTicks(ArmReference target) {
        return UtilityKit.armDegreesToTicks(getAngle(target, UnitOfAngle.DEGREES));
    }

    public double getAngle(ArmReference target, UnitOfAngle targetUnitOfAngle) {
        double output;
        if (referenceA == target) {
            output = angleA;
        }
        else if (referenceB == target) {
            output = angleB;
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
}
