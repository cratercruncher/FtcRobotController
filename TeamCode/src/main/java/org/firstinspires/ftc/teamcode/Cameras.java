package org.firstinspires.ftc.teamcode;

import android.util.Log;

public class Cameras {
    static {
        try {
            System.loadLibrary("ftcrobotcontroller");
        } catch (UnsatisfiedLinkError | Exception e) {
            Log.e("Camera", "Failed to load native code", e);
        }
    }

    public Cameras() {
        Log.i("Camera", "Camera count is: " + cameraCount());
    }

    public native int cameraCount();

    public int getCameraCount(){
        return cameraCount();
    }

}
