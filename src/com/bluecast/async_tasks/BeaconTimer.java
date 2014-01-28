package com.bluecast.async_tasks;

import java.util.Collection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bluecast.adapters.UserSharedPreferencesAdapter;
import com.bluecast.interfaces.ScanPeopleAsyncTaskDelegate;
import com.radiusnetworks.ibeacon.IBeacon;

public class BeaconTimer extends AsyncTask<Void, Void, String> {
	public Collection<IBeacon> beacons;
	UserSharedPreferencesAdapter sharedPreferences;
	private ScanPeopleAsyncTaskDelegate delegate;

	public BeaconTimer(ScanPeopleAsyncTaskDelegate callback,
			Context context, Collection<IBeacon> beacons) {
		delegate = callback;
		this.beacons = beacons;
		sharedPreferences = new UserSharedPreferencesAdapter(context);
	}

	@Override
	protected String doInBackground(Void... params) {

		
		
		return "";
	};

	@Override
	protected void onPostExecute(String result) {
		delegate.didReceiveResponse(result);
		Log.e("Tag", result);
	}

}