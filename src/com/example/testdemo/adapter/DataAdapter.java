package com.example.testdemo.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.testdemo.base.BaseViewGroup;

public class DataAdapter<T> extends BaseAdapter {

	protected List<T> mList;
	protected Context mContext;
	private Class<? extends BaseViewGroup<T>> mClass;

	public DataAdapter(Context context,
			Class<? extends BaseViewGroup<T>> type,
			List<T> list) {
		
		mContext = context;
		mList = list;
		mClass = type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if (convertView == null) {
			
			try {

				Constructor<? extends View> constructor = mClass.getConstructor(Context.class);

				convertView  = constructor.newInstance(mContext);

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		BaseViewGroup<T> view = (BaseViewGroup<T>) convertView;

		if(view == null){
			return null;
		}

		view.setParam(mList.get(position));
		view.setTag(position);
		view.updateView();
		
		return view;
	}
}
