package com.example.testdemo.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.testdemo.widget.LoadingDialog;

public class BaseActvity extends FragmentActivity {

	private RequestQueue mQueue;
	
	private boolean isActivityDestroyFlag = true;
	
	private Dialog mLoadingDialog;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	
		mLoadingDialog = new LoadingDialog(this);
		
		isActivityDestroyFlag = false;
		mQueue = Volley.newRequestQueue(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		mQueue.cancelAll();
		mQueue = null;
		
		isActivityDestroyFlag = true;
	}
	
	public RequestQueue getRequestQueue(){
		return mQueue;
	}
	
	public boolean isActivityDestroy(){
		return isActivityDestroyFlag;
	}
	
	public void showLoadingDialog(){
		mLoadingDialog.show();
	}
	
	public void hideLoadingDialog(){
		mLoadingDialog.dismiss();
	}
}
