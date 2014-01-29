package com.bluecast.fragments.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluecast.bluecast.R;

public class SettingsRightMenuFragment extends Fragment {	
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
		return inflater.inflate(R.layout.settings, null);
	}
	
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
		}
	
	public void didClickRegisterBeacon(View v){
		
	}
	
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt("mColorRes", mColorRes);
//	}
	
}