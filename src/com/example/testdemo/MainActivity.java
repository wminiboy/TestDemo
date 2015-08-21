package com.example.testdemo;

import com.example.testdemo.base.BaseActvity;
import com.google.gson.Gson;
import com.suning.msim.arch.MSimDeviceBase;
import com.suning.msim.arch.MSimDeviceManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.provider.Telephony;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class MainActivity extends BaseActvity {
    
    int theme= -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        if(theme != -1){
            setTheme(theme);
        }
        
        setContentView(R.layout.activity_main);

        Log.d("yanjun", "MainActivity onCreate");
        
        Log.d("yanjun", "SyncRawContacts.CONTENT_URI="+ RawContacts.CONTENT_URI);
        Log.d("yanjun", "ContactsContract.CONTENT_URI="+ContactsContract.Contacts.CONTENT_URI);
        Log.d("yanjun", "ContactsContract.RawContacts.CONTENT_URI="+ContactsContract.RawContacts.CONTENT_URI);
        
        Cursor cursor = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, null,
                null, null);
        int columnCount = cursor.getColumnCount();
        StringBuilder builder = new StringBuilder();
        
        for(int i = 0 ;i<columnCount; i++){
            String name = cursor.getColumnName(i);
            builder.append(name);
            if(i<columnCount-1){
                builder.append("|");
            }
        }
        
        Log.d("yanjun",builder.toString());
        
        int size = cursor.getCount();
        StringBuilder builder2 = new StringBuilder();
        while(cursor.moveToNext()){

            for(int i = 0 ;i<columnCount; i++){
                String value = cursor.getString(i);
                builder2.append(value);
                if(i<columnCount-1){
                    builder2.append("|");
                }
            }
            builder2.append("\n");
        }  
        Log.d("yanjun",builder2.toString());
        
        Log.d("yanjun", "cursor="+cursor.getCount());
