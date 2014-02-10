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

import com.bluecast.adapters.SharedPreferencesAdapter;
import com.bluecast.fragments.main.BookmarkListFragment;
import com.bluecast.fragments.main.MainLeftMenuFragment;
import com.bluecast.fragments.main.PublicProfileFragment;
import com.bluecast.fragments.main.ScanBusinessFragment;
import com.bluecast.fragments.main.ScanPeopleListFragment;
import com.bluecast.fragments.main.SettingsRightMenuFragment;
import com.bluecast.models.Person;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

public class MainActivity extends BaseActivity implements IBeaconConsumer {
	// private Fragment mContent;
	private IBeaconManager iBeaconManager = IBeaconManager
			.getInstanceForApplication(this);

	public ScanPeopleListFragment fragmentScanPeople;
	MainLeftMenuFragment fragmentLeftMenu;
	ScanBusinessFragment fragmentScanBusiness;
	BookmarkListFragment fragmentBookmarks;
	SettingsRightMenuFragment fragmentSettings;
	PublicProfileFragment fragmentPublicProfile;
	FragmentManager contentFragmentManager;

	public MainActivity() {
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

		// mContent = new BeaconIndividualScanFragment();
		// mContent = new RefreshListFragment();

		contentFragmentManager = getFragmentManager();
		fragmentScanPeople = new ScanPeopleListFragment();
		fragmentLeftMenu = new MainLeftMenuFragment();
		fragmentScanBusiness = new ScanBusinessFragment();
		fragmentBookmarks = new BookmarkListFragment();
		fragmentSettings = new SettingsRightMenuFragment();
		fragmentPublicProfile = new PublicProfileFragment();

		// set the Above View
		setContentView(R.layout.content_frame);
		switchFragment(0);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, fragmentLeftMenu).commit();

		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, fragmentSettings).commit();
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
	int currentPosition;

	public void switchFragment(int position) {
		switch (position) {
		case 0:
			contentFragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragmentScanPeople).commit();
			break;
		case 1:
			contentFragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragmentScanBusiness).commit();
			break;
		case 2:
			contentFragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragmentBookmarks).commit();
			break;
		case 3:
			break;
		case 4:
			shouldLogout();
			break;
		}
		getSlidingMenu().showContent();
	}

	public void shouldLoadPublicProfile(Person person) {
		fragmentPublicProfile.setProperties(person);
		contentFragmentManager.beginTransaction().hide(fragmentScanPeople)
				.commit();
		contentFragmentManager.beginTransaction()
				.add(R.id.content_frame, fragmentPublicProfile).commit();
		contentFragmentManager.beginTransaction().show(fragmentPublicProfile)
				.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				& fragmentPublicProfile.isVisible()) {
			contentFragmentManager.beginTransaction()
					.hide(fragmentPublicProfile).commit();
			contentFragmentManager.beginTransaction().show(fragmentScanPeople)
					.commit();
			contentFragmentManager.beginTransaction()
					.remove(fragmentPublicProfile).commit();
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

	Collection<IBeacon> iBeaconCollection;

	Collection<IBeacon> latestIBeacons;
	@Override
	public void onIBeaconServiceConnect() {
		iBeaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(
					final Collection<IBeacon> iBeacons, Region region) {
				logToThread(iBeacons);
			}
		});
		
		try {
            iBeaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
	}

	String currentWorkingFragment;


	public void showText(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	String cwf = "";

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

	// public void delegateResponseToFragment(){
	// if(currentWorkingFragment.equals("scan_people")){
	// if(iBeaconCollection.size()>0){
	// fragmentScanPeople.didFindBeacons(iBeaconCollection);
	// }else{
	// fragmentScanPeople.didNotFindBeacons();
	// }
	// }
	//
	// if(currentWorkingFragment.equals("settings")){
	// if(iBeaconCollection.size()>0){
	// fragmentSettings.didFindBeacons(iBeaconCollection);
	// }else{
	// fragmentSettings.didNotFindBeacons();
	// }
	//
	// }
	// }

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
		SharedPreferencesAdapter sharedPreferencesAdapter = new SharedPreferencesAdapter(
				this);
		sharedPreferencesAdapter.saveUser("", "");
		
		Intent myIntent = new Intent(this, LoginActivity.class);
		startActivity(myIntent);
		finish();
	}

	ArrayList<Person> personArrayList;

//	@Override
//	public void setPersonArrayList(ArrayList<Person> personArrayList) {
//		this.personArrayList = personArrayList;
//	}

	public ArrayList<Person> getPersonArrayList() {
		return personArrayList;
	}
}