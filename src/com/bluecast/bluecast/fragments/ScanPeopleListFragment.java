package com.bluecast.bluecast.fragments;


import com.bluecast.adapters.ScanPeopleAdapter;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

	public class ScanPeopleListFragment extends ListFragment implements
			OnRefreshListener {

		private String[] ITEMS = { "Abbaye de Belloc",
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
//			setListAdapter(new ArrayAdapter<String>(getActivity(),
//					android.R.layout.simple_list_item_1, ITEMS));
			setListAdapter(new ScanPeopleAdapter(getActivity()));
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