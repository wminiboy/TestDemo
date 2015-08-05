package com.example.testdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        
//        Log.d("yanjun", "action="+intent.getAction());
//        
//        //DEVICE_TYPE type = MSimDeviceManager.getDeviceType(context);
//        Log.d("yanjun", "DEVICE_TYPE="+type);
//        
//        MTKMSimDevice device = new MTKMSimDevice(type);
//        int state = device.getSimState(context, 0);
//        Log.d("yanjun", "state 0="+state);
//        
//        state = device.getSimState(context, 1);
//        Log.d("yanjun", "state 1="+state);
//        
//        state = device.getSimState(context, 2);
//        Log.d("yanjun", "state 2="+state);
    }
}
