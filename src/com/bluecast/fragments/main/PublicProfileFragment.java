package com.bluecast.fragments.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bluecast.bluecast.R;

public class PublicProfileFragment extends Fragment {
		WebView webView; 
//	public BeaconBusinessScanFragment() { 
//		this(R.color.white);
//	}
	
//	public BeaconBusinessScanFragment(int colorRes) {
//		mColorRes = colorRes;
//		setRetainInstance(true);
//	}
		String URL; 
		public void loadURL(String URL){
			this.URL = URL; 
		}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		if (savedInstanceState != null)
//			mColorRes = savedInstanceState.getInt("mColorRes");
//		int color = getResources().getColor(mColorRes);
//		// construct the RelativeLayout
//		RelativeLayout v = new RelativeLayout(getActivity());
//		v.setBackgroundColor(color);		
		return inflater.inflate(R.layout.publicprofile, null);
	}
	
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			willSetupWebView();
	}
	
	public void willSetupWebView() {
		webView = (WebView) getActivity().findViewById(
				R.id.webview_public_profile);
		webView.getSettings().setJavaScriptEnabled(true);
//		webView.addJavascriptInterface(new JavaScriptInterface(), "HtmlViewer");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {

			}
		});

		webView.loadUrl(URL);
	}
	
	//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt("mColorRes", mColorRes);
//	}
	
}
