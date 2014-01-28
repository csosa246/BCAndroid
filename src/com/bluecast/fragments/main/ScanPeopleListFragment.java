package com.bluecast.fragments.main;

import java.util.Collection;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluecast.adapters.ScanPeopleListAdapter;
import com.bluecast.adapters.UserSharedPreferencesAdapter;
import com.bluecast.async_tasks.ScanPeopleAsyncTask;
import com.bluecast.bluecast.MainActivity;
import com.bluecast.interfaces.ScanPeopleAsyncTaskDelegate;
import com.radiusnetworks.ibeacon.IBeacon;

public class ScanPeopleListFragment extends ListFragment implements
		OnRefreshListener, ScanPeopleAsyncTaskDelegate {
	private PullToRefreshLayout mPullToRefreshLayout;
	
	UserSharedPreferencesAdapter sharedPreferences;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) view;
		// As we're using a ListFragment we create a PullToRefreshLayout
		// manually
		mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());
		// We can now setup the PullToRefreshLayout
		ActionBarPullToRefresh
				.from(getActivity())
				.insertLayoutInto(viewGroup)
				.theseChildrenArePullable(android.R.id.list, android.R.id.empty)
				.listener(this).setup(mPullToRefreshLayout);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ScanPeopleListAdapter(getActivity()));
		setListShownNoAnimation(true);
		
	}
	

	@Override
	public void onRefreshStarted(View view) {
		setListShown(false);
		// Scan ibeacons
		notifyMainActivityToScanForIBeacons();
	}

	public void showText(String result) {
		Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
	}

	private void notifyMainActivityToScanForIBeacons() {
		if (getActivity() != null) {
			MainActivity fca = (MainActivity) getActivity();
			fca.shouldStartRangingForBeacons();
		}
	}

	public void didReceiveBeaconCollection(Collection<IBeacon> iBeacons) {
		showText("yeah we got them ibeacons");
		//Construct the arraylist to be fed into the ScanPeopleAsyncTask 
		ScanPeopleAsyncTask scanPeopleAsyncTask = new ScanPeopleAsyncTask(this,getActivity(), iBeacons);
		scanPeopleAsyncTask.execute();
	}

	public void noBeaconsRanged() {
		showText("no ibeacons in range");
		shouldResignRefresh();
	}

	@Override
	public void didReceiveResponse(String response) {
		showText(response);
		shouldResignRefresh();
	}

	public void shouldResignRefresh() {
		mPullToRefreshLayout.setRefreshComplete();
		if (getView() != null) {
			setListShown(true);
		}
	}
}