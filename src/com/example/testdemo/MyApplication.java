package com.example.testdemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {
	
	private RequestQueue mQueue;
	private LruImageCache mLruImageCache;
	private ImageLoader mImageLoader;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		mQueue = Volley.newRequestQueue(this);
		mLruImageCache = LruImageCache.instance();
		mImageLoader = new ImageLoader(mQueue, LruImageCache.instance());
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	} 

	public void cleanCaech() {
		mLruImageCache.cleanChche();
	}
}
