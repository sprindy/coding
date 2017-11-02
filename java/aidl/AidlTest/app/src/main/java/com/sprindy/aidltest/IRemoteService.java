package com.sprindy.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by sprindy on 11/1/17.
 */

public class IRemoteService extends Service {

    public String TAG = "SPRINDY_AIDL";

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new ISprindyAidl.Stub(){
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d(TAG, "add: AIDL add nums: " + num1 +" + " + num2);
            return num1 + num2;
        }
    };
}
