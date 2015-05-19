package com.example.testdemo.net;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;

public abstract class GetData<T> {

	protected GetDataListener<T> mListener;

	public void setOnGetDataListener(GetDataListener<T> listener){
		mListener = listener;
	}
	
	public Request<T> get(RequestQueue queue, String url, Class<T> clazz){
		return doNet(Method.GET, queue, url, null, clazz);
	}
	
	public Request<T> post(RequestQueue queue, String url, Map<String, String> param, Class<T> clazz){
		return doNet(Method.POST, queue, url, param, clazz);
	}
	
	protected abstract Request<T> doNet(int mode, RequestQueue queue, String url, Map<String, String> param,  Class<T> clazz);

	
	public static Map<String ,String> getPostParam(String key ,String value){
		
		Map<String ,String> map = new HashMap<String, String>();
		map.put(key, value);
		return map;
	}
}
