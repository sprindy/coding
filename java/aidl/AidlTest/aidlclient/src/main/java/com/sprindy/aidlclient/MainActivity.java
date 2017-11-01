package com.sprindy.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "SPRINDY";

    private EditText mEtNum1;
    private EditText mEtNum2;
    private EditText mEtNum3;
    private Button mBtnAdd;

    ISprindyAidl iSprindyAidl;


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            iSprindyAidl = ISprindyAidl.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iSprindyAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        bindService();
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
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

//        int num1 = Integer.parseInt(mEtNum1.getText().toString());
//        int num2 = Integer.parseInt(mEtNum2.getText().toString());
        int num1 = 0;
        int num2 = 0;
//        int sum = num1 + num2;

        //TODO
        if (iSprindyAidl == null) {
            Log.e(TAG, "onClick: iSprindyAidl = null!!!");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(conn);
    }
}

