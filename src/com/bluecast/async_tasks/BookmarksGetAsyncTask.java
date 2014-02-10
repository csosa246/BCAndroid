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
import android.widget.Toast;

import com.bluecast.adapters.SharedPreferencesAdapter;
import com.bluecast.interfaces.BookmarkGetAsyncTaskDelegate;
import com.bluecast.models.Person;

public class BookmarksGetAsyncTask extends
		AsyncTask<Void, Void, ArrayList<Person>> {
	SharedPreferencesAdapter sharedPreferences;
	private BookmarkGetAsyncTaskDelegate delegate;
	Context context; 

	public BookmarksGetAsyncTask(BookmarkGetAsyncTaskDelegate callback,
			Context context) {
		delegate = callback;
		this.context = context;
		sharedPreferences = new SharedPreferencesAdapter(this.context);
	}

	String pageFinal; 
	
	ArrayList<Person> personArrayList;

	@Override
	protected ArrayList<Person> doInBackground(Void... params) {

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

			personArrayList = new ArrayList<Person>();
			JSONObject inputJSON = new JSONObject(page);
			Iterator<String> keysIterator = inputJSON.keys();
			while (keysIterator.hasNext()) {
				String keyStr = (String) keysIterator.next();
				
				JSONObject jsonObject = new JSONObject(inputJSON.getString(keyStr));
				JSONObject profileObject = new JSONObject(jsonObject.getString("profile"));
				Person person = new Person(
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
	protected void onPostExecute(ArrayList<Person> result) {
		


		delegate.didFinishAddingBookmarks(result);
	}

}