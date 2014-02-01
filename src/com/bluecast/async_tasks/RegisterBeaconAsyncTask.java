package com.bluecast.async_tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bluecast.adapters.SharedPreferencesAdapter;
import com.bluecast.interfaces.RegisterBeaconAsyncTaskDelegate;
import com.radiusnetworks.ibeacon.IBeacon;

public class RegisterBeaconAsyncTask extends AsyncTask<Void, Void, String> {
	IBeacon beacon;
	SharedPreferencesAdapter sharedPreferences;
	private RegisterBeaconAsyncTaskDelegate delegate;

	public RegisterBeaconAsyncTask(RegisterBeaconAsyncTaskDelegate callback,
			Context context, IBeacon beacon) {
		delegate = callback;
		this.beacon = beacon;
		sharedPreferences = new SharedPreferencesAdapter(context);
	}

	@Override
	protected String doInBackground(Void... params) {
		JSONObject mainObj = new JSONObject();
		try {
			mainObj.put("user_id", sharedPreferences.getUserID());
			mainObj.put("remember_token", sharedPreferences.getUserToken());
			mainObj.put("uuid", beacon.getProximityUuid());
			mainObj.put("major", String.valueOf(beacon.getMajor()));
			mainObj.put("minor", String.valueOf(beacon.getMinor()));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		String page = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://bluecastalpha.herokuapp.com/mobile/beacon/register");
		httpPost.setHeader("Content-type", "application/json");
		StringEntity se;
		try {
			se = new StringEntity(mainObj.toString());
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
		Log.e("TAG", mainObj.toString());
		return page;
	};

	@Override
	protected void onPostExecute(String result) {
		delegate.didReceiveResponse(result);
		Log.e("Tag", result);
	}

}