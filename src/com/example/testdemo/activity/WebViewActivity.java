package com.example.testdemo.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.testdemo.base.BaseActvity;
import com.example.testdemo.fragment.WebViewFragment;

public class WebViewActivity extends BaseActvity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		if(arg0 == null){
			FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
			Fragment frag = new WebViewFragment();
			Bundle bundle = new Bundle();
			
			bundle.putString("url", getIntent().getStringExtra("url"));
			frag.setArguments(bundle);
			tr.add(android.R.id.content, frag);
			tr.commit();
		}
		
		initActionBar();
	}
	
	private void initActionBar(){
		ActionBar bar = getActionBar();

		bar.setDisplayHomeAsUpEnabled(true);
		
		bar.setDisplayOptions(0);
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_HOME_AS_UP|ActionBar.DISPLAY_SHOW_HOME, 
				 ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_HOME_AS_UP);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
