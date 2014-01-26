package com.bluecast.async_tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.bluecast.interfaces.ScanPeopleAsyncTaskDelegate;
import com.bluecast.models.Beacon;

public class ScanPeopleAsyncTask extends AsyncTask<Void,Void,String>{
	
	public ArrayList<Beacon> beaconArrayList;
    private ScanPeopleAsyncTaskDelegate delegate;
	
    public ScanPeopleAsyncTask(ScanPeopleAsyncTaskDelegate callback){
        delegate = callback;
    }

	@Override
	protected String doInBackground(Void... params) {
		beaconArrayList = new ArrayList<Beacon>(); 
		
		Beacon beacon1 = new Beacon("10","10","10"); 
		Beacon beacon2 = new Beacon("11","11","11"); 
		
		beaconArrayList.add(beacon1);
		beaconArrayList.add(beacon2);
		
		JSONArray ja = new JSONArray();

		for(int i = 0; i < beaconArrayList.size(); i ++){
			Beacon myBeacon = beaconArrayList.get(i);
					
			JSONObject jo = new JSONObject();
			try {
				jo.put("uuid", myBeacon.getUuid());
				jo.put("minor", myBeacon.getMinor());
				jo.put("major", myBeacon.getMajor());

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
			ja.put(jo);
		}

		JSONObject mainObj = new JSONObject();
		try {
			mainObj.put("beacons", ja);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
    	String page = "";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("https://bluecastalpha.herokuapp.com/mobile/beacon/scan");
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
//            Gson gson = new Gson();
//            Type listOfNewsStories = new TypeToken<ArrayList<NewsStory>>(){}.getType();
//            stories = gson.fromJson(page, listOfNewsStories);
        }catch (Exception e){
            e.printStackTrace();
        }
		return page;
	};

    @Override
    protected void onPostExecute(String result) {
    	delegate.didReceiveResponse(result);
    	Log.e("Tag", result);
    }

}