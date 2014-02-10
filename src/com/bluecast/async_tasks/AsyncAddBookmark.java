package com.bluecast.async_tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.bluecast.adapters.AdapterSharedPreferences;
import com.bluecast.interfaces.DelegateAsyncAddBookmark;
import com.bluecast.models.ModelPerson;
import com.radiusnetworks.ibeacon.IBeacon;

public class AsyncAddBookmark extends
		AsyncTask<Void, Void, String> {
	public Collection<IBeacon> beacons;
	AdapterSharedPreferences sharedPreferences;
	DelegateAsyncAddBookmark delegate;
	ModelPerson person; 
	String note; 

	public AsyncAddBookmark(DelegateAsyncAddBookmark callback,
			Context context,ModelPerson person,String note) {
		this.note = note;
		this.person = person; 
		delegate = callback;
		sharedPreferences = new AdapterSharedPreferences(context);
	}

	ArrayList<ModelPerson> personArrayList;

	@Override
	protected String doInBackground(Void... params) {

		JSONObject jsonFullObject = new JSONObject();
		try {
			jsonFullObject.put("user_id", sharedPreferences.getUserID());
			jsonFullObject.put("remember_token",
					sharedPreferences.getUserToken());
			jsonFullObject.put("linkedin_id", person.getLinkedInID());
			jsonFullObject.put("note", note);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		String page = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://bluecastalpha.herokuapp.com/mobile/beacon/linkedin/bookmark/add");
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	};

	@Override
	protected void onPostExecute(String result) {
		delegate.didFinishAddingBookmarks(result);
	}

}