package com.bluecast.bluecast.fragments;

import java.util.Collection;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluecast.adapters.ScanPeopleListAdapter;
import com.bluecast.async_tasks.ScanPeopleAsyncTask;
import com.bluecast.bluecast.MainActivity;
import com.bluecast.interfaces.ScanPeopleAsyncTaskDelegate;
import com.radiusnetworks.ibeacon.IBeacon;

	public class ScanPeopleListFragment extends ListFragment implements
			OnRefreshListener {
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
//			setListAdapter(new ArrayAdapter<String>(getActivity(),
//					android.R.layout.simple_list_item_1, ITEMS));
			setListAdapter(new ScanPeopleListAdapter(getActivity()));
			setListShownNoAnimation(true);
		}

		@Override
		public void onRefreshStarted(View view) {
			// Hide the list
			setListShown(false);
			//Scan ibeacons 
			notifyMainActivityToScanForIBeacons();
		}
		
		public void showText(String result){
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}
		
		// the meat of switching the above fragment
		private void notifyMainActivityToScanForIBeacons() {
			if (getActivity() == null)
				return;
			
			if (getActivity() instanceof MainActivity) {
				MainActivity fca = (MainActivity) getActivity();
				fca.shouldStartRangingForBeacons();
			} 
		}
		
		public void didReceiveBeaconCollection(Collection<IBeacon> iBeacons){
			showText("yeah we got them ibeacons");
			
			ScanPeopleAsyncTask scanPeopleAsyncTask = new ScanPeopleAsyncTask(new ScanPeopleAsyncTaskDelegate() {
				
				@Override
				public void didReceiveResponse(String response) {
					showText(response);
					refreshComplete();
				}
			});
			scanPeopleAsyncTask.execute();
			
		}
		
		public void noBeaconsRanged(){
			showText("no ibeacons in range");
			refreshComplete();
		}
		
		public void refreshComplete(){
			mPullToRefreshLayout.setRefreshComplete();
			if (getView() != null) {
				// Show the list again
				setListShown(true);
			}	
		}
	}