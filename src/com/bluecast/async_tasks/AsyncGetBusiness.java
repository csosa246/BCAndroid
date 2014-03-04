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
import android.util.Log;

import com.bluecast.adapters.AdapterSharedPreferences;
import com.bluecast.interfaces.DelegateAsyncGetBusiness;
import com.bluecast.models.Business;
import com.bluecast.models.Job;

public class AsyncGetBusiness extends
		AsyncTask<Void, Void, ArrayList<Business>> {
	AdapterSharedPreferences sharedPreferences;
	private DelegateAsyncGetBusiness delegate;
	Context context; 

	public AsyncGetBusiness(DelegateAsyncGetBusiness callback,
			Context context) {
		delegate = callback;
		this.context = context;
		sharedPreferences = new AdapterSharedPreferences(this.context);
		businessArrayList = new ArrayList<Business>();
	}
	
	ArrayList<Business> businessArrayList; 
	String pageFinal; 
	
	@Override
	protected ArrayList<Business> doInBackground(Void... params) {

		String page = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://bluecastme.herokuapp.com/event_show");

		try {
			HttpResponse response = client.execute(httpGet);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line;
			while ((line = in.readLine()) != null) {
				page += line + "\n";
			}
						
			JSONArray jsonArrayCompanies = new JSONArray(page);
			for(int i = 0; i<jsonArrayCompanies.length(); i++){
				JSONObject jsonObjectCompany = jsonArrayCompanies.getJSONObject(i);
				JSONArray jsonArrayJobs = jsonObjectCompany.getJSONArray("Jobs");
				ArrayList<Job> jobsArrayList= new ArrayList<Job>();
				for(int j = 0; j < jsonArrayJobs.length(); j++){
					JSONObject jsonObjectJob = jsonArrayJobs.getJSONObject(j);
					JSONArray jsonArrayAcceptableMajors = jsonObjectJob.getJSONArray("Majors");
					ArrayList<String> majorsArrayList = new ArrayList<String>();
					for(int k = 0; k < jsonArrayAcceptableMajors.length();k++){
						Log.e("Majors", jsonArrayAcceptableMajors.getString(k).toString());
						majorsArrayList.add(jsonArrayAcceptableMajors.getString(k));
					}
					Job job = new Job(jsonObjectJob.getString("title"),
							jsonObjectJob.getString("position"), 
							jsonObjectJob.getString("term"), 
							jsonObjectJob.getString("description"),
							majorsArrayList);
					jobsArrayList.add(job);	
				}
				Business business = new Business(jsonObjectCompany.getString("event_id"),
						jsonObjectCompany.getString("company"),
						jsonObjectCompany.getString("description"), 
						jsonObjectCompany.getString("picture_url"), 
						jsonObjectCompany.getString("url"),
						jobsArrayList);
				businessArrayList.add(business);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return businessArrayList;
	};

	@Override
	protected void onPostExecute(ArrayList<Business> result) {
		delegate.didFinishGettingBussiness(result);
	}

}