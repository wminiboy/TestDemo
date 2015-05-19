package com.example.testdemo.net;

public interface GetDataListener<T> {
	
	public void onGetStarted();
	public void onGetCompleted(GetDataResult result, T data);
}
