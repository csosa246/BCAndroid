package com.bluecast.bluecast;

import java.util.Collection;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bluecast.bluecast.R;
import com.bluecast.bluecast.fragments.BeaconIndividualScanFragment;
import com.bluecast.bluecast.fragments.ColorMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

public class MainActivity extends BaseActivity implements IBeaconConsumer {

	// private Fragment mContent;
	
	//Middle 
	private RefreshListFragment refreshFragment; 
	
	//Left 
	private ColorMenuFragment colorMenuFragment; 
	//Right
	
	private BeaconIndividualScanFragment beaconIndividualScanFragment; 
	
	private FragmentManager fragmentManager; 
	
	

	private IBeaconManager iBeaconManager = IBeaconManager
			.getInstanceForApplication(this);

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
		refreshFragment = new RefreshListFragment();
		colorMenuFragment = new ColorMenuFragment();
		beaconIndividualScanFragment = new BeaconIndividualScanFragment();

		fragmentManager = getFragmentManager();
		
		//Set the Above View
		setContentView(R.layout.content_frame);
		
		fragmentManager.beginTransaction().replace(R.id.content_frame, refreshFragment).commit();
		
//		fragmentManager.beginTransaction().replace(R.id.content_frame, refreshFragment).commit();

		//Set the Left View
		setBehindContentView(R.layout.menu_frame);
		getFragmentManager().beginTransaction().replace(R.id.menu_frame, colorMenuFragment).commit();
		//Set the Right View 
		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_two, new SampleListFragment()).commit();

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
	
	public void switchFragmentPosition(int index){
		switch (index) {
		case 0:
			getFragmentManager().beginTransaction().replace(R.id.content_frame, refreshFragment).commit();
			break;
		case 1:
			getFragmentManager().beginTransaction().replace(R.id.content_frame, beaconIndividualScanFragment).commit();
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
		getSlidingMenu().showContent();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
//		 getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

//	public void switchContent(Fragment fragment) {
//		// mContent = fragment;
//		
//		getFragmentManager().beginTransaction().remove(refreshFragment).commit();
//
//		getSupportFragmentManager().beginTransaction()
//				.replace(R.id.content_frame, fragment).commit();
//		getSlidingMenu().showContent();
//	}

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
					
					
//					Iterator<IBeacon> ib = iBeacons.iterator();
//					while(ib.hasNext()){
//						ib.next().getAccuracy();
//					}
					
					logToDisplay("The first iBeacon I see is about "
							+ iBeacons.iterator().next().getAccuracy()
							+ " meters away.");
					try {
						iBeaconManager.stopRangingBeaconsInRegion(new Region(
								"myRangingUniqueId", null, null, null));
					} catch (RemoteException e) {

					}
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

	private void logToDisplay(final String line) {
		runOnUiThread(new Runnable() {
			public void run() {
				showText(line);
			}
		});

	}

	public static class RefreshListFragment extends ListFragment implements
			OnRefreshListener {

		private static String[] ITEMS = { "Abbaye de Belloc",
				"Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
				"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
				"Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler",
				"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
				"Abondance", "Ackawi", "Acorn", "Adelost",
				"Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
				"Aisy Cendre", "Allgauer Emmentaler" };

		private PullToRefreshLayout mPullToRefreshLayout;

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			ViewGroup viewGroup = (ViewGroup) view;

			// As we're using a ListFragment we create a PullToRefreshLayout
			// manually
			mPullToRefreshLayout = new PullToRefreshLayout(
					viewGroup.getContext());

			// We can now setup the PullToRefreshLayout
			ActionBarPullToRefresh
					.from(getActivity())
					// We need to insert the PullToRefreshLayout into the
					// Fragment's ViewGroup
					.insertLayoutInto(viewGroup)
					// Here we mark just the ListView and it's Empty View as
					// pullable
					.theseChildrenArePullable(android.R.id.list,
							android.R.id.empty).listener(this)
					.setup(mPullToRefreshLayout);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			// Set the List Adapter to display the sample items
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, ITEMS));
			setListShownNoAnimation(true);
		}

		@Override
		public void onRefreshStarted(View view) {
			// Hide the list
			setListShown(false);

			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					// Notify PullToRefreshLayout that the refresh has finished
					mPullToRefreshLayout.setRefreshComplete();

					if (getView() != null) {
						// Show the list again
						setListShown(true);
					}
				}
			}.execute();
		}
	}

}
