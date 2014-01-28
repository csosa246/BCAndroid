package com.bluecast.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class UserSharedPreferencesAdapter {
	SharedPreferences sharedPreferences;
	public static String ID = "com.bluecast.adapters.user.id";
	public static String TOKEN = "com.bluecast.adapters.user.token";

	public UserSharedPreferencesAdapter(Context context){
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public String getUserID() {
		return sharedPreferences.getString(ID, "");
	}
	
	public String getUserToken() {
		return sharedPreferences.getString(TOKEN, "");
	}

	public void saveUser(String id, String token) {
		Editor editor = sharedPreferences.edit();
		editor.putString(ID, id);
		editor.putString(TOKEN, token);
		editor.commit();
	}
}