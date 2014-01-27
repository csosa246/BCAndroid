package com.bluecast.bluecast;

import com.bluecast.bluecast.fragments.LinkedInLoginFragment;
import com.bluecast.bluecast.fragments.LoginFragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

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
		goToMainActivity();
//		linkedInLoginFragmentVisible = true;
//		contentFragmentManager.beginTransaction()
//		.replace(R.id.login_content_frame, linkedInLoginFragment).commit();
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		
		if (keyCode == KeyEvent.KEYCODE_BACK & linkedInLoginFragmentVisible) {
			linkedInLoginFragmentVisible = false; 
			
			contentFragmentManager.beginTransaction()
			.replace(R.id.login_content_frame, loginFragment).commit();
			
			return true;
		}
		
		return super.onKeyDown(keyCode, event);

	}
	
	public void goToMainActivity(){
		Intent myIntent = new Intent(this, MainActivity.class);
		startActivity(myIntent);
	}
	
	
	
	public void willSetupActionBar(){ 
		getActionBar().hide();
	}
	
	
}
