package com.bluecast.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bluecast.bluecast.R;

public class ScanPeopleListAdapter extends BaseAdapter {
	private final Activity context;
	ArrayList<String> subwayModel;
	Typeface typeface;

	static class ViewHolder {
		public TextView name;
	}

	public ScanPeopleListAdapter(Activity context) {
		this.context = context;
		subwayModel = new ArrayList<String>(); 
		subwayModel.add("TEST");
		subwayModel.add("UEASDF");
		subwayModel.add("SDFSDFSDF");
//		typeface = Typeface.createFromAsset(context.getAssets(),
//				"fonts/Roboto-Light.ttf");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.rowlayout, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.profile_name);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Color of train
//		viewHolder.textViewTrainName.setTypeface(typeface);
//		viewHolder.textViewTrainStatus.setTypeface(typeface);

//		viewHolder.name.setText(subwayModel.get(position).toString());

		return convertView;
	}
	
	public int selected; 
	
	public void setSelected(int selected){ 
		this.selected = selected;
	}

	@Override
	public int getCount() {
		return subwayModel.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}