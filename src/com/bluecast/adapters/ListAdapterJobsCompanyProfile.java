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
import com.bluecast.models.Job;

public class ListAdapterJobsCompanyProfile extends BaseAdapter {
	private final Activity context;
	ArrayList<Job> jobsArray;
	Typeface typeface;

	static class ViewHolder {
		public TextView title;
		public TextView position;
		public TextView term;
		public TextView description;
	}

	public ListAdapterJobsCompanyProfile(Activity context) {
		this.context = context;
		jobsArray = new ArrayList<Job>();
		typeface = Typeface.createFromAsset(this.context.getAssets(),"fonts/Roboto-Light.ttf");
	}
	
	public void setJobsArray(ArrayList<Job> jobsArray) {
		this.jobsArray = jobsArray;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.rowlayout_jobs, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.textview_job_name);
			viewHolder.description = (TextView) convertView.findViewById(R.id.textview_job_description);
			viewHolder.position = (TextView) convertView.findViewById(R.id.textview_job_position);
			viewHolder.term = (TextView) convertView.findViewById(R.id.textview_job_term);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.title.setText(jobsArray.get(position).getTitle());
		viewHolder.description.setText(jobsArray.get(position).getDescription());
		viewHolder.position.setText(jobsArray.get(position).getPosition());
		viewHolder.term.setText(jobsArray.get(position).getTerm());
		return convertView;
	}

	@Override
	public int getCount() {
		return jobsArray.size();
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