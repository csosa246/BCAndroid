package com.bluecast.fragments;

import java.lang.reflect.Type;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bluecast.adapters.UserSharedPreferencesAdapter;
import com.bluecast.bluecast.LoginActivity;
import com.bluecast.bluecast.R;
import com.bluecast.models.LinkedInLoginCredentialsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LinkedInLoginFragment extends Fragment {

	private WebView webView;
	UserSharedPreferencesAdapter sharedPreferences;
	private static String URL = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=77upwjrghunhka&state=KNO&redirect_uri=https://bluecastalpha.herokuapp.com/mobile/linkedin/login";
	LoginActivity loginActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// if (savedInstanceState != null)
		// mColorRes = savedInstanceState.getInt("mColorRes");
		// int color = getResources().getColor(mColorRes);
		// // construct the RelativeLayout
		// RelativeLayout v = new RelativeLayout(getActivity());
		// v.setBackgroundColor(color);
		return inflater.inflate(R.layout.login_linkedin, null);
	}

	@SuppressLint("JavascriptInterface")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		willSetupMainActivity();
		willSetupSharedPreferences();
		willSetupWebView();
	}

	public void willSetupSharedPreferences() {
		sharedPreferences = new UserSharedPreferencesAdapter(getActivity());
	}

	public void willSetupMainActivity() {
		loginActivity = (LoginActivity) getActivity();
	}

	public void willSetupWebView() {
		webView = (WebView) getActivity().findViewById(
				R.id.login_linkedin_webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JavaScriptInterface(), "HtmlViewer");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {

				
				if (url.equals("https://www.linkedin.com/uas/oauth2/authorizedialog/submit")) {
					// Wrong credentials
					return;
				}

				if (url.equals("https://bluecastalpha.herokuapp.com/mobile/linkedin/login?error=access_denied&error_description=the+user+denied+your+request&state=KNO")) {
					// Cancelled
					loginActivity.didCancelLinkedInLogin();
					return;
				}

				if (!url.equals(URL)) {
					loginActivity.showMainLogin();

					webView.loadUrl("javascript:window.HtmlViewer.showHTML"
							+ "(document.body.textContent);");
					return;
				}

			}
		});

		webView.loadUrl(URL);
	}

	class JavaScriptInterface {
		@JavascriptInterface
		public void showHTML(String html) {
			Gson gson = new Gson();
			Type listType = new TypeToken<List<LinkedInLoginCredentialsModel>>() {
			}.getType();
			List<LinkedInLoginCredentialsModel> myModelList = gson.fromJson(
					html, listType);
			for (LinkedInLoginCredentialsModel js_obj : myModelList) {
				sharedPreferences.saveUser("IDBOO",
						"TOKENBOO");
			}
			
			
			loginActivity.didConfirmLoginAndShouldProceedToMainActivity();
		}

	}

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// outState.putInt("mColorRes", mColorRes);
	// }
}