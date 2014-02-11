package com.bluecast.async_tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.bluecast.adapters.AdapterSharedPreferences;
import com.bluecast.interfaces.DelegateAsyncGetBusiness;
import com.bluecast.models.ModelBusiness;

public class AsyncGetBusiness extends
		AsyncTask<Void, Void, ArrayList<ModelBusiness>> {
	AdapterSharedPreferences sharedPreferences;
	private DelegateAsyncGetBusiness delegate;
	Context context; 

	public AsyncGetBusiness(DelegateAsyncGetBusiness callback,
			Context context) {
		delegate = callback;
		this.context = context;
		sharedPreferences = new AdapterSharedPreferences(this.context);
		businessArrayList = new ArrayList<ModelBusiness>();
	}
	
	ArrayList<ModelBusiness> businessArrayList; 

	String pageFinal; 
	
	@Override
	protected ArrayList<ModelBusiness> doInBackground(Void... params) {

		String page = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://bluecast.herokuapp.com/mobile/exhibits");

		try {
			HttpResponse response = client.execute(httpGet);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line;
			while ((line = in.readLine()) != null) {
				page += line + "\n";
			}
			
//			pageFinal = page;
			JSONArray jsonArraye1 = new JSONArray(page);
			JSONArray jsonArraye2 = jsonArraye1.getJSONArray(0);

			for(int i = 0; i<jsonArraye2.length(); i++) {
				JSONObject jsonObject = jsonArraye2.getJSONObject(i);
				String businessID = jsonObject.getString("id");
				String title = jsonObject.getString("title");
				
				ModelBusiness modelBusiness = new ModelBusiness(
						jsonObject.getString("id"), 
						jsonObject.getString("title"), 
						jsonObject.getString("description"), 
						jsonObject.getString("url"), 
						jsonObject.getString("picture_url"), 
						jsonObject.getString("user_id"), 
						jsonObject.getString("created_at"), 
						jsonObject.getString("updated_at"),
						jsonObject.getString("event_id"));
//				Log.i("businesstitle", modelBusiness.get);
				businessArrayList.add(modelBusiness);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return businessArrayList;
	};

	@Override
	protected void onPostExecute(ArrayList<ModelBusiness> result) {
		delegate.didFinishGettingBussiness(result);
	}

}