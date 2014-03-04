package com.bluecast.bluecast;

import java.util.ArrayList;
import java.util.Collection;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bluecast.adapters.AdapterSharedPreferences;
import com.bluecast.fragments.main.FragmentCompanyProfile;
import com.bluecast.fragments.main.FragmentLeftMenu;
import com.bluecast.fragments.main.FragmentPublicProfile;
import com.bluecast.fragments.main.FragmentRightMenu;
import com.bluecast.fragments.main.ListFragmentBookmark;
import com.bluecast.fragments.main.ListFragmentScanBusiness;
import com.bluecast.fragments.main.ListFragmentScanPeople;
import com.bluecast.models.Business;
import com.bluecast.models.ModelPerson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

public class ActivityMain extends ActivityBase implements IBeaconConsumer {
	private IBeaconManager iBeaconManager = IBeaconManager.getInstanceForApplication(this);
	public ListFragmentScanPeople fragmentScanPeople;
	FragmentLeftMenu fragmentLeftMenu;
	ListFragmentScanBusiness fragmentScanBusiness;
	ListFragmentBookmark fragmentBookmarks;
	FragmentRightMenu fragmentSettings;
	FragmentPublicProfile fragmentPublicProfile;
	FragmentManager contentFragmentManager;
	FragmentCompanyProfile fragmentCompanyProfile; 

	public ActivityMain() {
		super(R.string.changing_fragments);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
		// set the Above View
		// if (savedInstanceState != null)
		// mContent =
		// getSupportFragmentManager().getFragment(savedInstanceState,
		// "mContent");
		// if (mContent == null)

		contentFragmentManager = getFragmentManager();
		fragmentScanPeople = new ListFragmentScanPeople();
		fragmentLeftMenu = new FragmentLeftMenu();
		fragmentScanBusiness = new ListFragmentScanBusiness();
		fragmentBookmarks = new ListFragmentBookmark();
		fragmentSettings = new FragmentRightMenu();
		fragmentPublicProfile = new FragmentPublicProfile();
		fragmentCompanyProfile = new FragmentCompanyProfile();
		// set the Above View
		setContentView(R.layout.content_frame);
		switchFragment(0);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, fragmentLeftMenu).commit();

		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getFragmentManager().beginTransaction().replace(R.id.menu_frame_two, fragmentSettings).commit();
		// customize the SlidingMenu
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		iBeaconManager.bind(this);
		iBeaconCollection = new ArrayList<IBeacon>();
	}

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// // getSupportFragmentManager().putFragment(outState, "mContent",
	// mContent);
	// }
	//
	// public void switchContent(Fragment fragment) {
	// // mContent = fragment;
	// getSupportFragmentManager().beginTransaction()
	// .replace(R.id.content_frame, fragment).commit();
	// getSlidingMenu().showContent();
	// }
	
	public void switchFragment(int position) {
		switch (position) {
		case 0:
			contentFragmentManager.beginTransaction().replace(R.id.content_frame, fragmentScanPeople).commit();
			break;
		case 1:
			contentFragmentManager.beginTransaction().replace(R.id.content_frame, fragmentScanBusiness).commit();
			break;
		case 2:
			contentFragmentManager.beginTransaction().replace(R.id.content_frame, fragmentBookmarks).commit();
			break;
		case 3:
			break;
		case 4:
			shouldLogout();
			break;
		}
		getSlidingMenu().showContent();
	}

	public void shouldLoadPublicProfile(ModelPerson person) {
		fragmentPublicProfile.setProperties(person);
		contentFragmentManager.beginTransaction().hide(fragmentScanPeople).commit();
		contentFragmentManager.beginTransaction().add(R.id.content_frame, fragmentPublicProfile).commit();
		contentFragmentManager.beginTransaction().show(fragmentPublicProfile).commit();
	}
	
	public void shouldLoadCompanyProfile(Business business){
		fragmentCompanyProfile.setBusiness(business);
		contentFragmentManager.beginTransaction().hide(fragmentScanBusiness).commit();
		contentFragmentManager.beginTransaction().add(R.id.content_frame, fragmentCompanyProfile).commit();
		contentFragmentManager.beginTransaction().show(fragmentCompanyProfile).commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK & fragmentPublicProfile.isVisible()) {
			contentFragmentManager.beginTransaction().hide(fragmentPublicProfile).commit();
			contentFragmentManager.beginTransaction().show(fragmentScanPeople).commit();
			contentFragmentManager.beginTransaction().remove(fragmentPublicProfile).commit();
			return false;
		}
		
		if (keyCode == KeyEvent.KEYCODE_BACK & fragmentCompanyProfile.isVisible()) {
			contentFragmentManager.beginTransaction().hide(fragmentCompanyProfile).commit();
			contentFragmentManager.beginTransaction().show(fragmentScanBusiness).commit();
			contentFragmentManager.beginTransaction().remove(fragmentCompanyProfile).commit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		iBeaconManager.unBind(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (iBeaconManager.isBound(this))
			iBeaconManager.setBackgroundMode(this, true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (iBeaconManager.isBound(this))
			iBeaconManager.setBackgroundMode(this, false);
	}

	Collection<IBeacon> iBeaconCollection,latestIBeacons;
	@Override
	public void onIBeaconServiceConnect() {
		iBeaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(final Collection<IBeacon> iBeacons, Region region) {
				logToThread(iBeacons);
			}
		});
		
		try {
            iBeaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
	}

	public void showText(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	public void logToThread(final Collection<IBeacon> iBeacons) {
		runOnUiThread(new Runnable() {
			public void run() {
				latestIBeacons = iBeacons;
				for (IBeacon beacon : iBeacons) {
					if (!iBeaconCollection.contains(beacon)) {
						iBeaconCollection.add(beacon);
					}
				}
				
				Log.e("TAG", String.valueOf(iBeaconCollection.size()));
				//Lets go ahead and update the distance 
				if(fragmentScanPeople.isVisible()){
					fragmentScanPeople.shouldUpdateProximity(latestIBeacons);
				}
			}
		});
	}
	
	public Collection<IBeacon> getiBeaconCollection() {
		return iBeaconCollection;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.github:
			getSlidingMenu().showSecondaryMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void shouldLogout() {
		// Destroy credentials
		AdapterSharedPreferences sharedPreferencesAdapter = new AdapterSharedPreferences(this);
		sharedPreferencesAdapter.saveUser("", "");
		Intent myIntent = new Intent(this, ActivityLogin.class);
		startActivity(myIntent);
		finish();
	}

	ArrayList<ModelPerson> personArrayList;

	public ArrayList<ModelPerson> getPersonArrayList() {
		return personArrayList;
	}
}