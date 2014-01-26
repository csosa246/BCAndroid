package com.bluecast.bluecast;

import java.util.Collection;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.Toast;

import com.bluecast.bluecast.fragments.BookmarksFragment;
import com.bluecast.bluecast.fragments.ScanBusinessFragment;
import com.bluecast.bluecast.fragments.ScanPeopleListFragment;
import com.bluecast.bluecast.fragments.ColorMenuFragment;
import com.bluecast.bluecast.fragments.SettingsFragment;
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

	ScanPeopleListFragment refreshListFragment;
	ColorMenuFragment colorMenuFragment;
	SampleListFragment sampleListFragment;
	ScanBusinessFragment beaconBusinessScanFragment;
	BookmarksFragment bookmarksFragment;
	SettingsFragment settingsFragment;
	FragmentManager contentFragmentManager;

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

		refreshListFragment = new ScanPeopleListFragment();
		colorMenuFragment = new ColorMenuFragment();
		sampleListFragment = new SampleListFragment();
		beaconBusinessScanFragment = new ScanBusinessFragment();
		bookmarksFragment = new BookmarksFragment();
		settingsFragment = new SettingsFragment();

		// set the Above View
		setContentView(R.layout.content_frame);
		contentFragmentManager.beginTransaction()
				.replace(R.id.content_frame, refreshListFragment).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, colorMenuFragment).commit();

		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, settingsFragment).commit();
		// customize the SlidingMenu
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		iBeaconManager.bind(this);

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
			contentFragmentManager.beginTransaction()
					.replace(R.id.content_frame, refreshListFragment).commit();
			break;
		case 1:
			contentFragmentManager.beginTransaction()
					.replace(R.id.content_frame, beaconBusinessScanFragment)
					.commit();
			break;
		case 2:
			contentFragmentManager.beginTransaction()
					.replace(R.id.content_frame, bookmarksFragment).commit();
			break;
		case 3:
			break;
		case 4:
			break;
		}
		getSlidingMenu().showContent();
	}
	
	public MainActivity() {
		super(R.string.changing_fragments);
	}

	@Override
	public void onIBeaconServiceConnect() {
		iBeaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons,
					Region region) {
				
					
				
				if (iBeacons.size() > 0) {

						
					try {
						iBeaconManager.stopRangingBeaconsInRegion(new Region(
								"myRangingUniqueId", null, null, null));
					} catch (RemoteException e) {

					}
//					logToDisplay("The first iBeacon I see is about "
//					+ iBeacons.iterator().next().getAccuracy()
//					+ " meters away.");
					logToDisplay("GOT EM", iBeacons);
				}
			}

		});

		try {
			iBeaconManager.startRangingBeaconsInRegion(new Region(
					"myRangingUniqueId", null, null, null));
		} catch (RemoteException e) {

		}
	}

	public void showText(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	private void logToDisplay(final String line,final Collection<IBeacon> iBeacons) {
		runOnUiThread(new Runnable() {
			public void run() {
//				showText(line);
				refreshListFragment.didReceiveBeaconCollection(iBeacons);

			}
		});

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
}
