package com.bluecast.fragments.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluecast.adapters.ListAdapterJobsCompanyProfile;
import com.bluecast.bluecast.R;
import com.bluecast.models.Business;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class FragmentCompanyProfile extends Fragment {

	private Business business; 
	
	public void setBusiness(Business business) {
		this.business = business;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.companyprofile, null);
	}
	
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			willSetupTextViews();
			willSetupImageViews();
			willSetupListViews();
	}
	
	public void willSetupTextViews(){
		TextView textViewCompanyName = (TextView) getActivity().findViewById(R.id.textview_company_name);
		TextView textViewCompanyDescription = (TextView) getActivity().findViewById(R.id.textview_company_description);
		textViewCompanyName.setText(business.getCompany());
		textViewCompanyDescription.setText(business.getDescription());
	}
	
	public void willSetupImageViews(){
		ImageView imageViewCompanyProfilePic  = (ImageView) getActivity().findViewById(R.id.imageview_company_profile_pic);
		UrlImageViewHelper.setUrlDrawable(imageViewCompanyProfilePic, business.getPictureURL());
	}
	
	public void willSetupListViews(){
		Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_LONG).show();
		ListView listViewCompanyJobs = (ListView) getActivity().findViewById(R.id.listview_jobs_list);
		ListAdapterJobsCompanyProfile adapter = new ListAdapterJobsCompanyProfile(getActivity());
		adapter.setJobsArray(business.getJobsArray());
		listViewCompanyJobs.setAdapter(adapter);
	}
	
	//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt("mColorRes", mColorRes);
//	}
	
}
