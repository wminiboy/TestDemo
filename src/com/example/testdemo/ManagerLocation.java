/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ManagerLocation.java
 * Author:   zhangxiaoxiao
 * Date:     2013-5-15 下午2:47:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.example.testdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class ManagerLocation {
    private static final String TAG = "ManagerLocation";
    private Context mContext;
    private boolean isFinishLocation = false;
    private boolean status3G = false;
    private boolean isClose3G = false;
    private boolean isOpenWifi = false;
    private OnLocationFinishListener listener = null;

    private static final int HANDLER_LOCATION = 0;
    private static final int HANDLER_LOCATION_FINISH = 1;
    private static final int HANDLER_TIME_OUT = 2;
    private static final int HANDLER_WIFI_TIME_OUT = 3;
    private static final int HANDLER_START_LOCATION = 4;

    public static final int LOCATION_FAIL = 0;
    public static final int LOCATION_SUCCESS = 1;
    public static final int LOCATION_ONLY_GPS = 2;
    private static ManagerLocation mManagerLocation = null;

    private ConcurrentLinkedQueue<OnLocationFinishListener> mListenerList = new ConcurrentLinkedQueue<ManagerLocation.OnLocationFinishListener>();
    private LocationClient mLocationClient = null;
    private Handler handler;
    private BDLocationListener myListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation loca) {
            double lat;
            double lon;
            int errorCode = loca.getLocType();
            String addr = loca.getAddrStr();
            Log.e("tina::", "addr = " + addr);
            if (errorCode == 61) {// GPS result
                lat = loca.getLatitude();
                lon = loca.getLongitude();
                locationSuccess(lat, lon, LOCATION_ONLY_GPS);
                handler.sendEmptyMessage(HANDLER_LOCATION_FINISH);
            } else if (errorCode == 161) {// network location result
                lat = loca.getLatitude();
                lon = loca.getLongitude();
                locationSuccess(lat, lon, LOCATION_SUCCESS);
                handler.sendEmptyMessage(HANDLER_LOCATION_FINISH);
            }
        }

    };

    public synchronized static ManagerLocation getInstance(Context context) {
        if (null == mManagerLocation) {
            mManagerLocation = new ManagerLocation(context.getApplicationContext());
        }

        return mManagerLocation;
    }

    public ManagerLocation(Context context) {
        mContext = context;
        //判断当前线程的Looper是否是MainLooper
        if (Looper.myLooper() != context.getMainLooper()){
            Looper.prepare();
        }
        handler = new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleLocationMessage(msg);
            }
        };
        isFinishLocation = true;
        mLocationClient = new LocationClient(mContext);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setAddrType("all");
        option.setCoorType("bd09ll");
        option.setScanSpan(5000);
        //option.disableCache(true);
        option.setOpenGps(true);
        option.setProdName("AssistantServer");
        //option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);
    }

    public void doActive() {

        synchronized (this) {
            handler.sendEmptyMessage(HANDLER_START_LOCATION);
        }
    }

    private void doLocation() {
        Log.e(TAG, "isFinishLocation = " + isFinishLocation);
        Log.e(TAG, "mLocationClient.isStarted() = " + mLocationClient.isStarted());
        Log.e(TAG, "mListenerList.isEmpty() = " + mListenerList.isEmpty());
        if (!isFinishLocation || mLocationClient.isStarted() || mListenerList.isEmpty()) {
            Log.e(TAG, "Location has been started, but not finish yet.");
            return;
        }

        getActiveNetwork();
        if (isClose3G) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int times = 0;
                    while (times < 10) {
                        try {
                            Thread.sleep(500);
                            times++;
                        } catch (InterruptedException e) {
                        }
                    }

                }
            }).start();
        }
        sendLocationMsg();
    }

    private void sendLocationMsg() {
        isFinishLocation = false;
        handler.sendEmptyMessage(HANDLER_LOCATION);
    }

    private void locationSuccess(double lat, double lon, int locationType) {
        synchronized (this) {
            // if (listener != null) {
            // listener.onFinish(locationType, lat, lon, isOpenWifi, isClose3G);
            // }

            for (OnLocationFinishListener listener : mListenerList) {
                if (null != listener) {
                    listener.onFinish(locationType, lat, lon, isOpenWifi, isClose3G);
                }
            }
            mListenerList.clear();

            handler.sendEmptyMessage(HANDLER_LOCATION_FINISH);
        }
    }

    private void locationFail() {
        synchronized (this) {
            isFinishLocation = true;
            for (OnLocationFinishListener listener : mListenerList) {
                if (null != listener) {
                    listener.onFinish(LOCATION_FAIL, 0.0D, 0.0D, isOpenWifi, isClose3G);
                }
                mListenerList.clear();
            }
        }
    }

    public void setOnFinishLisenerListenForOnce(OnLocationFinishListener l) {
        synchronized (this) {
            mListenerList.add(l);
        }
    }

    public void setOnFinishListener(OnLocationFinishListener l) {
        // listener = l;
    }

    private void getActiveNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        if (null == activeNetInfo) {
            status3G = false;
            toggleNetWork(!status3G);
            return;
        }
        if (ConnectivityManager.TYPE_WIFI == activeNetInfo.getType()) {
            handler.removeMessages(HANDLER_WIFI_TIME_OUT);
            handler.sendEmptyMessageDelayed(HANDLER_WIFI_TIME_OUT, 30000);
        } else if (ConnectivityManager.TYPE_MOBILE == activeNetInfo.getType()) {
            status3G = true;
            isClose3G = false;
        } else {
            if (!status3G) {
                status3G = false;
                toggleNetWork(!status3G);
            }
        }
        return;

    }

    private void toggleNetWork(boolean isOpen2) {
        ConnectivityManager conManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        Class<?> conManagerClass = null; // C M class
        Field iconManagerField = null; // CM Class column
        Class<?> iconManagerClass = null; // IC M class
        Object iconManager = null; // IC M class
        Method setMobileDataEnabledMethod = null; // setMobileDataEnabled method

        try {
            conManagerClass = Class.forName(conManager.getClass().getName());

            iconManagerField = conManagerClass.getDeclaredField("mService");
            iconManagerField.setAccessible(true);
            iconManager = iconManagerField.get(conManager);
            iconManagerClass = Class.forName(iconManager.getClass().getName());
            setMobileDataEnabledMethod = iconManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(iconManager, isOpen2);
            status3G = true;
            isClose3G = true;
        } catch (Exception e) {
        }
    }

    public interface OnLocationFinishListener {
        void onFinish(int type, Double geoLat, Double geoLng, boolean beforeWifi, boolean isClose3G);
    }

    private void setWifiStatus(final boolean value) {
        WifiManager mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mWifiManager.setWifiEnabled(value);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int times = 0;
                while (times < 10) {
                    try {
                        Thread.sleep(500);
                        times++;
                    } catch (InterruptedException e) {
                    }
                }
                getActiveNetwork();
                while (times < 20) {
                    try {
                        Thread.sleep(500);
                        times++;
                    } catch (InterruptedException e) {
                    }
                }
                sendLocationMsg();
            }
        }).start();
    }

    private void handleLocationMessage(Message msg) {
        int what = msg.what;
        switch (what) {
            case HANDLER_LOCATION_FINISH:
                isFinishLocation = true;
                mLocationClient.stop();
                break;
            case HANDLER_LOCATION:
                if (mLocationClient != null) {
                    mLocationClient.start();
                    Log.d("yanjun","HANDLER_LOCATION");
                } else {
                    isFinishLocation = true;
                }
                break;
            case HANDLER_TIME_OUT:
                if (!isFinishLocation) {
                    Log.d("yanjun","HANDLER_TIME_OUT");
                    mLocationClient.stop();
                    locationFail();
                }
                break;
            case HANDLER_WIFI_TIME_OUT:
                if (!isFinishLocation) {
                    Log.d("yanjun","HANDLER_WIFI_TIME_OUT");
                    mLocationClient.stop();
                    locationFail();
                    isOpenWifi = true;
                }
                break;
            case HANDLER_START_LOCATION:
                doLocation();
                break;
            default:
                break;
        }
    }
}