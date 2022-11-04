package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.RelativeTo;
import org.firstinspires.ftc.teamcode.util.UnitOfAngle;
import org.firstinspires.ftc.teamcode.util.UtilityKit;

import java.util.ArrayList;

public class ArmController {
    //Grabber + = forward
    //Upper + = forward
    //Lower + = backward
    //Base + = backward

    // Arm positions
    ArmPosition foldedPosition = new ArmPosition(-60, 160, -160, 135, UnitOfAngle.DEGREES, RelativeTo.ARM, "Folded Position");
    ArmPosition carryPosition = new ArmPosition(-30, 90, -90, 90, UnitOfAngle.DEGREES, RelativeTo.ARM, "Carry Position");
    ArmPosition closePosition = new ArmPosition(-30, 90, 90, -105, UnitOfAngle.DEGREES, RelativeTo.ARM, "Close Grab Position");
    ArmPosition farPosition = new ArmPosition(30, 15, 45, -90, UnitOfAngle.DEGREES, RelativeTo.ARM, "Far Grab Position");
    ArmPosition highPosition = new ArmPosition(-60, 30, -100, -25, UnitOfAngle.DEGREES, RelativeTo.ARM, "High Junction Position");
    ArmPosition middlePosition = new ArmPosition(-60, 30, 140, -65, UnitOfAngle.DEGREES, RelativeTo.ARM, "Middle Junction Position");
    ArmPosition lowPosition = new ArmPosition(-60, 90, 130, -115, UnitOfAngle.DEGREES, RelativeTo.ARM, "Low Junction Position");
    ArmPosition straightUpPosition = new ArmPosition(0, 0, 0, 0, UnitOfAngle.DEGREES, RelativeTo.ARM, "Straight Up Position"); // 4 testing

    ArmPosition placeTransition = new ArmPosition(0, 0, 0, 0, UnitOfAngle.DEGREES, RelativeTo.ARM, "Transition");
//    ArmPosition placeTransition = new ArmPosition(-20, 90, 90, -90, UnitOfAngle.DEGREES, RelativeTo.ARM, "Transition");
    ArmPosition carryTransition = placeTransition; //new ArmPosition(-45, 120, 0, 0, UnitOfAngle.DEGREES, RelativeTo.ARM);
    ArmPosition grabTransition = placeTransition;
    ArmPosition zeroPosition = new ArmPosition(0, 0, 0, 0, UnitOfAngle.DEGREES, RelativeTo.ARM, "Zero Position");

    ArmPosition targetPosition;
    ArmPosition lastPosition;

    ArmPosition tickTarget;
    boolean currentSequenceDone = true;
    int sequenceIndex = 0;

    // Gear Ratio = 188:1
    // Encoder Shaft = 28 pulses per revolution
    // Gearbox Output = 5281.1 pulses per revolution (*1.4 for small sprocket)
    public int grabberTicks = 0;
    public int upperTicks = 0;
    public int lowerTicks = 0;
    public int baseTicks = 0;

    private Telemetry telemetry;

    // Arm sequences
    ArrayList<ArmPosition> foldedToCarry = new ArrayList<>();

    ArrayList<ArmPosition> upToCarry = new ArrayList<>();
    ArrayList<ArmPosition> carryToUp = new ArrayList<>();

    ArrayList<ArmPosition> carryToFold = new ArrayList<>();
    ArrayList<ArmPosition> carryToFar = new ArrayList<>();
    ArrayList<ArmPosition> carryToClose = new ArrayList<>();
    ArrayList<ArmPosition> carryToHigh = new ArrayList<>();
    ArrayList<ArmPosition> carryToMiddle = new ArrayList<>();
    ArrayList<ArmPosition> carryToLow = new ArrayList<>();

    ArrayList<ArmPosition> farToFolded = new ArrayList<>();
    ArrayList<ArmPosition> farToCarry = new ArrayList<>();
    ArrayList<ArmPosition> farToClose = new ArrayList<>();
    ArrayList<ArmPosition> farToHigh = new ArrayList<>();
    ArrayList<ArmPosition> farToMiddle = new ArrayList<>();
    ArrayList<ArmPosition> farToLow = new ArrayList<>();

    ArrayList<ArmPosition> closeToFolded = new ArrayList<>();
    ArrayList<ArmPosition> closeToCarry = new ArrayList<>();
    ArrayList<ArmPosition> closeToFar = new ArrayList<>();
    ArrayList<ArmPosition> closeToHigh = new ArrayList<>();
    ArrayList<ArmPosition> closeToMiddle = new ArrayList<>();
    ArrayList<ArmPosition> closeToLow = new ArrayList<>();

