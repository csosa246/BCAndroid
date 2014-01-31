package com.bluecast.async_tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bluecast.adapters.UserSharedPreferencesAdapter;
import com.bluecast.interfaces.ScanPeopleAsyncTaskDelegate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.radiusnetworks.ibeacon.IBeacon;

public class ScanPeopleAsyncTask extends AsyncTask<Void, Void, String> {
	public Collection<IBeacon> beacons;
	UserSharedPreferencesAdapter sharedPreferences;
	private ScanPeopleAsyncTaskDelegate delegate;

	public ScanPeopleAsyncTask(ScanPeopleAsyncTaskDelegate callback,
			Context context, Collection<IBeacon> beacons) {
		delegate = callback;
		this.beacons = beacons;
		sharedPreferences = new UserSharedPreferencesAdapter(context);
	}

	@Override
	protected String doInBackground(Void... params) {
		JSONArray jsonBeaconArray = new JSONArray();
		for (IBeacon beacon : beacons) {
			JSONObject jsonBeaconObject = new JSONObject();
			try {
				jsonBeaconObject.put("uuid", beacon.getProximityUuid());
				jsonBeaconObject.put("minor", String.valueOf(beacon.getMinor()));
				jsonBeaconObject.put("major", String.valueOf(beacon.getMajor()));
			} catch (JSONException e1) {

			}
			jsonBeaconArray.put(jsonBeaconObject);
		}

		JSONObject jsonFullObject = new JSONObject();
		try {
			jsonFullObject.put("user_id", sharedPreferences.getUserID());
			jsonFullObject.put("remember_token", sharedPreferences.getUserToken());
			jsonFullObject.put("beacons", jsonBeaconArray);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		String page = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://bluecastalpha.herokuapp.com/mobile/beacon/scan");
		httpPost.setHeader("Content-type", "application/json");
		StringEntity se;
		try {
			se = new StringEntity(jsonFullObject.toString());
			httpPost.setEntity(se);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			HttpResponse response = client.execute(httpPost);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line;
			while ((line = in.readLine()) != null) {
				page += line + "\n";
			}
			
			
			// Gson gson = new Gson();
			// Type listOfNewsStories = new
			// TypeToken<ArrayList<NewsStory>>(){}.getType();
			// stories = gson.fromJson(page, listOfNewsStories);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		Log.e("TAG", jsonFullObject.toString());
		return page;
	};

	@Override
	protected void onPostExecute(String result) {
		delegate.didFinishIdentifyingBeacons(result);
		Log.e("Tag", result);
	}

}