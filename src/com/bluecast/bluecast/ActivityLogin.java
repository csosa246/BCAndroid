package com.bluecast.bluecast;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.bluecast.adapters.AdapterSharedPreferences;
import com.bluecast.fragments.login.FragmentLogin;
import com.bluecast.fragments.login.FragmentLinkedInLogin;

public class ActivityLogin extends FragmentActivity {
	FragmentManager contentFragmentManager;
	FragmentLogin loginFragment;
	FragmentLinkedInLogin linkedInLoginFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_content_frame);
		willSetupActionBar();
		willSetupFragments();
	}

	public void willSetupActionBar() {
		getActionBar().hide();
	}

	public void willSetupFragments() {
		contentFragmentManager = getFragmentManager();
		loginFragment = new FragmentLogin();
		linkedInLoginFragment = new FragmentLinkedInLogin();
		// Replace fragment
		contentFragmentManager.beginTransaction()
				.replace(R.id.login_content_frame, loginFragment).commit();
	}

	public void didClickLogin(View view) {
		showLinkedInLogin();
	}

	public void didConfirmLoginAndShouldProceedToMainActivity() {
		Intent myIntent = new Intent(this, ActivityMain.class);
		startActivity(myIntent);
		finish();
	}

	public void shouldShowMainLogin() {
		contentFragmentManager.beginTransaction()
				.replace(R.id.login_content_frame, loginFragment).commit();
	}

	public void showLinkedInLogin() {
		contentFragmentManager.beginTransaction()
				.replace(R.id.login_content_frame, linkedInLoginFragment)
				.commit();
	}

	public void didCancelLinkedInLogin() {
		shouldShowMainLogin();
	}
	
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		AdapterSharedPreferences sharedPreferencesAdapter = new AdapterSharedPreferences(this);
		if(sharedPreferencesAdapter.getUserID()!=""){
//			Toast.makeText(getActivity(), "woop", Toast.LENGTH_LONG).show();
			didConfirmLoginAndShouldProceedToMainActivity();
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				& linkedInLoginFragment.isVisible()) {
			shouldShowMainLogin();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}
}