    ArrayList<ArmPosition> highToFold = new ArrayList<>();
    ArrayList<ArmPosition> highToCarry = new ArrayList<>();
    ArrayList<ArmPosition> highToMiddle = new ArrayList<>();
    ArrayList<ArmPosition> highToLow = new ArrayList<>();
    ArrayList<ArmPosition> highToFar = new ArrayList<>();
    ArrayList<ArmPosition> highToClose = new ArrayList<>();

    ArrayList<ArmPosition> middleToFolded = new ArrayList<>();
    ArrayList<ArmPosition> middleToCarry = new ArrayList<>();
    ArrayList<ArmPosition> middleToHigh = new ArrayList<>();
    ArrayList<ArmPosition> middleToLow = new ArrayList<>();
    ArrayList<ArmPosition> middleToFar = new ArrayList<>();
    ArrayList<ArmPosition> middleToClose = new ArrayList<>();

    ArrayList<ArmPosition> lowToFolded = new ArrayList<>();
    ArrayList<ArmPosition> lowToCarry = new ArrayList<>();
    ArrayList<ArmPosition> lowToHigh = new ArrayList<>();
    ArrayList<ArmPosition> lowToMiddle = new ArrayList<>();
    ArrayList<ArmPosition> lowToFar = new ArrayList<>();
    ArrayList<ArmPosition> lowToClose = new ArrayList<>();

    ArrayList<ArmPosition> currentSequence;
    ArrayList<ArmPosition> nextSequence;

    StringBuilder sb = new StringBuilder();

    public void initialize(Telemetry telemetry) {
        this.telemetry = telemetry;
        // store the initial position of the arm
        currentSequenceDone = true;

        foldedToCarry.add(carryPosition);

        //--------------------------------- 4 testing
        carryToUp.add(straightUpPosition);
        upToCarry.add(carryPosition);

        //--------------------------------- Carry to other

        carryToFold.add(foldedPosition);
        // Initialize the current sequence to carryToFolded, and record that its done
        currentSequence = carryToFold;

        carryToFar.add(grabTransition);
        carryToFar.add(farPosition);

        carryToClose.add(grabTransition);
        carryToClose.add(closePosition);

        carryToHigh.add(placeTransition);
        carryToHigh.add(highPosition);

        carryToMiddle.add(placeTransition);
        carryToMiddle.add(middlePosition);

        carryToLow.add(placeTransition);
        carryToLow.add(lowPosition);

        //--------------------------------- Far to other

        farToFolded.add(carryTransition);
        farToFolded.add(foldedPosition);

        farToCarry.add(carryTransition);
        farToCarry.add(carryPosition);

        farToClose.add(grabTransition);
        farToClose.add(closePosition);

        farToHigh.add(placeTransition);
        farToHigh.add(highPosition);

        farToMiddle.add(placeTransition);
        farToMiddle.add(middlePosition);

        farToLow.add(placeTransition);
        farToLow.add(lowPosition);

        //--------------------------------- Close to other

        closeToFolded.add(carryTransition);
        closeToFolded.add(foldedPosition);

        closeToCarry.add(carryTransition);
        closeToCarry.add(carryPosition);

        closeToFar.add(grabTransition);
        closeToFar.add(farPosition);

        closeToHigh.add(placeTransition);
        closeToHigh.add(highPosition);

        closeToMiddle.add(placeTransition);
        closeToMiddle.add(middlePosition);

        closeToLow.add(placeTransition);
        closeToLow.add(lowPosition);

        //--------------------------------- High to other

        highToFold.add(carryTransition);
        highToFold.add(foldedPosition);

        highToCarry.add(carryTransition);
        highToCarry.add(carryPosition);

        highToFar.add(grabTransition);
        highToFar.add(farPosition);

        highToClose.add(grabTransition);
        highToClose.add(closePosition);

        highToMiddle.add(placeTransition);
        highToMiddle.add(middlePosition);

        highToLow.add(placeTransition);
        highToLow.add(lowPosition);

        //--------------------------------- Middle to other

        middleToFolded.add(carryTransition);
        middleToFolded.add(foldedPosition);

        middleToCarry.add(carryTransition);
        middleToCarry.add(carryPosition);

        middleToFar.add(grabTransition);
        middleToFar.add(farPosition);

        middleToClose.add(grabTransition);
        middleToClose.add(closePosition);

        middleToHigh.add(placeTransition);
        middleToHigh.add(highPosition);

        middleToLow.add(placeTransition);
        middleToLow.add(lowPosition);

        //--------------------------------- Low to other

        lowToFolded.add(carryTransition);
        lowToFolded.add(foldedPosition);

        lowToCarry.add(carryTransition);
        lowToCarry.add(carryPosition);

        lowToFar.add(grabTransition);
        lowToFar.add(farPosition);

        lowToClose.add(grabTransition);
        lowToClose.add(closePosition);

        lowToHigh.add(placeTransition);
        lowToHigh.add(highPosition);

        lowToMiddle.add(placeTransition);
        lowToMiddle.add(middlePosition);
    }

