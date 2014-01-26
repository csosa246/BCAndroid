package com.bluecast.adapters;

import java.util.ArrayList;

import com.bluecast.bluecast.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScanPeopleAdapter extends BaseAdapter {
	private final Activity context;
	ArrayList<String> subwayModel;
	Typeface typeface;

	static class ViewHolder {
		public TextView textViewTrainName;
		public TextView textViewTrainStatus;
		public TextView textViewTrainColor;
	}

	public ScanPeopleAdapter(Activity context) {
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
			viewHolder.textViewTrainName = (TextView) convertView
					.findViewById(R.id.textview_train_name);
			viewHolder.textViewTrainStatus = (TextView) convertView
					.findViewById(R.id.textview_train_status);
//			viewHolder.textViewTrainColor = (TextView) convertView
//					.findViewById(R.id.textview_train_color);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		// Color of train
		viewHolder.textViewTrainName.setTypeface(typeface);
		viewHolder.textViewTrainStatus.setTypeface(typeface);

		viewHolder.textViewTrainName.setText(subwayModel.get(position).toString());
//		viewHolder.textViewTrainName.setBackgroundColor((subwayModel
//				.get(position).getTextNameColor()));
//
//		viewHolder.textViewTrainName.setTextColor((subwayModel.get(position)
//				.getTextLineColor()));
//
//		viewHolder.textViewTrainStatus.setText(subwayModel.get(position)
//				.getStatus());
//				
//		viewHolder.textViewTrainColor.setBackgroundColor(subwayModel.get(
//				position).getTextStatusColor());
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