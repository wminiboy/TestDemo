package com.example.testdemo;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class LruImageCache implements ImageCache{

	private static LruCache<String, Bitmap> mMemoryCache;
	
	private static LruImageCache lruImageCache;
	
	private LruImageCache(){
		
		// Get the Max available memory
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 6;
	
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
			
			@Override
			protected int sizeOf(String key, Bitmap bitmap){
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}
	
	public static LruImageCache instance(){
		if(lruImageCache == null){
			lruImageCache = new LruImageCache();
		}
		return lruImageCache;
	}
	
	@Override
	public Bitmap getBitmap(String arg0) {		
		return mMemoryCache.get(arg0);	
	}

	@Override
	public void putBitmap(String arg0, Bitmap arg1) {
		if(getBitmap(arg0) == null){
			mMemoryCache.put(arg0, arg1);
		}		
	}
	
	public void printKey(){
		Map<String, Bitmap> map = mMemoryCache.snapshot();
		
		Set<String> set = map.keySet();
		
		Iterator<String> it = set.iterator();
		
		while(it.hasNext()){
			Log.d("LruImageCache", "key="+it.next());
		}
	}
	
	public void removeBitmap(String key){
		mMemoryCache.remove(key);
	}
	
	public void cleanChche(){
		mMemoryCache.trimToSize(-1);
	}
	
	
}
