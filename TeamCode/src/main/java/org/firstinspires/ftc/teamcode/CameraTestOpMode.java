package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="CameraTestOpMode", group="Opmode")
public class CameraTestOpMode extends OpMode {

    @Override
    public void init() {
        Cameras cameras = new Cameras();
        Log.i("Camera", "Init Camera count is: " + cameras.getCameraCount());

    }

    @Override
    public void loop() {

    }

    @Override
    public void start(){

    }

    @Override
    public void stop() {}
}
