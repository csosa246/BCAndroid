package com.bluecast.fragments.main;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bluecast.adapters.ListAdapterScanPeople;
import com.bluecast.async_tasks.AsyncScanPeople;
import com.bluecast.bluecast.ActivityMain;
import com.bluecast.interfaces.DelegateAsyncScanPeople;
import com.bluecast.models.ModelPerson;
import com.radiusnetworks.ibeacon.IBeacon;

public class ListFragmentScanPeople extends ListFragment implements
		OnRefreshListener, DelegateAsyncScanPeople {
	private PullToRefreshLayout mPullToRefreshLayout;
	Collection<IBeacon> iBeaconCollection;
	ArrayList<ModelPerson> personArrayList; 
//	MainFragmentDelegate mainFragmentDelegate;
//	MainActivity mainActivity; 
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        mainActivity = (MainActivity) activity; 
//        try {
//        	mainFragmentDelegate = (MainFragmentDelegate) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnHeadlineSelectedListener");
//        }
//    }

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

	ListAdapterScanPeople scanPeopleListAdapter; 
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		scanPeopleListAdapter = new ListAdapterScanPeople(getActivity());
		setListAdapter(scanPeopleListAdapter);
		setListShownNoAnimation(true);
		onRefreshStarted(this.getView());
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		((ActivityMain)getActivity()).shouldLoadPublicProfile(personArrayList.get(position));
	}
	
	public void shouldUpdateProximity(Collection<IBeacon> iBeaconUpdateArray){
		scanPeopleListAdapter.setBeaconUpdateArray(iBeaconUpdateArray);
	}

	@Override
	public void onRefreshStarted(View view) {
		setListShown(false);
		iBeaconCollection = ((ActivityMain)getActivity()).getiBeaconCollection();
		if(iBeaconCollection.size()>0){
			AsyncScanPeople scanPeopleAsyncTask = new AsyncScanPeople(this, getActivity(), iBeaconCollection);
			scanPeopleAsyncTask.execute();
		}else{
			shouldResignRefresh();
		}
	}

	public void showText(String result) {
		Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void didFinishIdentifyingBeacons(ArrayList<ModelPerson> personArrayList) {
		this.personArrayList = personArrayList;		
		scanPeopleListAdapter.setPersonArray(this.personArrayList);
		shouldResignRefresh();
	}

	public void shouldResignRefresh() {
		mPullToRefreshLayout.setRefreshComplete();
		if (getView() != null) {
			setListShown(true);
		}
	}
}