// "WaveVR SDK
// © 2017 HTC Corporation. All Rights Reserved.
//
// Unless otherwise required by copyright law and practice,
// upon the execution of HTC SDK license agreement,
// HTC grants you access to and use of the WaveVR SDK(s).
// You shall fully comply with all of HTC’s SDK license agreement terms and
// conditions signed by you and all SDK and API requirements,
// specifications, and documentation provided by HTC to You."

#include <log.h>
#include <GLES3/gl3.h>
#include "hellovr.h"


// Return micro second.  Should always positive because now is bigger.
#define timeval_subtract(now, last) \
    ((now.tv_sec - last.tv_sec) * 1000000LL + now.tv_usec - last.tv_usec)

//#define AUTO_ROTATE_TEST

#undef LOGENTRY
#define LOGENTRY(...)


bool gDebug = true;
bool gMsaa = true;
bool gScene = false;

#define LOGDIF(args...) if (gDebug) LOGD(args)

#define VR_MAX_CLOCKS 200


MainApplication::MainApplication()
{
    // other initialization tasks are done in init
    LOGI("MainApplication::MainApplication()");
}

MainApplication::~MainApplication() {
    // work is done in Shutdown
    LOGI("Shutdown");
}

static void printGLString(const char *name, GLenum s) {
    const char *v = (const char *) glGetString(s);
    LOGI("GL %s = %s\n", name, v);
}

bool MainApplication::initVR() {
    LOGENTRY();
     LOGI("MainApplication::initVR()");

    return true;
}


void MainApplication::shutdownVR() {
//    XXX_Quit();
}

//-----------------------------------------------------------------------------
// Purpose: Poll events.  Quit application if return true.
//-----------------------------------------------------------------------------
bool MainApplication::handleInput() {
    LOGENTRY();

    // No quit.
    return false;
}

