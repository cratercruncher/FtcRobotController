#include <jni.h>
#include "include/librealsense2/rs.hpp"

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("ftcrobotcontroller");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("ftcrobotcontroller")
//      }
//    }
extern "C"
JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_teamcode_Cameras_cameraCount(JNIEnv *env, jobject thiz) {
    // TODO: implement cameraCount()
    rs2::context ctx;
    return ctx.query_devices().size();
}