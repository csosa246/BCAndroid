package com.bluecast.adapters;

import java.util.ArrayList;
import java.util.Collection;

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
import com.bluecast.models.Person;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.radiusnetworks.ibeacon.IBeacon;

public class ScanPeopleListAdapter extends BaseAdapter {
	private final Activity context;
	ArrayList<Person> personArray;
	Typeface typeface;
	Collection<IBeacon> beaconUpdateArray;

	static class ViewHolder {
		public TextView name;
		public TextView details;
		public ImageView imageView;
		public TextView distance;
		public TextView proximity;
	}

	public void setPersonArray(ArrayList<Person> personArray) {
		this.personArray = personArray;
		notifyDataSetChanged();
	}

	public void setBeaconUpdateArray(Collection<IBeacon> beaconUpdateArray) {
		for (int i = 0; i < personArray.size(); i++) {
			Person currentPerson = personArray.get(i);
			String currentMinor = currentPerson.getMinor();
			
			//Clear up array bleh ----
			currentPerson.setProximity(0);
			personArray.set(i, currentPerson);
			//------

			
			for (IBeacon beacon : beaconUpdateArray) {
				String minorFound = String.valueOf(beacon.getMinor());
				if (currentMinor.equals(minorFound)) {
					currentPerson.setProximity(beacon.getProximity());
					personArray.set(i, currentPerson);
				}

			}
		}
		
		notifyDataSetChanged();
	}

	public ScanPeopleListAdapter(Activity context) {
		this.context = context;
		personArray = new ArrayList<Person>();
		typeface = Typeface.createFromAsset(this.context.getAssets(),
				"fonts/Roboto-Light.ttf");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.rowlayout, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.profile_picture);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.profile_name);
			viewHolder.details = (TextView) convertView
					.findViewById(R.id.profile_details);
			viewHolder.distance = (TextView) convertView
					.findViewById(R.id.profile_distance);
			convertView.setTag(viewHolder);
			viewHolder.proximity = (TextView) convertView.findViewById(R.id.profile_accuracy);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

//		convertView.setBackgroundResource(R.drawable.container_dropshadow);
		
		
//		Toast.makeText(context, personArray.get(position).getFullName(), Toast.LENGTH_LONG).show();


		viewHolder.name.setText(personArray.get(position).getFullName());
		viewHolder.details.setText(personArray.get(position).getHeadline());
		viewHolder.distance.setText(personArray.get(position).getDistance());
		viewHolder.proximity.setText(personArray.get(position).getProximity());

		UrlImageViewHelper.setUrlDrawable(viewHolder.imageView, personArray
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
		viewHolder.proximity.setTypeface(typeface);
		return convertView;
	}

	public int selected;

	public void setSelected(int selected) {
		this.selected = selected;
	}

	@Override
	public int getCount() {

		return personArray.size();
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