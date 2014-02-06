package com.bluecast.fragments.main;

import java.util.Collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluecast.async_tasks.RegisterBeaconAsyncTask;
import com.bluecast.async_tasks.ScanPeopleAsyncTask;
import com.bluecast.bluecast.MainActivity;
import com.bluecast.bluecast.R;
import com.bluecast.interfaces.MainFragmentDelegate;
import com.bluecast.interfaces.RegisterBeaconAsyncTaskDelegate;
import com.radiusnetworks.ibeacon.IBeacon;

public class SettingsRightMenuFragment extends Fragment implements
		RegisterBeaconAsyncTaskDelegate {

	MainActivity mainActivity;
	Collection<IBeacon> iBeaconCollection;
//	MainFragmentDelegate mainFragmentDelegate;
	
	public int index = 5; 
public int getIndex() {
	return index;
}
	// public BeaconBusinessScanFragment() {
	// this(R.color.white);
	// }

//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//
//		try {
//			mainFragmentDelegate = (MainFragmentDelegate) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnHeadlineSelectedListener");
//		}
//	}

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
				// mainActivity.startBeaconScan(6000);
					// showText("There are beacons, and we're gonna run through to try and register them");
//					shouldConfirmBeacon();
				
				
				
				
//					mainFragmentDelegate.shouldStartBeaconScan(5000,"settings");
				
			}
		});
	}
	
	public void didFindBeacons(Collection<IBeacon> iBeaconCollection){
		this.iBeaconCollection = iBeaconCollection;
//		showText("did find some ibeacons on settings");
		shouldConfirmBeacon();
	}
	
	public void didNotFindBeacons(){
		
	}


	public void shouldConfirmBeacon() {
		AlertDialog.Builder saveDialog = new AlertDialog.Builder(getActivity());
		saveDialog.setTitle("Beacon Register");
		saveDialog.setMessage("Confirmation Number :");
		final EditText input = new EditText(getActivity());
		saveDialog.setView(input);
		saveDialog.setPositiveButton("Save",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String confirmationNumber = input.getText().toString();
						int i = 0;
						for (IBeacon beacon : iBeaconCollection) {
							if (String.valueOf(beacon.getMinor()).equals(
									confirmationNumber)) {
								// Send registration
								Toast.makeText(getActivity(),
										"Yeah we got that", Toast.LENGTH_LONG)
										.show();
								
								shouldRegisterBeacon(beacon);
							}
						}
					}
				});
		saveDialog.setNegativeButton("Cancel", null);
		saveDialog.show();
	}

	public void shouldRegisterBeacon(IBeacon beacon) {
		RegisterBeaconAsyncTask registerBeaconAsyncTask = new RegisterBeaconAsyncTask(
				this, getActivity(), beacon);
		registerBeaconAsyncTask.execute();
	}

	public void showText(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}

	@Override
	public void didReceiveResponse(String response) {
		Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
	}

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// outState.putInt("mColorRes", mColorRes);
	// }

}