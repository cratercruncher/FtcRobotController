#include <jni.h>

#include <android/log.h>
//#include <librealsense2/rs.hpp>

#define  LOG_TAG    "testjni"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)


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
JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_NativeWrap_sayHello(JNIEnv *env, jobject thiz) {
    // TODO: implement sayHello()
    ALOG("This message comes from C at line %d.", __LINE__);
}