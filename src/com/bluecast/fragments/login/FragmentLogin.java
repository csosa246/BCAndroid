package com.bluecast.fragments.login;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bluecast.adapters.AdapterSharedPreferences;
import com.bluecast.bluecast.ActivityLogin;
import com.bluecast.bluecast.R;

public class FragmentLogin extends Fragment {
	
	private Typeface robotoTypeface ;
	ActivityLogin loginActivity; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		if (savedInstanceState != null)
//			mColorRes = savedInstanceState.getInt("mColorRes");
//		int color = getResources().getColor(mColorRes);
//		// construct the RelativeLayout
//		RelativeLayout v = new RelativeLayout(getActivity());
//		v.setBackgroundColor(color);		
		return inflater.inflate(R.layout.login, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		willCheckLoginCredentials();
		willSetupTypeface();
		willSetupButtons();
	}
	
	public void willSetupMainActivity() {
		loginActivity = (ActivityLogin) getActivity();
	}
	
//	public void willCheckLoginCredentials(){
//		SharedPreferencesAdapter sharedPreferencesAdapter = new SharedPreferencesAdapter(getActivity());
//		if(sharedPreferencesAdapter.getUserID()!=""){
////			Toast.makeText(getActivity(), "woop", Toast.LENGTH_LONG).show();
//			loginActivity.didConfirmLoginAndShouldProceedToMainActivity();
//		}
//	}
	
	public void willSetupTypeface(){
		robotoTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
	}
	
	public void willSetupButtons(){
		Button loginMainButton = (Button) getActivity().findViewById(R.id.login_main_button_login);
		loginMainButton.setTypeface(robotoTypeface);
	}
	
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt("mColorRes", mColorRes);
//	}
}