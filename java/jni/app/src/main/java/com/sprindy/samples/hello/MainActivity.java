// "WaveVR SDK 
// © 2017 HTC Corporation. All Rights Reserved.
//
// Unless otherwise required by copyright law and practice,
// upon the execution of HTC SDK license agreement,
// HTC grants you access to and use of the WaveVR SDK(s).
// You shall fully comply with all of HTC’s SDK license agreement terms and
// conditions signed by you and all SDK and API requirements,
// specifications, and documentation provided by HTC to You."

package com.sprindy.samples.hello;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


public class MainActivity extends Activity {
    private static final String TAG = "hello";


    private boolean mDebug = false;
    private int mFlag = 1;

    static {
        System.loadLibrary("jninative");
    }
    public MainActivity() {
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"onReceive: start");

            setFlag(mFlag);

            Log.i(TAG,"onReceive: end2");
        }
    };

    static native void setFlag(int flag);

    @Override
    protected void onCreate(Bundle icicle) {
        Log.i(TAG,"onCreate:call init");
        init(getResources().getAssets(), mRListener);
        super.onCreate(icicle);

        // dump verion information
        try {
            Log.i(TAG, "(Native Hello) : ");
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(getApplicationInfo().packageName, 0);
            Log.i(TAG, "Native Hello version name: " + info.versionName + " code: " + info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);

        setFlag(mFlag);
         Log.i(TAG,"onCreate:end");
    }


    @Override
    protected void onResume() {
        super.onResume();
         Log.i(TAG,"onResume:start");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause:start");
    }



    // Pass this acitivty instance to native
    @SuppressWarnings("unused")
    public native void init(AssetManager am, Controller.Listener listener);

    public Controller.Listener mRListener = new Controller.Listener() {
        @Override
        public void onButtonDataUpdate(int deviceType,int keycode, boolean press) {
            Log.d(TAG,"ButtonEvent " + "deviceType" + deviceType + "keycode " + keycode + " press " + press);
        }

        @Override
        public void onAnalogDataUpdate(int deviceType,float Axis_x ,float Axis_y) {
            Log.d(TAG,"TrackPadEvent " +  "deviceType" + deviceType +" x " + Axis_x + " y " + Axis_y);
        }

        @Override
        public void onImuDataUpdate(int deviceType,float[] data) {
        /*
        *       data[0] = q.w;
                data[1] = q.x;
                data[2] = q.y;
                data[3] = q.z;
                data[4] = pos.x;
                data[5] = pos.y;
                data[6] = pos.z;
                data[7] =  mDevicePairs[nDevice].pose.velocity.v[0];
                data[8] =  mDevicePairs[nDevice].pose.velocity.v[1];
                data[9] =  mDevicePairs[nDevice].pose.velocity.v[2];
                data[10] = mDevicePairs[nDevice].pose.angularVelocity.v[0];
                data[11] = mDevicePairs[nDevice].pose.angularVelocity.v[1];
                data[12] = mDevicePairs[nDevice].pose.angularVelocity.v[2];
        * */
            Log.d(TAG,"onImuDataUpdate " +  "deviceType" + deviceType +" q.w " + data[0] + " q.x " + data[1]+" q.y " + data[2] + " q.z " + data[3]);

        }
    };

}
