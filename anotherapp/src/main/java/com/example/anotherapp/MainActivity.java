package com.example.anotherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;

import com.example.startservicefromanotherapp.IAppServiceRemoteBinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent intent;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput=findViewById(R.id.EtIput);
        intent=new Intent();
        intent.setComponent(new ComponentName("com.example.startservicefromanotherapp","com.example.startservicefromanotherapp.AppService"));
        findViewById(R.id.BtnStartService).setOnClickListener(this);
        findViewById(R.id.BtnStopService).setOnClickListener(this);
        findViewById(R.id.BindService).setOnClickListener(this);
        findViewById(R.id.UnBindService).setOnClickListener(this);
        findViewById(R.id.Btsync).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BtnStartService:
                startService(intent);
                break;
            case R.id.BtnStopService:
                stopService(intent);
                break;
            case R.id.BindService:
                bindService(intent,this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.UnBindService:
                unbindService(this);
                binder=null;
                break;
            case R.id.Btsync:
                if(binder!=null){
                    try {
                        binder.setData(etInput.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        System.out.println("bindServic");
        System.out.println(iBinder);
        binder=IAppServiceRemoteBinder.Stub.asInterface(iBinder);

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
    private IAppServiceRemoteBinder binder=null;
}
