package com.bluecast.adapters;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.os.RemoteException;
import android.util.Log;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

public class AdapterBeacon extends Activity implements IBeaconConsumer {

	private IBeaconManager iBeaconManager = IBeaconManager
			.getInstanceForApplication(this);
	
	Collection<IBeacon> iBeaconCollection;

	long endTime;
	
	public void overrideOnCreate(){
		iBeaconManager.bind(this);
		iBeaconCollection = new ArrayList<IBeacon>();
	}
	
	public void overrideDestroy(){
		iBeaconManager.unBind(this);
	}
	
	public void overridePause(){
		if (iBeaconManager.isBound(this))
			iBeaconManager.setBackgroundMode(this, true);
	}
	
	public void overrideResume(){
		endTime = System.currentTimeMillis() + 30000;

		if (iBeaconManager.isBound(this))
			iBeaconManager.setBackgroundMode(this, false);
	}
	
	@Override
	public void onIBeaconServiceConnect() {
		// TODO Auto-generated method stub
		try {
			iBeaconManager.startRangingBeaconsInRegion(new Region(
					"myRangingUniqueId", null, null, null));
		} catch (RemoteException e) {

		}
		iBeaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(
					final Collection<IBeacon> iBeacons, Region region) {
				logToThread(iBeacons);
			}

		});
	}
	
	public void logToThread(final Collection<IBeacon> iBeacons) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (endTime > System.currentTimeMillis()) {
					for (IBeacon beacon : iBeacons) {
						if (!iBeaconCollection.contains(beacon)) {
							iBeaconCollection.add(beacon);
						}
					}
				} else {
					// Clear the array
					iBeaconCollection.clear();
					endTime = System.currentTimeMillis() + 30000;
				}
				Log.e("TAG", String.valueOf(iBeaconCollection.size()));

			}
		});
	}

	public void getIBeaconCollection() {
//		if (iBeaconCollection.size() > 0) {
//			refreshListFragment.didFindBeacons(iBeaconCollection);
//		} else {
//			refreshListFragment.didNotFindBeacons();
//		}
	}
	
}
