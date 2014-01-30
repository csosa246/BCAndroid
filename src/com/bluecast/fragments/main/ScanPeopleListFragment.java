package com.bluecast.fragments.main;

import java.util.Collection;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluecast.adapters.ScanPeopleListAdapter;
import com.bluecast.adapters.UserSharedPreferencesAdapter;
import com.bluecast.async_tasks.ScanPeopleAsyncTask;
import com.bluecast.interfaces.MainFragmentDelegate;
import com.bluecast.interfaces.ScanPeopleAsyncTaskDelegate;
import com.radiusnetworks.ibeacon.IBeacon;

public class ScanPeopleListFragment extends ListFragment implements
		OnRefreshListener, ScanPeopleAsyncTaskDelegate {
	private PullToRefreshLayout mPullToRefreshLayout;
	UserSharedPreferencesAdapter sharedPreferences;
	MainFragmentDelegate mainFragmentDelegate;
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
        	mainFragmentDelegate = (MainFragmentDelegate) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) view;
		mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());
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
		mainFragmentDelegate.shouldStartBeaconScan(4000);
	}

	public void showText(String result) {
		Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
	}
	
	Collection<IBeacon> iBeaconCollection;
	
	public void didFindBeacons(Collection<IBeacon> iBeaconCollection){
		this.iBeaconCollection = iBeaconCollection;
		ScanPeopleAsyncTask scanPeopleAsyncTask = new ScanPeopleAsyncTask(this, getActivity(), iBeaconCollection);
		scanPeopleAsyncTask.execute();
	}
	
	public void didNotFindBeacons(){
		shouldResignRefresh();
	}

	@Override
	public void didFinishIdentifyingBeacons(String response) {
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