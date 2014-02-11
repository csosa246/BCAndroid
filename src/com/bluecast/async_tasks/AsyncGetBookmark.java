package com.bluecast.async_tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.bluecast.adapters.AdapterSharedPreferences;
import com.bluecast.interfaces.DelegateAsyncGetBookmark;
import com.bluecast.models.ModelPerson;

public class AsyncGetBookmark extends
		AsyncTask<Void, Void, ArrayList<ModelPerson>> {
	AdapterSharedPreferences sharedPreferences;
	private DelegateAsyncGetBookmark delegate;
	Context context; 

	public AsyncGetBookmark(DelegateAsyncGetBookmark callback,
			Context context) {
		delegate = callback;
		this.context = context;
		sharedPreferences = new AdapterSharedPreferences(this.context);
	}

	String pageFinal; 
	
	ArrayList<ModelPerson> personArrayList;

	@Override
	protected ArrayList<ModelPerson> doInBackground(Void... params) {

		JSONObject jsonFullObject = new JSONObject();
		try {
			jsonFullObject.put("user_id", sharedPreferences.getUserID());
			jsonFullObject.put("remember_token",
					sharedPreferences.getUserToken());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		String page = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://bluecastalpha.herokuapp.com/mobile/beacon/linkedin/bookmark/show");
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
//			Log.i("page", page);
			pageFinal = page;

			personArrayList = new ArrayList<ModelPerson>();
			JSONObject inputJSON = new JSONObject(page);
			Iterator<String> keysIterator = inputJSON.keys();
			while (keysIterator.hasNext()) {
				String keyStr = (String) keysIterator.next();
				
				JSONObject jsonObject = new JSONObject(inputJSON.getString(keyStr));
				JSONObject profileObject = new JSONObject(jsonObject.getString("profile"));
				ModelPerson person = new ModelPerson(
						profileObject.getString("last_name"),
						profileObject.getString("first_name"),
						profileObject.getString("headline"),
						profileObject.getString("distance"),
						profileObject.getString("picture_url"),
						profileObject.getString("public_profile_url"),
						profileObject.getString("id"));
				
				
				personArrayList.add(person);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return personArrayList;
	};

	@Override
	protected void onPostExecute(ArrayList<ModelPerson> result) {
		


		delegate.didFinishAddingBookmarks(result);
	}

}