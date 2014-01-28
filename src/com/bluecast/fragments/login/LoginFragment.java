package com.bluecast.fragments.login;

import com.bluecast.bluecast.R;

import android.os.Bundle;
import android.app.Fragment;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginFragment extends Fragment {
	
	private int mColorRes = -1;
	private Typeface robotoTypeface ;
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
		return inflater.inflate(R.layout.login, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		willSetupTypeface();
		willSetupTextViews();
		willSetupButtons();
	}
	
	public void willSetupTypeface(){
		robotoTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
	}
	
	public void willSetupTextViews(){
//		TextView loginMainTextViewTitle = (TextView) getActivity().findViewById(R.id.login_main_textview_title);
//		loginMainTextViewTitle.setTypeface(robotoTypeface);
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
