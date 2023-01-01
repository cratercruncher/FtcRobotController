package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="CameraTestOpMode", group="Opmode")
public class CameraTestOpMode extends OpMode {

    @Override
    public void init() {
//        Cameras cameras = new Cameras();
//        int count = cameras.cameraCount();
//        Log.i("Camera", "Init Camera count is: " + count);
//        telemetry.addData("Init Camera count", count);
//        telemetry.update();
    }

    @Override
    public void loop() {
       /* Cameras cameras = new Cameras();
        int count = cameras.cameraCount();
        Log.i("Camera", "Loop Camera count is: " + count);
        telemetry.addData("Loop Camera count", count);
        telemetry.update();*/
    }

    @Override
    public void start(){
        resetRuntime();
    }

    @Override
    public void stop() {}
}
