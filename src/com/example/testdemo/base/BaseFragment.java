package com.example.testdemo.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.testdemo.widget.LoadingDialog;

public class BaseFragment extends Fragment {

	private boolean isDestroyView = true;
	
	private RequestQueue mQueue;
	
	private Dialog mLoadingDialog;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mQueue = Volley.newRequestQueue(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		mLoadingDialog = new LoadingDialog(getActivity());
		
		isDestroyView = false;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
	    super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
	    super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		
		isDestroyView = true;

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		mLoadingDialog = null;
		
		mQueue.cancelAll();
		mQueue = null;
	}

	public boolean isViewDestroyed(){
		return isDestroyView;
	}
	
	public RequestQueue getRequestQueue(){
		return mQueue;
	}
	
	public void showLoadingDialog(){
		mLoadingDialog.show();
	}
	
	public void hideLoadingDialog(){
		mLoadingDialog.dismiss();
	}
}
