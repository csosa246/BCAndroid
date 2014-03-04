package com.bluecast.fragments.main;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bluecast.adapters.ListAdapterScanBusiness;
import com.bluecast.async_tasks.AsyncGetBusiness;
import com.bluecast.bluecast.ActivityMain;
import com.bluecast.interfaces.DelegateAsyncGetBusiness;
import com.bluecast.models.Business;

public class ListFragmentScanBusiness extends ListFragment implements
		OnRefreshListener, DelegateAsyncGetBusiness {
	private PullToRefreshLayout mPullToRefreshLayout;
	private ArrayList<Business> businessArrayList; 
	private ListAdapterScanBusiness businessListAdapter; 

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) view;
		mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());
		ActionBarPullToRefresh.from(getActivity()).insertLayoutInto(viewGroup).theseChildrenArePullable(android.R.id.list, android.R.id.empty).listener(this).setup(mPullToRefreshLayout);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		businessListAdapter = new ListAdapterScanBusiness(getActivity());
		setListAdapter(businessListAdapter);
		setListShownNoAnimation(true);
		onRefreshStarted(this.getView());
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		((ActivityMain)getActivity()).shouldLoadCompanyProfile(this.businessArrayList.get(position));
	}

	@Override
	public void onRefreshStarted(View view) {
		setListShown(false);
		AsyncGetBusiness businessAsyncTask = new AsyncGetBusiness(this, getActivity());
		businessAsyncTask.execute();
	}
	
	public void showText(String result) {
		Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
	}

	public void shouldResignRefresh() {
		mPullToRefreshLayout.setRefreshComplete();
		if (getView() != null) {
			setListShown(true);
		}
	}
	
	@Override
	public void didFinishGettingBussiness(ArrayList<Business> businessArrayList) {		
		this.businessArrayList = businessArrayList;
		businessListAdapter.setBusinessArray(this.businessArrayList);
		shouldResignRefresh();
	}

//	@Override
//	public void didFinishAddingBookmarks(ArrayList<ModelPerson> personArrayList) {
//		this.personArrayList = personArrayList;			
//		bookmarkListAdapter.setPersonArray(this.personArrayList);
//		shouldResignRefresh();
//	}
}