package com.bluecast.async_tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.json.*;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AndroidAsyncHTTPTest {
	
    public void getGoogle() throws JSONException {
    	
    	
    	
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.setTimeout(10);
    	client.get("http://www.google.com", new AsyncHttpResponseHandler() {
    	    @Override
    	    public void onSuccess(String response) {
    	        Log.e("RESPONSE", response);
    	        
    	    }
    	    
    	    
    	    
    	    @Override
    	    public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
    	    	super.onFailure(arg0, arg1, arg2, arg3);
    	    	
    	    	
    	    	
    	    }
    	    
    	});
    	
    	
    	
//        client.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(JSONArray timeline) {
//                // Pull out the first event on the public timeline
//                JSONObject firstEvent;
//				try {
//					firstEvent = timeline.getJSONObject(0);
//	                String tweetText = firstEvent.getString("text");
//
//	                // Do something with the response
//	                System.out.println(tweetText);
//	                
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//            }
//        });
    }
}