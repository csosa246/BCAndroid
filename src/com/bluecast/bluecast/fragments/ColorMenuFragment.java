package com.bluecast.bluecast.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bluecast.bluecast.MainActivity;
import com.bluecast.bluecast.R;

public class ColorMenuFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] colors = getResources().getStringArray(R.array.color_names);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		setListAdapter(colorAdapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		switchFragment(position);
	}

	// the meat of switching the above fragment
	private void switchFragment(int position) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchFragment(position);
		} 
	}
}
