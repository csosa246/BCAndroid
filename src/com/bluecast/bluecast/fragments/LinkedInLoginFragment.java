package com.bluecast.bluecast.fragments;

import com.bluecast.bluecast.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LinkedInLoginFragment extends Fragment {
	
	private int mColorRes = -1;
	WebView webView;
//	public BeaconBusinessScanFragment() { 
//		this(R.color.white);
//	}
	
//	public BeaconBusinessScanFragment(int colorRes) {
//		mColorRes = colorRes;
//		setRetainInstance(true);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		if (savedInstanceState != null)
//			mColorRes = savedInstanceState.getInt("mColorRes");
//		int color = getResources().getColor(mColorRes);
//		// construct the RelativeLayout
//		RelativeLayout v = new RelativeLayout(getActivity());
//		v.setBackgroundColor(color);		
		return inflater.inflate(R.layout.login_linkedin, null);
	}
	
	@SuppressLint("JavascriptInterface")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		webView = (WebView) getActivity().findViewById(R.id.login_linkedin_webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new myWebClient());
		webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

		webView.loadUrl("https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=77upwjrghunhka&state=KNO&redirect_uri=https://bluecastalpha.herokuapp.com/mobile/linkedin/login");
		
	}
	
    public class myWebClient extends WebViewClient
    {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    // TODO Auto-generated method stub
                    super.onPageStarted(view, url, favicon);
            }
            
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    
                    view.loadUrl(url);
                    return true;
                    
            }
            
            @Override
            public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
                webView.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

            super.onPageFinished(view, url);
            }
    }
    
    class MyJavaScriptInterface
    {
        @SuppressWarnings("unused")
        public void processHTML(String html)
        {
            // process the html as needed by the app
        	Toast.makeText(getActivity(), html, Toast.LENGTH_LONG).show();
        }
    }
	
//	public class LinkedInWebViewClient extends WebViewClient {
//	    @Override
//	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			view.loadUrl("https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=77upwjrghunhka&state=KNO&redirect_uri=https://bluecastalpha.herokuapp.com/mobile/linkedin/login");
//	        return true;
//	    }
//	}
	
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt("mColorRes", mColorRes);
//	}
	
}
