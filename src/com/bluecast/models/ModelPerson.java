package com.bluecast.models;

import android.graphics.Color;

public class ModelPerson {
	String lastName, linkedInID, firstName, headline, distance, pictureURL,
			publicProfileURL, minor, major, rssi, proximity;
	int proximityColor; 

	public ModelPerson( String lastName, String firstName, String headline,
			String distance, String pictureURL, String publicProfileURL, String linkedInID) {
//		this.minor = minor;
		this.lastName = lastName;
		this.firstName = firstName;
		this.headline = headline;
		this.distance = distance;
		this.pictureURL = pictureURL;
		this.publicProfileURL = publicProfileURL;
		this.linkedInID = linkedInID;
	}
	
	public void setLinkedInID(String linkedInID) {
		this.linkedInID = linkedInID;
	}

	public void setProximity(int proximityValue) {
		if (proximityValue == 0) {
			this.proximity = "";
			this.proximityColor = Color.TRANSPARENT;
		} else if (proximityValue == 1) {
			this.proximity = "Proximity Immediate";
			this.proximityColor = Color.GREEN;
		} else if (proximityValue == 2) {
			this.proximity = "Proximity Near";
			this.proximityColor = Color.GRAY;
		} else if (proximityValue == 3) {
			this.proximity = "Proximity Far";
			this.proximityColor = Color.RED;
		}
	}
	
	public int getProximityColor() {
		return proximityColor;
	}
	
	public String getHeadline() {
		return headline;
	}

	public String getProximity() {
		return proximity;
	}

	public String getDistance() {
		if (distance.equals("1")) {
			return "1st";
		} else if (distance.equals("2")) {
			return "2nd";
		} else if (distance.equals("3")) {
			return "3rd";
		} else if (distance.equals("4")) {
			return "4th";
		} else if (distance.equals("5")) {
			return "5th";
		}
		return ">6th";
	}

	public void setRssi(String rssi) {
		this.rssi = rssi;
	}

	public String getRssi() {
		return rssi;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getMinor() {
		return minor;
	}

	public String getLinkedInID() {
		return linkedInID;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getPublicProfileURL() {
		return publicProfileURL;
	}
}
