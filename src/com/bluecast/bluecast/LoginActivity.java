package com.bluecast.bluecast;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.bluecast.fragments.LinkedInLoginFragment;
import com.bluecast.fragments.LoginFragment;

public class LoginActivity extends FragmentActivity {

	FragmentManager contentFragmentManager;
	
	LoginFragment loginFragment;
	LinkedInLoginFragment linkedInLoginFragment; 
	
	boolean linkedInLoginFragmentVisible = false; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_content_frame);
		willSetupActionBar();
		willSetupFragments();
	}
	
	public void willSetupFragments(){ 
		
		contentFragmentManager = getFragmentManager();
		loginFragment = new LoginFragment();
		linkedInLoginFragment = new LinkedInLoginFragment();
		
		contentFragmentManager.beginTransaction()
		.replace(R.id.login_content_frame, loginFragment).commit();
	}
	
	public void didClickLogin(View view){
		showLinkedInLogin();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK & linkedInLoginFragmentVisible) {
			showMainLogin();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);

	}
	
	public void didConfirmLoginAndShouldProceedToMainActivity(){
		Intent myIntent = new Intent(this, MainActivity.class);
		startActivity(myIntent);
		finish();
	}
	
	public void willSetupActionBar(){ 
		getActionBar().hide();
	}
	
	public void showMainLogin(){
		linkedInLoginFragmentVisible = false; 
		
		contentFragmentManager.beginTransaction()
		.replace(R.id.login_content_frame, loginFragment).commit();
	}
	
	public void showLinkedInLogin(){
		linkedInLoginFragmentVisible = true;
		contentFragmentManager.beginTransaction()
		.replace(R.id.login_content_frame, linkedInLoginFragment).commit();
	}
	
	public void didCancelLinkedInLogin(){
		showMainLogin();
	}
	
	
	
}