    public void checkForNextSequenceChangeRequest(GamePadState gamePadState) {

        // CHECK FOR HOT BUTTON REQUEST FOR A NEW MOTION SEQUENCE, AND CHANGE IF NECESSARY
        // GO TO CARRY POSITION
        if (gamePadState.dPadUp) {
            sb.append("dUp "); // log
            ArmPosition endPosition = currentSequence.get(currentSequence.size() - 1);
            if (endPosition == foldedPosition) {
                setNextSequence(foldedToCarry);
            } else if (endPosition == farPosition) {
                setNextSequence(farToCarry);
            } else if (endPosition == closePosition) {
                setNextSequence(closeToCarry);
            } else if (endPosition == highPosition) {
                setNextSequence(highToCarry);
            } else if (endPosition == middlePosition) {
                setNextSequence(middleToCarry);
            } else if (endPosition == lowPosition) {
                setNextSequence(lowToCarry);
            } else if (endPosition == straightUpPosition) {     // TEMP
                setNextSequence(upToCarry);
            }
        }
        // GO TO CLOSE POSITION
        else if (gamePadState.dPadDown) {
            sb.append("dDown "); // log
            ArmPosition endPosition = currentSequence.get(currentSequence.size() - 1);
            if (endPosition == carryPosition) {
                setNextSequence(carryToClose);
            } else if (endPosition == farPosition) {
                setNextSequence(farToClose);
            } else if (endPosition == highPosition) {
                setNextSequence(highToClose);
            } else if (endPosition == middlePosition) {
                setNextSequence(middleToClose);
            } else if (endPosition == lowPosition) {
                setNextSequence(lowToClose);
            }
        }
        // GO TO FOLDED POSITION
        else if (gamePadState.dPadLeft) {
            sb.append("dLeft "); // log
            ArmPosition endPosition = currentSequence.get(currentSequence.size() - 1);
            if (endPosition == carryPosition) {
                setNextSequence(carryToFold);
            } else if (endPosition == farPosition) {
                setNextSequence(farToFolded);
            } else if (endPosition == closePosition) {
                setNextSequence(closeToFolded);
            } else if (endPosition == highPosition) {
                setNextSequence(highToFold);
            } else if (endPosition == middlePosition) {
                setNextSequence(middleToFolded);
            } else if (endPosition == lowPosition) {
                setNextSequence(lowToFolded);
            }
        }
        // GO TO FAR POSITION
        else if (gamePadState.dPadRight) {
            sb.append("dRight "); // log
            ArmPosition endPosition = currentSequence.get(currentSequence.size() - 1);
            if (endPosition == carryPosition) {
                //setNextSequence(carryToFar);
                setNextSequence(carryToUp);                     // TEMP
            } else if (endPosition == closePosition) {
                setNextSequence(closeToFar);
            } else if (endPosition == highPosition) {
                setNextSequence(highToFar);
            } else if (endPosition == middlePosition) {
                setNextSequence(middleToFar);
            } else if (endPosition == lowPosition) {
                setNextSequence(lowToFar);
            }
        }
        // GO TO HIGH POSITION
        else if (gamePadState.x) {
            sb.append("X "); // log
            ArmPosition endPosition = currentSequence.get(currentSequence.size() - 1);
            if (endPosition == carryPosition) {
                setNextSequence(carryToHigh);
            } else if (endPosition == farPosition) {
                setNextSequence(farToHigh);
            } else if (endPosition == closePosition) {
                setNextSequence(closeToHigh);
            } else if (endPosition == middlePosition) {
                setNextSequence(middleToHigh);
            } else if (endPosition == lowPosition) {
                setNextSequence(lowToHigh);
            }
        }
        // GO TO MIDDLE POSITION
        else if (gamePadState.y) {
            sb.append("Y "); // log
            ArmPosition endPosition = currentSequence.get(currentSequence.size() - 1);
            if (endPosition == carryPosition) {
                setNextSequence(carryToMiddle);
            } else if (endPosition == farPosition) {
                setNextSequence(farToMiddle);
            } else if (endPosition == closePosition) {
                setNextSequence(closeToMiddle);
            } else if (endPosition == highPosition) {
                setNextSequence(highToMiddle);
            } else if (endPosition == lowPosition) {
                setNextSequence(lowToMiddle);
            }
        }
        // GO TO LOW POSITION
        else if (gamePadState.b) {
            sb.append("B "); // log
            ArmPosition endPosition = currentSequence.get(currentSequence.size() - 1);
            if (endPosition == carryPosition) {
                setNextSequence(carryToLow);
            } else if (endPosition == farPosition) {
                setNextSequence(farToLow);
            } else if (endPosition == closePosition) {
                setNextSequence(closeToLow);
            } else if (endPosition == highPosition) {
                setNextSequence(highToLow);
            } else if (endPosition == middlePosition) {
                setNextSequence(middleToLow);
            }
        }
    }

