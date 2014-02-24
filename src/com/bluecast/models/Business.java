package com.bluecast.models;

import java.util.ArrayList;


public class Business {
	String  eventID, company, description, pictureURL, URL,jobsArray; 
	public Business(String eventID, String company, String description, String pictureURL, String URL,ArrayList<Job> jobsArray) {
		// this.minor = minor;
		this.eventID = eventID; 
		this.company = company; 
		this.description = description; 
		this.pictureURL = pictureURL; 
		this.URL = URL; 
	}
	
	public String getCompany() {
		return company;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getEventID() {
		return eventID;
	}
	
	public String getPictureURL() {
		return pictureURL;
	}
	
	public String getURL() {
		return URL;
	}
	
	public String getJobsArray() {
		return jobsArray;
	}

}