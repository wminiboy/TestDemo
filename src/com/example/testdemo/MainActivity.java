
package com.example.testdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.testdemo.base.BaseActvity;
import com.example.testdemo.net.GetDataListener;
import com.example.testdemo.net.GetDataResult;
import com.example.testdemo.net.GetJsonData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        
    }

    public void onButtonClick(View v) {
        Intent intent = new Intent("haha.hehe");
        //intent.setPackage("com.example.testdemo");
        startService(intent);
    }

    public void onButtonClick2(View v) {

        pushMessage();
    } 
    
    private void relateRevices(){
        RequestQueue queue = Volley.newRequestQueue(this);
        
        GetJsonData<String> gd = new GetJsonData<String>();
        gd.setOnGetDataListener(new GetDataListener<String>() {
            
            @Override
            public void onGetStarted() {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetCompleted(GetDataResult result, String data) {
                // TODO Auto-generated method stub
                Log.d("yanjun","success..."+result.toString() +" data="+data);
            }
        });
        
        //http://10.11.31.57/fmp
        //358239058541758  Nenux
        //356157060519012  M1
        
        String url = "http://10.11.31.57/fmp/v2/safePassport/relateDevice/ppossever/356157060519012/M1/1.0/empty/empty/ppossever/empty.htm";
        //String url = "http://10.11.31.57/fmp/v2/safePassport/relateDevice/pptv_yanjunwang/358239058541758/Nenux/5.0/empty/empty/pptv_yanjunwang/empty.htm";
        
        Map<String, String> param = new HashMap<String, String>();
        
        gd.post(queue, url, param, String.class);
    }
    
    private void pushMessage(){
       
        RequestQueue queue = Volley.newRequestQueue(this);
        
        GetJsonData<String> gd = new GetJsonData<String>();
        gd.setOnGetDataListener(new GetDataListener<String>() {
            
            @Override
            public void onGetStarted() {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetCompleted(GetDataResult result, String data) {
                // TODO Auto-generated method stub
                Log.d("yanjun","success..."+result.toString() +" data="+data);
            }
        });
       
        
        String begintime = System.currentTimeMillis()/1000 + "";
        String endtime = (System.currentTimeMillis()/1000 + 100)+"";
        
        //String url = "http://172.16.205.242:10080/push?op=create&type=tag&tag=[ALL]&app=com.test.test&key=123456&begintime="+begintime+"&endtime="+endtime;
        final String url = "http://172.16.205.242:10080/push?op=create&type=clientid&app=com.test.test&key=123456&nomore=true&id=cli99&begintime="+begintime+"&endtime="+endtime;
        
        Map<String, String> param = new HashMap<String, String>();
        param.put("key", "value");
        
        //gd.post(queue, url, param, String.class);
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                httpPost(url);
            }
        }).start();
 
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
    
    private void httpPost(String url){

        NameValuePair pair1 = new BasicNameValuePair("username", "hehe");
        NameValuePair pair2 = new BasicNameValuePair("age", 90+"");

        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        pairList.add(pair1);
        pairList.add(pair2);

        try
        {
            //HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList);
            
            HttpEntity requestHttpEntity = new StringEntity("hhehe");
            
            // URL使用基本URL即可，其中不需要加参数
            HttpPost httpPost = new HttpPost(url);
            // 将请求体内容加入请求中
            httpPost.setEntity(requestHttpEntity);
            // 需要客户端对象来发送请求
            HttpClient httpClient = new DefaultHttpClient();
            // 发送请求
            HttpResponse response = httpClient.execute(httpPost);
            // 显示响应
            showResponseResult(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void showResponseResult(HttpResponse response)
    {
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
}
