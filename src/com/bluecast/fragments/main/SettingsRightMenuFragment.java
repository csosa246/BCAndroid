package com.bluecast.fragments.main;

import java.util.Collection;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bluecast.async_tasks.ScanPeopleAsyncTask;
import com.bluecast.bluecast.MainActivity;
import com.bluecast.bluecast.R;
import com.radiusnetworks.ibeacon.IBeacon;

public class SettingsRightMenuFragment extends Fragment {

	MainActivity mainActivity;
	Collection<IBeacon> iBeaconCollection;

	// public BeaconBusinessScanFragment() {
	// this(R.color.white);
	// }

	// public BeaconBusinessScanFragment(int colorRes) {
	// mColorRes = colorRes;
	// setRetainInstance(true);
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// if (savedInstanceState != null)
		// mColorRes = savedInstanceState.getInt("mColorRes");
		// int color = getResources().getColor(mColorRes);
		// // construct the RelativeLayout
		// RelativeLayout v = new RelativeLayout(getActivity());
		// v.setBackgroundColor(color);
		return inflater.inflate(R.layout.settings, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		willSetupMainActivity();
		willSetupButtons();
	}

	public void willSetupMainActivity() {
		mainActivity = (MainActivity) getActivity();
	}

	public void willSetupButtons() {
		Button didClickRegisterButton = (Button) getActivity().findViewById(
				R.id.settings_button_choosebroadcast);
		didClickRegisterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity fca = (MainActivity) getActivity();
				iBeaconCollection = fca.getiBeaconCollection();
				if (iBeaconCollection.size() > 0) {
					// showText("There are beacons, and we're gonna run through to try and register them");
					
				}
			}
		});
	}

	public void shouldConfirmBeacon() {
		
	}

	public void showText(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// outState.putInt("mColorRes", mColorRes);
	// }

}