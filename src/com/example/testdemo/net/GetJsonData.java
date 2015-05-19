package com.example.testdemo.net;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class GetJsonData<T> {

	private GetDataListener<T> mListener;

	public void setOnGetDataListener(GetDataListener<T> listener){
		mListener = listener;
	}
	
	public Request<T> get(RequestQueue queue, String url, Class<T> clazz){
		return doNet(Method.GET, queue, url, null, clazz);
	}
	
	public Request<T> post(RequestQueue queue, String url, Map<String, String> param, Class<T> clazz){
		return doNet(Method.POST, queue, url, param, clazz);
	}
	
	private Request<T> doNet(int mode, RequestQueue queue, String url, Map<String, String> param,  Class<T> clazz){

		Class<T> mClass = clazz;
		
		GsonRequest<T> gsonRequest = new GsonRequest<T>(mode, url , mClass,
				new Response.Listener<T>() {
				
					@Override
					public void onResponse(T data) {
						
						if(mListener != null){
							if(data != null){
								mListener.onGetCompleted(new GetDataResult(GetDataResult.SUCCESS), data);
							}else{
								mListener.onGetCompleted(new GetDataResult(GetDataResult.FAILURE, "Gson Parse Error!"), null);
							}
							
							mListener = null;
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if(mListener != null){
							mListener.onGetCompleted(new GetDataResult(GetDataResult.FAILURE, error.getMessage()), null);
							mListener = null;
						}
					}
				});
		
		if(mode == Method.POST){
			gsonRequest.addParams(param);
			Log.d("YanJunFramework","Req Post:"+ url + param.toString());
		}else{
			Log.d("YanJunFramework","Req Get:"+ url);
		}
	
		if(mListener != null){
			mListener.onGetStarted();
		}
		
		if(queue != null){
			queue.add(gsonRequest);
			return gsonRequest;
		}
		
		return null;
	}
	

	public static Map<String ,String> getPostParam(String key ,String value){
		
		Map<String ,String> map = new HashMap<String, String>();
		map.put(key, value);
		return map;
	}
}