//        
//        //String selectionStr = "account_type='com.pptv' and deleted!=1";  
////        String selectionStr = "account_type='Local Phone Account' or account_type is null and deleted!=1 or account_type='com.pptv'";
//        
////        Cursor ccc = getContentResolver().query(RawContacts.CONTENT_URI, SYNC_CONTACT_PROJECTION, selectionStr, null,
////                RawContacts._ID);
////        
////        Log.d("yanjun", "ccc=" + ccc.getCount());
//        
//        String selection = "address = '18511803872' AND body LIKE '【手机找回】2015-07-13 16:38的位置在北京市朝阳区利泽东街,点击链接查看手机位置http:'";
//        //Cursor cc = getContentResolver().query(Uri.parse("content://sms/sent"), null, selection, null, "date DESC");
//       
//        Uri uuri = Uri.parse("content://telephony/siminfo");
//        Cursor cc = getContentResolver().query(uuri, null, null, null, null);
//   
//        Log.d("yanjun", "Telephony.Carriers.CONTENT_URI="+ Telephony.Carriers.CONTENT_URI);
//        Log.d("yanjun", "ContentUris.withAppendedId(uuri, 1)="+ContentUris.withAppendedId(uuri, 1));
//        
//        size = cc.getCount();
//        columnCount = cc.getColumnCount();
//        Log.d("yanjun", "cc=" + cc.getCount());
//        StringBuilder builder3 = new StringBuilder();
//        while(cc.moveToNext()){
//
//            for(int i = 0 ;i<columnCount; i++){
//                String value = cc.getString(i);
//                builder3.append(value);
//                if(i<columnCount-1){
//                    builder3.append("|");
//                }
//            }
//            builder3.append("\n");
//        }
//        Log.d("yanjun", "builder3" + builder3.toString());
//        
//        String result = "";
//        final Uri CONTENT_URI = Uri.parse("content://com.pptv.assistantserver.provider/sim_info_list");
//        Cursor cursor3 = getContentResolver().query(CONTENT_URI, null, null, null, null);
//        Log.d("yanjun", "cursor3 size="+cursor3.getCount());
//        if (cursor3 != null) {
//            if (cursor3.moveToFirst()) {
//                result = cursor3.getString(0);
//            }
//            cursor3.close();  
//        }
//        Log.d("yanjun", "result" + result);
//        
//        CallListenerTest();
//        
//        String str = "【手机找回】2015-08-20 09:38的位置在北京市朝阳区利泽东街,点击链接查看手机位置http://api.map.baidu.co";
//        Log.d("yanjun", "result" + str.length());
    }
    
    public void onButtonClick(View v) {
        //356157060000930  356157060002308
        String app = "com.pptv.assistantserver.push"; 
        String id = "356157060025598";
        pushMessage(app, id);
        
//      new Thread(new Runnable() {
//      
//          @Override
//          public void run() {
//              // TODO Auto-generated method stub
//              pptvPassportTest();
//          }
//      }).start();
        
//        startActivity(new Intent(this, PreferenceWithHeaders.class));
       
//        assistantServiceTest();

//        sendBroadcast(new Intent("android.intent.action.pptv.account.LOGIN_SUCCESS"));
        
        //relateRevices();
        
        //vipActivityTest();
        
//        vipActivityQeueyTest();
        
        //assistantServiceMsgLockTest();

//        AccountManager accountManager = AccountManager.get(this);
//        Account account = accountManager.getAccountsByType("com.pptv")[0];
//        accountManager.removeAccount(account, null, null);
        
        //getReLoginToken(accountManager, account);
//        getLoginToken2(accountManager);

//        
        
//        getSingture();
        
//        relateRevices();
        
        assistantServiceTest();
    }

    public void onButtonClick2(View v) {
//        String app = "com.pptv.android.ota.push";
//        String id = "356157060025598";//356157060000906";
//        pushMessage(app, id);
        
//        relateRevices();
        
//        new Thread(new Runnable() {
//            
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                testAuth();
//            }
//        }).start();
        
        Intent intent = new Intent();
        intent.setClassName("com.pptv.assistantserver", "com.pptv.assistantserver.phonesecurity.findPhone.CheckAndUnbindActivity");
        startActivity(intent);
        
//        assistantServiceTest();  
        
        //sendMessage(this,"18701684036","【手机找回】2015-08-20 09:38的位置在北京市朝阳区利泽东街,点击链接查看手机位置http://api.map.baidu.com/marker?location=40.015273,116.493365&output=html");
        //13810651742
        //sendMessage(this,"18701684036","【手机找回】2015-08-20 09:38的位置在北京市朝阳区利泽东街,纬度=40.015273,经度=116.493365");

    } 
    
    public void onButtonClick3(View v){
        
//        new Thread(new Runnable() {
//            
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                suningPassportTest();
//            }
//        }).start();
        
//        getPhoneNumberFromSim(this);
        
//      String app = "com.pptv.mobile.sync.push"; 
//      String id = "356157060025598";
//      pushMessage(app, id);
      
//      CallListenerTest();
        
//        vipActivityQeueyTest();
//        vipActivityTest();
        
//        acceptCall();
        
        sendMessage(this,"18701684036","WLAN信号强度为三格");
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private static final String ACTION_RECEIVE_SEND_RESULT = "com.sn.phonesecmtk.tools.send_result";
    
    public static boolean sendMessage(Context context, final String phoneNumber, final String msg) {
        Log.d("yanjun", "Send sms content:" + msg);
        if (TextUtils.isEmpty(msg) || TextUtils.isEmpty(phoneNumber)) {
            Log.d("yanjun", "ModifyPreference    " + "send message number or message is null");
            return false;
        }

        SmsManager smsManager = SmsManager.getDefault();
        final List<String> smsDivs = smsManager.divideMessage(msg);

        final ArrayList<PendingIntent> mPendingIntentList = getPendingIntentList(smsDivs.size(),context);
        
        IntentFilter filter = new IntentFilter(ACTION_RECEIVE_SEND_RESULT);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                for (PendingIntent pendingIntent : mPendingIntentList) {
                    pendingIntent.cancel();
                }
                context.unregisterReceiver(this);
                Log.d("yanjun", "Send over=" + getResultCode());
            }
        }, filter);

        if (smsDivs.size() > 1) {
            Log.d("yanjun", "Send sms more time");
            smsManager.sendMultipartTextMessage(phoneNumber, null, (ArrayList<String>) smsDivs, null, null);
        } else {
            smsManager.sendMultipartTextMessage(phoneNumber, null, (ArrayList<String>) smsDivs,
                    mPendingIntentList, null);
        }

        Log.d("yanjun", "Send sms count:" + smsDivs.size() + " msg length:" + msg.length());

        return true;
    }
    
    private static ArrayList<PendingIntent> getPendingIntentList(int num, Context context) {
        ArrayList<PendingIntent> list = new ArrayList<PendingIntent>();
        for (int i = 0; i < num; i++) {
            list.add(PendingIntent.getBroadcast(context, 0, new Intent(ACTION_RECEIVE_SEND_RESULT),
                    PendingIntent.FLAG_UPDATE_CURRENT));
        }
        return list;
    }
    
    private MyPhoneStateListener[] mPhoneStateListeners;
    
    private void CallListenerTest(){
//        mPhoneStateListeners = new MyPhoneStateListener[2];
//        int events = PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_SERVICE_STATE;
//        for(int i = 0; i < mPhoneStateListeners.length; i++){
//            mPhoneStateListeners[i] = new MyPhoneStateListener();
//            MainActivity.listen(this, mPhoneStateListeners[i], events, i);
//            
//            Log.d("yanjun", "listen id="+i);
//        }
        
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener mListenerCall = new MyPhoneStateListener();
        mTelephonyManager.listen(mListenerCall, PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_SERVICE_STATE);
    }
    
    public static void listen(Context context, PhoneStateListener listener, int events, int slot) {
        MSimDeviceBase device = MSimDeviceManager.getMSimDevice(context);

        if (device != null) {
            device.listen(context, listener, events, slot);
            Log.d("yanjun", "listener ok");
        } else {
            Log.d("yanjun", "device not found, listen failed!");
        }
    }
    
    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // TODO Auto-generated method stub
            super.onCallStateChanged(state, incomingNumber);
            
            Log.d("yanjun", "state = "+ state + " incomingNumber="+incomingNumber);
        }
    }
    
    private void acceptCall(){
//        Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
//        meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
//        sendOrderedBroadcast(meidaButtonIntent, null);
        
        new Thread(new Runnable() {
            @Override
          public void run() {
              try {
                  Runtime.getRuntime().exec( "input keyevent " + KeyEvent.KEYCODE_HEADSETHOOK );
              }
              catch (Throwable t) {
                  // do something proper here.
              }
          }
      }).start();
        
        Log.d("yanjun", "acceptCall...");
    }
    
    private void accetpCall2(){
        Intent headsetPlugIntent = new Intent(Intent.ACTION_HEADSET_PLUG);
        headsetPlugIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        headsetPlugIntent.putExtra("state", 1);
        headsetPlugIntent.putExtra("microphone", 1);
        headsetPlugIntent.putExtra("name", "Headset");
        sendOrderedBroadcast(headsetPlugIntent, "android.permission.CALL_PRIVILEGED");

        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        KeyEvent keyEventDown = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
        mediaButtonIntent.putExtra("android.intent.extra.KEY_EVENT", keyEventDown);
        sendOrderedBroadcast(mediaButtonIntent, "android.permission.CALL_PRIVILEGED");

        KeyEvent keyEventUp = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
        mediaButtonIntent.putExtra("android.intent.extra.KEY_EVENT", keyEventUp);
        sendOrderedBroadcast(mediaButtonIntent, "android.permission.CALL_PRIVILEGED");

        headsetPlugIntent.putExtra("state", 0);
        sendOrderedBroadcast(headsetPlugIntent, "android.permission.CALL_PRIVILEGED");
        Log.d("yanjun", "acceptCall...2");
    }

    private void getSingture(){
        
        StringBuilder sb = new StringBuilder();
        
        final PackageManager packageManager = getPackageManager();
        final List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES);

        PackageInfo p = null;
        try { //com.yulore.framework
            p = packageManager.getPackageInfo("com.yulore.framework", PackageManager.GET_SIGNATURES);
        } catch (NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        final String strName = p.applicationInfo.loadLabel(packageManager).toString();
        final String strVendor = p.packageName;

        sb.append("<br>" + strName + " / " + strVendor + "<br>");

        final Signature[] arrSignatures = p.signatures;
        for (final Signature sig : arrSignatures) {
            /*
            * Get the X.509 certificate.
            
            */
            //Log.d("yanjun", "hashCode = "+ sig.hashCode());
            //Log.d("yanjun", "sign = "+ sig.toCharsString());
            
            final byte[] rawCert = sig.toByteArray();
            InputStream certStream = new ByteArrayInputStream(rawCert);

            final CertificateFactory certFactory;
            final X509Certificate x509Cert;
            try {
                certFactory = CertificateFactory.getInstance("X509");
                x509Cert = (X509Certificate) certFactory.generateCertificate(certStream);

                sb.append("Certificate subject: " + x509Cert.getSubjectDN() + "<br>");
                sb.append("Certificate issuer: " + x509Cert.getIssuerDN() + "<br>");
                sb.append("Certificate serial number: " + x509Cert.getSerialNumber() + "<br>");
                sb.append("<br>");
            }
            catch (CertificateException e) {
                e.printStackTrace();
            }
        }
        
        Log.d("yanjun", "Certificate = "+ sb.toString());
        
        String fingerprintMD5 = null;
        for(Signature sig : arrSignatures)
        {
            Log.d("yanjun", "fingerprintMD5 = "+ fingerprintMD5);
            byte[] hexBytes = sig.toByteArray();
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            byte[] md5digest = new byte[0];
            if(digest != null)
            {
                md5digest = digest.digest(hexBytes);
                StringBuilder sbb = new StringBuilder();
                for(int i = 0; i < md5digest.length; ++i)
                {
                    sbb.append((Integer.toHexString((md5digest[i] & 0xFF) | 0x100)).substring(1, 3));
                }
                fingerprintMD5 = sbb.toString();
            }
        }
        Log.d("yanjun", "fingerprintMD5 = "+ fingerprintMD5);
    }
    
    private void getReLoginToken(final AccountManager accountManager, final Account account) {
        
        //final AccountManagerFuture<Bundle> future3 = accountManager.getAuthToken(account, "PPTV_TOKEN_ACCESS", null, this, null, null);
        final AccountManagerFuture<Bundle> future3 = accountManager.getAuthToken(account, "PPTV_TOKEN_ACCESS", null, false, null, null);

        new Thread(new Runnable() {

            @Override
            public void run() {

                Log.e("pptv", "getReLoginToken");

                Bundle bnd = null;
                try {
                    bnd = future3.getResult();
                    Log.e("pptv", "getResult");
                    // 请注意此次取token的key
                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                    // 这个取token为null
                    Log.d("pptv", "3 token type PPTV_TOKEN_ACCESS =" + bnd.getString("PPTV_TOKEN_ACCESS"));
                    Log.d("pptv", "3 token type KEY_AUTHTOKEN =" + authtoken);
                    Log.d("pptv", "3 token type errorCode =" + bnd.getString("errorCode_relogin"));

                    /*
                     * 发现token过期，使用api invalidateAuthToken会删除相应账号的token，并在下次调用
                     * getAuthTokenByFeatures时会调用pptv账号管理里的getAuthToken接口，当然如果不
                     * 使用invalidateAuthToken就不会调用getAuthToken接口
                     */
                } catch (Exception e) {
                    Log.d("pptv", "3 e=" + e.toString());
                }
            }
        }).start();
    }
    
    private void getLoginToken2(final AccountManager accountManager){
        new Thread(new Runnable() { 
            @Override 
            public void run() { 
                // TODO Auto-generated method stub 
                // 取当前已登录账号的token,token的类型为PPTV_TOKEN_CURRENT_ACCESS 
                final AccountManagerFuture<Bundle> future1 = accountManager 
                        .getAuthTokenByFeatures("com.pptv", 
                                "PPTV_TOKEN_ACCESS", null, MainActivity.this, null, 
                                null, new AccountManagerCallback<Bundle>() { 
                                    @Override 
                                    public void run( 
                                            AccountManagerFuture<Bundle> future) { 
                                        Bundle bnd = null; 
                                        try { 
                                            bnd = future.getResult(); 
                                            // 请注意此次取token的key 
                                            final String authtoken = bnd 
                                                    .getString(AccountManager.KEY_AUTHTOKEN); 
                                            // 这个取token为null 
                                            Log.d("pptv", "1 token type PPTV_TOKEN_ACCESS =" 
                                                    + bnd.getString("PPTV_TOKEN_ACCESS")); 
                                            Log.d("pptv", "1 token type KEY_AUTHTOKEN =" 
                                                    + authtoken); 
                                            Log.d("pptv", "1 token type errorCode =" 
                                                    + bnd.getString("errorCode_relogin")); 
                                            /* 
                                             * 发现token过期，使用api 
                                             * invalidateAuthToken会删除相应账号的token 
                                             * ，并在下次调用 
                                             * getAuthTokenByFeatures时会调用pptv账号管理里的getAuthToken接口 
                                             * ，当然如果不 
                                             * 使用invalidateAuthToken就不会调用getAuthToken接口 
                                             */ 
                                            // accountManager.invalidateAuthToken(account.type, 
                                            // authtoken); 
                                        } catch (Exception e) { 
                                            Log.d("pptv", "1 e=" + e.toString()); 
                                            // showMessage(e.getMessage()); 
                                        } 
                                    } 
                                }, null); 
            } 
        }).start(); 

    }
    
    public void vipActivityTest(){
        
       final String url = "https://172.19.33.236/phone-activate-vip/vip/activateVip";
       //final String url = "https://test.yuanjian.pplive.cn/phone-activate-vip/vip/activateVip";
       
       String username = "ppossever";
       String usernameEncoce = "";
       String agentkey = "URJDNDE#$ASDFALJALKPI";
       String deviceid = "356157060000930";
       String sign = "";
       
       try {
           sign = Md5.MD5(URLEncoder.encode(username+"&"+deviceid+"&"+agentkey,"utf-8"), 32);
           usernameEncoce = URLEncoder.encode(username,"utf-8");
       } catch (UnsupportedEncodingException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       
       final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
       nvps.add(new BasicNameValuePair("username", usernameEncoce));
       nvps.add(new BasicNameValuePair("deviceid", deviceid));
       nvps.add(new BasicNameValuePair("sign", sign));
       
       new Thread(new Runnable() {
           
           @Override
           public void run() {
               // TODO Auto-generated method stub
               httpPost(url, nvps);
           }
       }).start();
    }
    
    public void vipActivityQeueyTest(){
        //final String url = "https://172.19.33.236/phone-activate-vip/vip/activateVipStatus";
        final String url = "https://test.yuanjian.pplive.cn/phone-activate-vip/vip/activateVipStatus";
        //356157060000930
        String agentkey = "URJDNDE#$ASDFALJALKPI";
        String deviceid = "356157060803010";
        String sign = "";
        
        try {
            sign = Md5.MD5(URLEncoder.encode(deviceid+"&"+agentkey,"utf-8"), 32);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new HttpValuePair("deviceid", deviceid, HTTP.UTF_8));
        nvps.add(new HttpValuePair("sign", sign, HTTP.UTF_8));
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                httpPost(url, nvps);
            }
        }).start();
     }

    public static String getPhoneNumberFromSim(Context context) {
        String number = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        number = tm.getLine1Number();
        
        Log.d("yanjun", "get number from simCard" + " number:" + number);
        
        return number;
    }
    
    public static int opId = 1200;
    private void assistantServiceTest(){
        
        //{"appIden":"com.pptv.assistantserver.push","opId":921,"opInf":6,"data":{"msg":"这是我丢失的手机，如若捡到请与我联系，谢谢！","uNum":"18701684036","lPwd":"91AE2B277E1D52E3"}
        
        PushData dd = new PushData();
        dd.setlPwd("91AE2B277E1D52E3");
        dd.setMsg("A hahahahaha");
        dd.setuNum("18701684036");
        
        Push pp = new Push();
        pp.setAppIden("com.pptv.assistantserver.push");
        pp.setData(dd);
        pp.setOpId((opId++)+"");
        pp.setOpInf(6);
        
        Gson gson = new Gson();
        String data = gson.toJson(pp);
        
        Intent service = new Intent("com.pptv.assistantserver.AssistantService");
        service.setPackage("com.pptv.assistantserver");
        service.putExtra("message", data);
        startService(service);
    }
    
    //{"opInf":"6","opId":"","appIden":""}
    private void assistantServiceMsgLockTest(){
 
        PushData dd = new PushData();
        dd.setlPwd("91AE2B277E1D52E3");
        dd.setMsg("A hahahahaha");
        dd.setuNum("18701684036");
        
        Push pp = new Push();
        pp.setOpId("");
        pp.setOpInf(6);
        
        Gson gson = new Gson();
        String data = gson.toJson(pp);
        
        Intent service = new Intent("com.pptv.assistantserver.AssistantService");
        service.setPackage("com.pptv.assistantserver");
        service.putExtra("message", data);
        startService(service);
    }
    
    private void relateRevices(){
        //http://10.11.31.57/fmp
        //358239058541758  Nenux
        //356157060519012  M1
        // 356157060002035  M1
        // 356157060000930
        // 356157060000906

        
        final String url = "http://test.yuanjian.pplive.cn/find-my-phone/v2/safePassport/relateDevice/ppospp/356157060002308/M1/1.0/empty/empty/ppossever/empty.htm";
        //final String url = "http://10.11.31.57/fmp/v2/safePassport/relateDevice/ppossever/358239058541758/Nenux/5.0/empty/empty/haha/empty.htm";
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                httpPost(url, "hehe");  
            }
        }).start();
    }
    
    private void pushMessage(String app, String id){

        String begintime = System.currentTimeMillis()/1000 + "";
        String endtime = (System.currentTimeMillis()/1000 + 100)+"";
        //http://172.16.205.242:10080
        final String urlTag = "http://push.ppmessager.pptv.com/push?op=create&type=tag&tag=[ALL]&app=com.test.test&key=123456&begintime="+begintime+"&endtime="+endtime;
        final String urlId = "http://push.ppmessager.pptv.com/push?op=create&type=clientid&app="+app+"&key=123456&nomore=true&id="+id+"&begintime="+begintime+"&endtime="+endtime;
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                httpPost(urlId, "hehee");
            }
        }).start();
    }
    
    private String Token = "";
    private void pptvPassportTest(){
        
        StringBuilder url = new StringBuilder();
        url.append("HTTPS://api.passport.pptv.com/v3/login/login.do");
        url.append("?");
        
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new HttpValuePair("username", "ppossever", HTTP.UTF_8));
        nvps.add(new HttpValuePair("password", "ppossever11", HTTP.UTF_8));
        nvps.add(new HttpValuePair("format", "json", HTTP.UTF_8));
        
        url.append(URLEncodedUtils.format(nvps, HTTP.UTF_8));
        
        HttpClient mHttpClient = getNewHttpClient();

        try {
            HttpPost request = new HttpPost(url.toString());
            HttpResponse response = mHttpClient.execute(request);

            
            String responseResult = EntityUtils.toString(response.getEntity(), "utf-8");
            //responseResult = URLDecoder.decode(responseResult, "utf-8");
            Log.d("yanjun", "Response:" + responseResult.trim());
            
            JSONObject responJson = new JSONObject(responseResult);
            String errorCodeKey = "errorCode";

            if (responJson.has(errorCodeKey) && (responJson.getInt(errorCodeKey) == 0)) {
                JSONObject resultObj = responJson.getJSONObject("result");
                Token = resultObj.getString("token");
            }
            
        }  catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void suningPassportTest(){
        
        StringBuilder url = new StringBuilder();
        url.append("https://passport.suning.com/ids/login?jsonViewType=true");
        url.append("&username=13811572454");
        url.append("&password=pptv123456");
        url.append("&service=http://cloud.suning.com/cloud-api/auth");
        url.append("?targetUrl=http://cloud.suning.com/cloud-api/v2/login/logon/K68w/1.0.0/4.4.2/13811572454/32");
        
        
        HttpClient mHttpClient = getNewHttpClient();

        Log.d("yanjun","url="+url.toString());
        
        try {
            HttpPost request = new HttpPost(new URI(url.toString()));
            HttpResponse response = mHttpClient.execute(request);
            showResponseResult(response);
            
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private DefaultHttpClient getNewHttpClient() {
        try {

            HttpParams params = new BasicHttpParams();
            
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpConnectionParams.setSocketBufferSize(params, 8192);
            HttpClientParams.setCookiePolicy(params,CookiePolicy.BROWSER_COMPATIBILITY);
            HttpClientParams.setRedirecting(params, true);

            String userAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) " +
                "AppleWebKit/528.18 (KHTML, like Gecko) " +
                    "Version/4.0 Mobile/7A341 Safari/528.16";
            HttpProtocolParams.setUserAgent(params, userAgent);

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
            
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
    
    public static class SSLSocketFactoryEx extends SSLSocketFactory {

        SSLContext sslContext = SSLContext.getInstance("TLS");

        public SSLSocketFactoryEx(KeyStore truststore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port,
                boolean autoClose) throws IOException, UnknownHostException {
            
            return sslContext.getSocketFactory().createSocket(socket, host, port,
                    autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }
    
    class HttpValuePair implements NameValuePair{

        private String name;
        private String value;
        
        public HttpValuePair(String name, String value, String encode) {
            // TODO Auto-generated constructor stub
            try {
                this.name = new String(name.getBytes(), encode);
                this.value = new String(value.getBytes(), encode);
            
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        @Override
        public String getName() {
            // TODO Auto-generated method stub
            return name;
        }

        @Override
        public String getValue() {
            // TODO Auto-generated method stub
            return value;
        } 
    }
   
   
    private void httpGet(String url){
        
        // 生成请求对象
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();

        // 发送请求
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            showResponseResult(response);// 一个私有方法，将响应结果显示出来

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void httpPost(String url, String entity){

        try {
            HttpEntity requestHttpEntity;
            requestHttpEntity = new StringEntity(entity);
            httpPost(url, requestHttpEntity);
            
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }           
    
    private void httpPost(String url, List<NameValuePair> pairList){

        try {
            
//            NameValuePair pair1 = new BasicNameValuePair("username", "hehe");
//            NameValuePair pair2 = new BasicNameValuePair("age", 90+"");
//
//            List<NameValuePair> pairList = new ArrayList<NameValuePair>();
//            pairList.add(pair1);
//            pairList.add(pair2);
            
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList);
            httpPost(url, requestHttpEntity);
            
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
    
    private void httpPost(String url, HttpEntity entity){

        try
        {
            Log.d("yanjun", "request:" + url);
            // URL使用基本URL即可，其中不需要加参数
            HttpPost httpPost = new HttpPost(url);
            // 将请求体内容加入请求中
            //httpPost.setEntity(entity);
            // 需要客户端对象来发送请求
            HttpClient httpClient = getNewHttpClient();
            
            HttpParams httpParams = httpPost.getParams();
            
            Log.d("yanjun", "连接超时:"+HttpConnectionParams.getConnectionTimeout(httpParams)+" ms");
            Log.d("yanjun", "请求超时 :"+HttpConnectionParams.getConnectionTimeout(httpParams)+" ms");
            
            HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
            HttpConnectionParams.setSoTimeout(httpParams, 10000);
            
            //httpPost.setParams(httpParams);
            httpParams = httpPost.getParams();
            
            Log.d("yanjun", "连接超时222:"+HttpConnectionParams.getConnectionTimeout(httpParams)+" ms");
            Log.d("yanjun", "请求超时222 :"+HttpConnectionParams.getConnectionTimeout(httpParams)+" ms");
            
            String token = "xr4c0Ib_7vymtLS8IxxRzIAgJfMyZ6PTMqRkrE-43nkI_r8RZ9gIGw4TCAFCZF9ZneFyxOuKkTL4%0D%0AOHp3oUrkLFxXfxYFiicDmPOghH8XfLw9Hxrs_O1i09atURfxZSAn2q31Ks6m-onDLPlEaW_ENzil%0D%0ALEpmNuWAOkRfDBWyl94%0D%0A";
            httpPost.addHeader("token", token);
            httpPost.addHeader("userName", "yanjun");
            
            // 发送请求
            HttpResponse response = httpClient.execute(httpPost);
            // 显示响应
            showResponseResult(response);
        }
        catch (Exception e)
        {
            Log.d("yanjun", "exception " + e);
            e.printStackTrace();
        }
    }
    
    private void showResponseResult(HttpResponse response)
    {
        Log.d("yanjun", "Response:" + response);
        
        if (null == response)
        {
            return;
        }

        HttpEntity httpEntity = response.getEntity();
        try
        {
            InputStream inputStream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            String result = "";
            String line = "";
            while (null != (line = reader.readLine()))
            {
                result += line;
            }

            Log.d("yanjun", "Net Ret:" + result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    

    class Push{
        private String appIden;
        private String opId;
        private int opInf;
        private PushData data;
        public String getAppIden() {
            return appIden;
        }
        public void setAppIden(String appIden) {
            this.appIden = appIden;
        }
        public String getOpId() {
            return opId;
        }
        public void setOpId(String opId) {
            this.opId = opId;
        }
        public int getOpInf() {
            return opInf;
        }
        public void setOpInf(int opInf) {
            this.opInf = opInf;
        }
        public PushData getData() {
            return data;
        }
        public void setData(PushData data) {
            this.data = data;
        }
    }
    
    class PushData{
        private String msg;
        private String uNum;
        private String lPwd;
        
        public String getMsg() {
            return msg;
        }
        public void setMsg(String msg) {
            this.msg = msg;
        }
        public String getuNum() {
            return uNum;
        }
        public void setuNum(String uNum) {
            this.uNum = uNum;
        }
        public String getlPwd() {
            return lPwd;
        }
        public void setlPwd(String lPwd) {
            this.lPwd = lPwd;
        }
        
    }
}
