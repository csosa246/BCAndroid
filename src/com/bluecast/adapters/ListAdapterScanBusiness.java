package com.bluecast.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluecast.bluecast.R;
import com.bluecast.models.Business;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class ListAdapterScanBusiness extends BaseAdapter {
	private final Activity context;
	ArrayList<Business> businessArray;
	Typeface typeface;

	static class ViewHolder {
		public TextView name;
		public TextView details;
		public ImageView imageView;
		public TextView distance;
	}

	public ListAdapterScanBusiness(Activity context) {
		this.context = context;
		businessArray = new ArrayList<Business>();
		typeface = Typeface.createFromAsset(this.context.getAssets(),"fonts/Roboto-Light.ttf");
	}
	
	public void setBusinessArray(ArrayList<Business> businessArray) {
		this.businessArray = businessArray;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.rowlayout, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.profile_picture);
			viewHolder.name = (TextView) convertView.findViewById(R.id.profile_name);
			viewHolder.details = (TextView) convertView.findViewById(R.id.profile_details);
			viewHolder.distance = (TextView) convertView.findViewById(R.id.profile_distance);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.name.setText(businessArray.get(position).getCompany());
		viewHolder.details.setText(businessArray.get(position).getDescription());
		viewHolder.distance.setText("");

		UrlImageViewHelper.setUrlDrawable(viewHolder.imageView, businessArray
				.get(position).getPictureURL(), R.drawable.loading,
				new UrlImageViewCallback() {
					@Override
					public void onLoaded(ImageView imageView,
							Bitmap loadedBitmap, String url,
							boolean loadedFromCache) {
						if (!loadedFromCache) {
							ScaleAnimation scale = new ScaleAnimation(0, 1, 0,
									1, ScaleAnimation.RELATIVE_TO_SELF, .5f,
									ScaleAnimation.RELATIVE_TO_SELF, .5f);
							scale.setDuration(300);
							scale.setInterpolator(new OvershootInterpolator());
							imageView.startAnimation(scale);
						}
					}
				});

		// Setting typeface
		viewHolder.name.setTypeface(typeface);
		viewHolder.distance.setTypeface(typeface);
		viewHolder.details.setTypeface(typeface);
		return convertView;
	}

	@Override
	public int getCount() {
		return businessArray.size();
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