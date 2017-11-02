package com.sprindy.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sprindy.aidltest.ISprindyAidl;
import com.sprindy.aidltest.IExtAidlInterface;
import com.sprindy.aidltest.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "SPRINDY_AIDL";

    private EditText mEtNum1;
    private EditText mEtNum2;
    private EditText mEtNum3;
    private Button   mBtnAdd;
    private Button   mBtnPadd;

    private ISprindyAidl iSprindyAidl;
    private IExtAidlInterface iExtAidl;


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            iSprindyAidl = ISprindyAidl.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
            iSprindyAidl = null;
        }
    };

    private ServiceConnection mConnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onService2Connected: ");
            iExtAidl = IExtAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onService2Disconnected: ");
            iExtAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        bindService();

        bindService2(mConnect);
    }

    private void bindService2(ServiceConnection mConnect) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.sprindy.aidltest", "com.sprindy.aidltest.IAidlService"));

        bindService(intent, mConnect, Context.BIND_AUTO_CREATE);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.sprindy.aidltest", "com.sprindy.aidltest.IRemoteService"));

        //bind service
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        mEtNum1 = (EditText)findViewById(R.id.et_num1);
        mEtNum2 = (EditText)findViewById(R.id.et_num2);
        mEtNum3 = (EditText)findViewById(R.id.et_num3);

        mBtnAdd = (Button) findViewById(R.id.bt_add);
        mBtnPadd = (Button) findViewById(R.id.bt_padd);
        mBtnAdd.setOnClickListener(this);
        mBtnPadd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_add:
                onClickDealAdd();
                break;
            case R.id.bt_padd:
                onClickDealPadd();
                break;
            default:
                break;
        }

    }

    private void onClickDealAdd() {
        int num1 = Integer.parseInt(mEtNum1.getText().toString());
        int num2 = Integer.parseInt(mEtNum2.getText().toString());
//        int num1 = 0;
//        int num2 = 0;
//        int sum = num1 + num2;

        //TODO
        if (iSprindyAidl == null) {
            Log.e(TAG, "onClick: iSprindyAidl = null!!!");
            Toast.makeText(this, "iSprindyAidl = null!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        int sum = 0;
        try {
            sum = iSprindyAidl.add(num1, num2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        mEtNum3.setText(sum+"");
    }

    private void onClickDealPadd() {
        if (iExtAidl == null) {
            Log.e(TAG, "onClick: iExtAidl = null!!!");
            Toast.makeText(this, "iExtAidl = null!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            List<Person> persons = iExtAidl.add(new Person("sprindy", 27));
            Log.d(TAG, "onClickDealPadd:" + persons.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(conn);
    }

}

