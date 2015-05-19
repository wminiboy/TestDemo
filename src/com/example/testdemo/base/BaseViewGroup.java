package com.example.testdemo.base;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class BaseViewGroup<T> extends FrameLayout implements InterfaceParam<T>{
    
	protected T mParams;
	protected Object objec;

	/**
	 * notice : Sub class must have this constructor at lest, to let adapter reflect it's self.   
	 * @param context
	 */
	public BaseViewGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public BaseViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setParam(T param) {
		// TODO Auto-generated method stub
		if(mParams != param){
			mParams = param;
		}
	}

	@Override
	public T getParam() {
		// TODO Auto-generated method stub
		return mParams;
	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		updateView();
		super.onAttachedToWindow();
	}
}