    public void goManual(GamePadState gamePadState) {
        boolean lb = gamePadState.leftBumper;
        boolean rb = gamePadState.rightBumper;
        double lt = gamePadState.leftTrigger;
        double rt = gamePadState.rightTrigger;

        double addUpperAngle = 0;
        double addGrabberAngle = 0;

        if (rb && !lb) {
            addUpperAngle = 2;
        } else if (lb && !rb) {
            addUpperAngle = -2;
        }

        if (rt != 0 && lt == 0) {
            addGrabberAngle = rt * 2;
        } else if (lt != 0 && rt == 0) {
            addGrabberAngle = lt * -2;
        }

        // 1 deg = 14.669 ticks // also 20.537
        grabberTicks += (int) (addGrabberAngle * 5281.1 / 360);
        upperTicks += (int) (addUpperAngle * (5281.1 * 1.4) / 360);
    }

    public void updateArm(GamePadState gamePadState, Actuators actuators, Sensors sensors, boolean verbose) {

        telemetry.addData("Log", sb.toString());

        checkForNextSequenceChangeRequest(gamePadState);

        // IF SEQUENCE IS COMPLETE ENABLE MANUAL CONTROL OR IF THERE IS ANOTHER SEQUENCE IN THE QUE, START THE NEXT SEQUENCE // PUNCTUATION COURTESY OF COACH DADA!!!
        if (currentSequenceDone) {
            // If there is a new sequence in the que, start the next sequence
            if (nextSequence != null) {
                currentSequence = nextSequence;
                sb.append("NewSeq: "); // log
                sb.append(currentSequence.toString());
                nextSequence = null;
                currentSequenceDone = false;
                sequenceIndex = 0;
            }
            // if the currentSequence is done and the nextSequence is null, perform manual control
            else {
                // DO MANUAL MOVEMENT
                goManual(gamePadState);
            }
        }

        // Check if the arm motors have reached their destinations and that the current sequence is not done (officially)
        boolean armNotBusy = !actuators.baseSegment.isBusy() && !actuators.lowerSegment.isBusy() && !actuators.upperSegment.isBusy() && !actuators.grabberSegment.isBusy();
        telemetry.addData("ArmNotBusy", armNotBusy);
        telemetry.addData("CurrentSequenceDone", currentSequenceDone);

        if (!currentSequenceDone && armNotBusy) {
            ArmPosition targetRelToWorld = currentSequence.get(sequenceIndex).getArmPosition(RelativeTo.WORLD, UnitOfAngle.DEGREES);
            ArmPosition foldedRelToWorld = foldedPosition.getArmPosition(RelativeTo.WORLD, UnitOfAngle.DEGREES);
            grabberTicks = UtilityKit.grabberDegreesToTicks(targetRelToWorld.th3 - foldedRelToWorld.th3);
            upperTicks = UtilityKit.armDegreesToTicks(targetRelToWorld.th2 - foldedRelToWorld.th2);
            lowerTicks = -UtilityKit.armDegreesToTicks(targetRelToWorld.th1 - foldedRelToWorld.th1);
            baseTicks = -UtilityKit.armDegreesToTicks(targetRelToWorld.th0 - foldedRelToWorld.th0);

            sb.append("BaseTicks:" + baseTicks + " Th: " + targetRelToWorld.th0 + " ");

            // increment the sequence index or label it done
            if (sequenceIndex < currentSequence.size() - 1) {
                sb.append("SeqInd:" + sequenceIndex + " ");
                sequenceIndex++;
            }
            if (sequenceIndex >= currentSequence.size() - 1) {
                sb.append("SeqDone "); // log
                currentSequenceDone = true;
                sequenceIndex = 0;
            }
        }

        // check arm limits
        checkArmLimits(sensors);

        // DO IT!
        actuators.updateArm(grabberTicks, upperTicks, lowerTicks, baseTicks);
    }

    private void checkArmLimits(Sensors sensors){
        // If the arm segments are at their limits back away
        if (sensors.mG) {
            grabberTicks -= 20; // this one needs to move in the negative direction when pressed
        }
        if (sensors.bU) {
            upperTicks += 20; // this one needs to move in the positive direction when pressed.
        }
        if (sensors.bLA || sensors.bLB) {
            lowerTicks += 20;  // this one needs to move in negative direction when pressed, but it hooked up backwards
        }
        if (sensors.bBA || sensors.bBB) {
            baseTicks -= 20; // this one needs to move in the positive direction when pressed, but is hooked up backwards
        }
    }

    private void setNextSequence(ArrayList<ArmPosition> newSequence) {
        nextSequence = newSequence;
        sb.append("Nxt:" + newSequence.toString());
    }
}
