package com.example.testdemo.net;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

public class GsonRequest<T> extends Request<T> {

	private final Listener<T> mListener;

	private Gson mGson;

	private Class<T> mClass;

	private Map<String, String> mParamsMap;

	public GsonRequest(int method, String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		
		mGson = new Gson();
		mClass = clazz;
		mListener = listener;
		mParamsMap = new HashMap<String, String>();
	}
	
	public GsonRequest(String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		this(Method.GET, url, clazz, listener, errorListener);
	}

	public void addParam(String key, String value) {
		mParamsMap.put(key, value);
	}
	
	public void addParams(Map<String, String> map) {
		mParamsMap = map;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return mParamsMap;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			
			String data = new String(jsonString.getBytes(HttpHeaderParser
					.parseCharset(response.headers)), "UTF-8");

			return Response.success(mGson.fromJson(data, mClass),
					HttpHeaderParser.parseCacheHeaders(response));
			
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		if(mListener != null){
			mListener.onResponse(response);	
		}
	}

	/**
	 * Default use URL for key , but if the method is post , all of the URL is same,
	 * So need to override this method , and add post parameter in key.
	 */
	@Override
	public String getCacheKey() {
		// TODO Auto-generated method stub
		String key = super.getCacheKey() + mParamsMap.toString();
		return key;
	}
}
