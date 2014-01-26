package com.bluecast.bluecast.fragments;

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
			
			ScanPeopleAsyncTask scanPeopleAsyncTask = new ScanPeopleAsyncTask(new ScanPeopleAsyncTaskDelegate() {
				
				@Override
				public void didReceiveResponse(String response) {
					showText(response);
//					notifyMainActivityDone();
					
					mPullToRefreshLayout.setRefreshComplete();

					if (getView() != null) {
						// Show the list again
						setListShown(true);
					}
				}
			});
			scanPeopleAsyncTask.execute();

//			new AsyncTask<Void, Void, Void>() {
//
//				@Override
//				protected Void doInBackground(Void... params) {
//					try {
//						Thread.sleep(4000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					return null;
//				}
//
//				@Override
//				protected void onPostExecute(Void result) {
//					super.onPostExecute(result);
//					// Notify PullToRefreshLayout that the refresh has finished
//					notifyMainActivityDone();
//					
//					mPullToRefreshLayout.setRefreshComplete();
//
//					if (getView() != null) {
//						// Show the list again
//						setListShown(true);
//					}
//				}
//			}.execute();
		}
		
		public void showText(String result){
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}
		
//		// the meat of switching the above fragment
//		private void notifyMainActivityDone() {
//			if (getActivity() == null)
//				return;
//			
//			if (getActivity() instanceof MainActivity) {
//				MainActivity fca = (MainActivity) getActivity();
//				fca.refreshMenuDone();
//			} 
//		}
	}