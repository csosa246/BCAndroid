package com.bluecast.fragments.main;

import java.util.Collection;

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

import com.bluecast.async_tasks.AsyncBeaconRegister;
import com.bluecast.bluecast.ActivityMain;
import com.bluecast.bluecast.R;
import com.bluecast.interfaces.DelegateAsyncBeaconRegister;
import com.radiusnetworks.ibeacon.IBeacon;

public class FragmentRightMenu extends Fragment implements
		DelegateAsyncBeaconRegister {

	ActivityMain mainActivity;
	Collection<IBeacon> iBeaconCollection;
	// MainFragmentDelegate mainFragmentDelegate;

	public int index = 5;

	public int getIndex() {
		return index;
	}

	// public BeaconBusinessScanFragment() {
	// this(R.color.white);
	// }

	// @Override
	// public void onAttach(Activity activity) {
	// super.onAttach(activity);
	//
	// try {
	// mainFragmentDelegate = (MainFragmentDelegate) activity;
	// } catch (ClassCastException e) {
	// throw new ClassCastException(activity.toString()
	// + " must implement OnHeadlineSelectedListener");
	// }
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
		mainActivity = (ActivityMain) getActivity();
	}

	public void willSetupButtons() {
		Button didClickRegisterButton = (Button) getActivity().findViewById(
				R.id.settings_button_choosebroadcast);
		didClickRegisterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iBeaconCollection = ((ActivityMain) getActivity())
						.getiBeaconCollection();
				shouldConfirmBeacon();

			}
		});
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
		AsyncBeaconRegister registerBeaconAsyncTask = new AsyncBeaconRegister(
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