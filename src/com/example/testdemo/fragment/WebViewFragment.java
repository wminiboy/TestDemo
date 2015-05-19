package com.example.testdemo.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.testdemo.R;
import com.example.testdemo.base.BaseFragment;

public class WebViewFragment extends BaseFragment{

	private WebView mWebView;
	
	private String mUrl = "";

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		mUrl = getArguments().getString("url");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View v = inflater.inflate(R.layout.fragment_webview, null);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		showLoadingDialog();
		
		View view = getView();

		mWebView = (WebView) view.findViewById(R.id.id_webview_view);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				
				showLoadingDialog();
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				
				if(isViewDestroyed()){
					return;
				}
				
				hideLoadingDialog();
				
				Toast.makeText(getActivity(), R.string.net_error, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				
				if(isViewDestroyed()){
					return;
				}
				
				hideLoadingDialog();
			}
		});


		mWebView.loadUrl(mUrl);
	}

}
