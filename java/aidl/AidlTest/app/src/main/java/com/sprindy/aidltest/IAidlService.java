package com.sprindy.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class IAidlService extends Service {
    public String TAG = "SPRINDY_AIDL";
    private ArrayList<Person> persons;

    public IAidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        persons = new ArrayList<Person>();

        return iBinder;
    }

    private IBinder iBinder =  new IExtAidlInterface.Stub(){

        @Override
        public void basicTypes(byte aByte, int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Person> add(Person person) throws RemoteException {

            persons.add(person);
            return persons;
        }
    };
}
