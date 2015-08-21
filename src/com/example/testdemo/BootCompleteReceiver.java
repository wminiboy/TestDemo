package com.example.testdemo;

import com.suning.msim.arch.MSimDeviceManager;
import com.suning.msim.arch.MSimDeviceManager.DEVICE_TYPE;
import com.suning.msim.arch.MTKMSimDevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        
        Log.d("yanjun", "action="+intent.getAction());
        
        DEVICE_TYPE type = MSimDeviceManager.getDeviceType(context);
        Log.d("yanjun", "DEVICE_TYPE="+type);
        
        MTKMSimDevice device = new MTKMSimDevice(type);
        int state = device.getSimState(context, 0);
        Log.d("yanjun", "state 0="+state);
        
        state = device.getSimState(context, 1);
        Log.d("yanjun", "state 1="+state);
        
        state = device.getSimState(context, 2);
        Log.d("yanjun", "state 2="+state);
        
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        
        Log.d("yanjun", "state tm ="+tm.getSimState());
        Log.d("yanjun", "isSmsCapable="+tm.isSmsCapable());
        
        
        Log.d("yanjun", "getDeviceId()="+tm.getDeviceId());
        
    }
}
