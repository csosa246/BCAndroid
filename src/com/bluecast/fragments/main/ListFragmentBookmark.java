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

import com.bluecast.adapters.ListAdapterBookmark;
import com.bluecast.async_tasks.AsyncGetBookmark;
import com.bluecast.bluecast.ActivityMain;
import com.bluecast.interfaces.DelegateAsyncGetBookmark;
import com.bluecast.models.ModelPerson;

public class ListFragmentBookmark extends ListFragment implements
		OnRefreshListener, DelegateAsyncGetBookmark {
	private PullToRefreshLayout mPullToRefreshLayout;
	ArrayList<ModelPerson> personArrayList; 

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

	ListAdapterBookmark bookmarkListAdapter; 
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bookmarkListAdapter = new ListAdapterBookmark(getActivity());
		setListAdapter(bookmarkListAdapter);
		setListShownNoAnimation(true);
		onRefreshStarted(this.getView());
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		((ActivityMain)getActivity()).shouldLoadPublicProfile(personArrayList.get(position));
	}

	@Override
	public void onRefreshStarted(View view) {
		setListShown(false);
		AsyncGetBookmark bookmarkPeopleAsyncTask = new AsyncGetBookmark(this, getActivity());
		bookmarkPeopleAsyncTask.execute();
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
	public void didFinishAddingBookmarks(ArrayList<ModelPerson> personArrayList) {
		this.personArrayList = personArrayList;			
		bookmarkListAdapter.setPersonArray(this.personArrayList);
		shouldResignRefresh();
	}
}