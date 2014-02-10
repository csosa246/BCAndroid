package com.bluecast.fragments.main;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.bluecast.async_tasks.AsyncAddBookmark;
import com.bluecast.bluecast.R;
import com.bluecast.interfaces.DelegateAsyncAddBookmark;
import com.bluecast.models.ModelPerson;

public class FragmentPublicProfile extends Fragment implements DelegateAsyncAddBookmark {
		WebView webView; 
		String note; 
		ModelPerson person; 
		
		public void setProperties(ModelPerson person){
			this.person = person; 
			URL = person.getPublicProfileURL(); 
//			linkedInID = person.get
			
		}

//	public BeaconBusinessScanFragment(int colorRes) {
//		mColorRes = colorRes;
//		setRetainInstance(true);
//	}
		String URL; 
		String linkedInID;
//		public void loadURL(String URL){
//			this.URL = URL; 
//		}

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
			willSetupButtons();
			willSetupEditTexts();
			willSetupWebView();
	}
	
	public void willSetupButtons(){
		final Button bookmarkButton = (Button) getActivity().findViewById(R.id.button_bookmark);
		bookmarkButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				shouldBookmarkPerson(); 
				bookmarkButton.setVisibility(View.GONE);
			}
		});
	}
	EditText editTextNote;
	
	public void willSetupEditTexts(){
		editTextNote = (EditText) getActivity().findViewById(R.id.edittext_note);
	}
	
	public void willSetupWebView() {
		webView = (WebView) getActivity().findViewById(
				R.id.webview_public_profile);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				
			}
		});

		webView.loadUrl(URL);
	}
	
	public void shouldBookmarkPerson(){
		note = editTextNote.getText().toString();
		AsyncAddBookmark bookmarkAsyncTaskAdapter = new AsyncAddBookmark(this,getActivity(),person,note);
		bookmarkAsyncTaskAdapter.execute();
	}

	@Override
	public void didFinishAddingBookmarks(String response) {
		Log.e("done", response);		
	}
	
	//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt("mColorRes", mColorRes);
//	}
	
}
