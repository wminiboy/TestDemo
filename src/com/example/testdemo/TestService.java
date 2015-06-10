package com.example.testdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {  
 
    private int x = 10;
    
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null; 
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Log.d("yanjun","onStartCommand x=" + x +" "+this.toString());
        x = 100;
        
        int ret = super.onStartCommand(intent, flags, startId);
        
        Log.d("yanjun","start return = " + ret);
        return Service.START_STICKY;
    }
    
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("yanjun","onDestroy  x=" + x +" "+this.toString());
        super.onDestroy();
    }
}
