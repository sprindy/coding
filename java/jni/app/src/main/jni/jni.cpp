// "WaveVR SDK 
// © 2017 HTC Corporation. All Rights Reserved.
//
// Unless otherwise required by copyright law and practice,
// upon the execution of HTC SDK license agreement,
// HTC grants you access to and use of the WaveVR SDK(s).
// You shall fully comply with all of HTC’s SDK license agreement terms and
// conditions signed by you and all SDK and API requirements,
// specifications, and documentation provided by HTC to You."

#define  LOG_TAG    "hello_jni"
#include <log.h>
#include <hellovr.h>
#include <unistd.h>
#include "jni.h"


// DEBUG can be disabled when the Activity ask.
extern bool gDebug;


struct fields_t {
    JavaVM      *mVM;
    jclass      remotedesktopclass;
    jobject     remotedesktopobject;
    pthread_key_t mThreadKey;

};
static fields_t fields;

int main(int argc, char *argv[]) {
    LOGENTRY();
    LOGI("Hello main, new MainApplication ");
    MainApplication *app = new MainApplication();
    LOGI("HelloVR main, start call app->initVR()");
    if (!app->initVR()) {
        LOGW("HelloVR main, initVR fail,start call app->shutdownVR()");
        app->shutdownVR();
        return 1;
    }


    while (1) {
        if (app->handleInput())
            break;
    }


    return 0;
}

extern "C" {
    JNIEXPORT void JNICALL Java_com_sprindy_samples_MainActivity_init(JNIEnv * env, jobject act, jobject am);
    JNIEXPORT void JNICALL Java_com_sprindy_samples_MainActivity_setFlag(JNIEnv * env, jclass clazz, jint flag);
};

static void Android_JNI_ThreadDestroyed(void* value)
{
    /* The thread is being destroyed, detach it from the Java VM and set the
        mThreadKey value to NULL as required */
    JNIEnv *env = (JNIEnv*) value;
    if (env != NULL) {
        (fields.mVM)->DetachCurrentThread();
        pthread_setspecific(fields.mThreadKey, NULL);
    }
}

JNIEXPORT void JNICALL Java_com_sprindy_samples_MainActivity_init(JNIEnv * env, jobject activityInstance, jobject assetManagerInstance, jobject ctrlListenInstance) {
     LOGI("MainActivity_init: call  Context::getInstance()->init");
//    Context::getInstance()->init(env, assetManagerInstance);
    int ret = env->GetJavaVM(&fields.mVM);
    jclass temp_class =  env->GetObjectClass(ctrlListenInstance);
    fields.remotedesktopclass = (jclass)env->NewGlobalRef(temp_class);
    fields.remotedesktopobject = env->NewGlobalRef(ctrlListenInstance);
    if (pthread_key_create(&fields.mThreadKey, Android_JNI_ThreadDestroyed) != 0) {
        LOGE("Error initializing pthread key");
    }
    pthread_setspecific(fields.mThreadKey, (void*) env);
}

JNIEXPORT void JNICALL Java_com_sprindy_samples_MainActivity_setFlag(JNIEnv * env, jclass clazz, jint flag) {
    // Flags are defined in java Activity.
    gDebug = (flag & 0x1) != 0;
    LOGD("gDebug = %d", gDebug ? 1 : 0);
}

void Java_com_sprindy_samples_MainActivity_onButtonDataUpdate
        (int deviceType,int keycode, bool press)
{
    JavaVMAttachArgs args = {JNI_VERSION_1_6, __FUNCTION__,__null};
    JNIEnv*env = __null;
    int ret = (fields.mVM)->AttachCurrentThread(&env, &args);
    pthread_setspecific(fields.mThreadKey, (void*) env);
    jmethodID onButtonDataUpdate = env->GetMethodID(fields.remotedesktopclass,
                                                     "onButtonDataUpdate", "(IIZ)V");
    env->CallVoidMethod(fields.remotedesktopobject, onButtonDataUpdate, (int)deviceType,(int)keycode, press);

}

void Java_com_sprindy_samples_MainActivity_onAnalogDataUpdate(int deviceType,float Axis_x, float Axis_y)
{
    JavaVMAttachArgs args = {JNI_VERSION_1_6, __FUNCTION__,__null};
    JNIEnv*env = __null;
    int ret = (fields.mVM)->AttachCurrentThread(&env, &args);
    pthread_setspecific(fields.mThreadKey, (void*) env);
    jmethodID onAnalogDataUpdate = env->GetMethodID(fields.remotedesktopclass,
                                                          "onAnalogDataUpdate", "(IFF)V");
    env->CallVoidMethod(fields.remotedesktopobject, onAnalogDataUpdate, (int)deviceType,Axis_x,Axis_y);

}

void Java_com_sprindy_samples_MainActivity_onImuDataUpdate(int deviceType,float data[])
{
    JavaVMAttachArgs args = {JNI_VERSION_1_6, __FUNCTION__,__null};
    JNIEnv*env = __null;
    int ret = (fields.mVM)->AttachCurrentThread(&env, &args);
    pthread_setspecific(fields.mThreadKey, (void*) env);
    jmethodID onImuDataUpdate = env->GetMethodID(fields.remotedesktopclass,
                                                          "onImuDataUpdate", "(I[F)V");
    jfloatArray j_data;
    j_data = env->NewFloatArray(13);
    env->SetFloatArrayRegion(j_data,0,12,data);
    env->CallVoidMethod(fields.remotedesktopobject, onImuDataUpdate, (int)deviceType,j_data);
    env->DeleteLocalRef(j_data);

}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    return JNI_VERSION_1_6;
}

jint JNI_OnUnLoad(JavaVM* vm, void* reserved) {
}

