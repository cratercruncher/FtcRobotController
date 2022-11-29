package org.firstinspires.ftc.teamcode;

public class NativeWrap {
    static {
        System.loadLibrary("native");
        System.loadLibrary("ftcrobotcontroller");
    }

    public String myMessage = "No Message.";

    // Declare a native method sayHello() that receives no arguments and returns void
    public native void sayHello();
}
