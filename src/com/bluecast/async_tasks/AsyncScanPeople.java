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

import com.bluecast.adapters.AdapterSharedPreferences;
import com.bluecast.interfaces.DelegateAsyncScanPeople;
import com.bluecast.models.ModelPerson;
import com.radiusnetworks.ibeacon.IBeacon;

public class AsyncScanPeople extends
		AsyncTask<Void, Void, ArrayList<ModelPerson>> {
	public Collection<IBeacon> beacons;
	AdapterSharedPreferences sharedPreferences;
	private DelegateAsyncScanPeople delegate;

	public AsyncScanPeople(DelegateAsyncScanPeople callback,Context context, Collection<IBeacon> beacons) {
		delegate = callback;
		this.beacons = beacons;
		
		sharedPreferences = new AdapterSharedPreferences(context);
	}

	ArrayList<ModelPerson> personArrayList;

	@Override
	protected ArrayList<ModelPerson> doInBackground(Void... params) {
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

//		Log.e("user id", sharedPreferences.getUserID());
		
		
		
		JSONObject jsonFullObject = new JSONObject();
		try {
			jsonFullObject.put("user_id", sharedPreferences.getUserID());
			jsonFullObject.put("remember_token",
					sharedPreferences.getUserToken());
			jsonFullObject.put("beacons", jsonBeaconArray);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

//		Log.e("JSONREQUEST", jsonFullObject.toString());

		String page = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://bluecastme.herokuapp.com/beacons/linkedin_scan");
		httpPost.setHeader("Content-type", "application/json");
		StringEntity se;
		try {
			Log.e("TAGGGGGG", jsonFullObject.toString());
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
//			Log.i("page", page);

			personArrayList = new ArrayList<ModelPerson>();
			JSONObject inputJSON = new JSONObject(page);
			Iterator<String> keysIterator = inputJSON.keys();
			while (keysIterator.hasNext()) {
				String keyStr = (String) keysIterator.next();
				
				Log.e("KEY", keyStr);
				
				JSONObject jsonObject = new JSONObject(inputJSON.getString(keyStr));
//				
				String major = jsonObject.getString("major"); 
				String minor = jsonObject.getString("minor");
				
				JSONObject profileObject = new JSONObject(jsonObject.getString("profile"));
				
				Log.i("profile object", profileObject.toString());

//				
				ModelPerson person = new ModelPerson(profileObject.getString("last_name"),
						profileObject.getString("first_name"),
						profileObject.getString("headline"),
						profileObject.getString("distance"),
						profileObject.getString("picture_url"),
						profileObject.getString("public_profile_url"),
						profileObject.getString("id"));
				person.setMinor(minor);
				
				Log.e("MINOR", person.getMinor());

				personArrayList.add(person);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return personArrayList;
	};

	@Override
	protected void onPostExecute(ArrayList<ModelPerson> result) {
		delegate.didFinishIdentifyingBeacons(result);
	}

}