package com.bluecast.async_tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

import com.bluecast.adapters.SharedPreferencesAdapter;
import com.bluecast.interfaces.ScanPeopleAsyncTaskDelegate;
import com.bluecast.models.Person;
import com.radiusnetworks.ibeacon.IBeacon;

public class ScanPeopleAsyncTask extends
		AsyncTask<Void, Void, ArrayList<Person>> {
	public Collection<IBeacon> beacons;
	SharedPreferencesAdapter sharedPreferences;
	private ScanPeopleAsyncTaskDelegate delegate;

	public ScanPeopleAsyncTask(ScanPeopleAsyncTaskDelegate callback,
			Context context, Collection<IBeacon> beacons) {
		delegate = callback;
		this.beacons = beacons;
		sharedPreferences = new SharedPreferencesAdapter(context);
	}

	ArrayList<Person> personArrayList;

	@Override
	protected ArrayList<Person> doInBackground(Void... params) {
		JSONArray jsonBeaconArray = new JSONArray();
		for (IBeacon beacon : beacons) {
			JSONObject jsonBeaconObject = new JSONObject();
			try {
				jsonBeaconObject.put("uuid", beacon.getProximityUuid());
				jsonBeaconObject
						.put("minor", String.valueOf(beacon.getMinor()));
				jsonBeaconObject
						.put("major", String.valueOf(beacon.getMajor()));
			} catch (JSONException e1) {

			}
			jsonBeaconArray.put(jsonBeaconObject);
		}

		Log.e("user id", sharedPreferences.getUserID());

		JSONObject jsonFullObject = new JSONObject();
		try {
			jsonFullObject.put("user_id", sharedPreferences.getUserID());
			jsonFullObject.put("remember_token",
					sharedPreferences.getUserToken());
			jsonFullObject.put("beacons", jsonBeaconArray);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		Log.e("JSONREQUEST", jsonFullObject.toString());

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
			Log.i("page", page);

			personArrayList = new ArrayList<Person>();
			JSONObject inputJSON = new JSONObject(page);
			Iterator<String> keysIterator = inputJSON.keys();
			while (keysIterator.hasNext()) {
				String keyStr = (String) keysIterator.next();
				JSONObject jsonObject = new JSONObject(
						inputJSON.getString(keyStr));
				Person person = new Person(jsonObject.getString("last_name"),
						jsonObject.getString("first_name"),
						jsonObject.getString("distance"),
						jsonObject.getString("picture_url"),
						jsonObject.getString("public_profile_url"));
				personArrayList.add(person);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return personArrayList;
	};

	@Override
	protected void onPostExecute(ArrayList<Person> result) {
		delegate.didFinishIdentifyingBeacons(result);
	}

}