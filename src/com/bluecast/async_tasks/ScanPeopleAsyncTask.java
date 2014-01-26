package com.bluecast.async_tasks;

//package com.julintani.ccny_and.async_tasks;

import android.os.AsyncTask;
import android.util.Log;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.julintani.ccny_and.fragments.NewsGridFragment;
//import com.julintani.ccny_and.models.NewsStory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bluecast.interfaces.ScanPeopleAsyncTaskDelegate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ScanPeopleAsyncTask extends AsyncTask<Void,Void,String>{
    private ScanPeopleAsyncTaskDelegate delegate;
	
    public ScanPeopleAsyncTask(ScanPeopleAsyncTaskDelegate callback){
        delegate = callback;
    }

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
    	String page = "";
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://bluecasttest.herokuapp.com/mobile_show");
            HttpResponse response = client.execute(get);
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

//    public interface FeedTaskCallback{
//        public void setFeed(ArrayList<NewsStory> stories);
//    }
}