package com.bluecast.models;

public class Person {
	String lastName, linkedInID, firstName, details, distance, pictureURL,publicProfileURL;

	public Person(String lastName, String firstName, String distance,
			String pictureURL, String publicProfileURL) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.distance = distance;
		this.pictureURL = pictureURL;
		this.publicProfileURL = publicProfileURL;
	}

	public String getDistance() {
		if(distance.equals("1")){
			return "1st";
		}else if (distance.equals("2")){
			return "2nd";
		}else if (distance.equals("3")){
			return "3rd";
		}else if (distance.equals("4")){
			return "4th";
		}else if (distance.equals("5")){
			return "5th";
		}
		return ">6th";
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getDetails() {
		return details;
	}

	public String getLinkedInID() {
		return linkedInID;
	}

	public String getPictureURL() {
		return pictureURL;
	}
	
	public String getFullName(){
		return firstName + " " + lastName;
	}
	
	public String getPublicProfileURL() {
		return publicProfileURL;
	}
}
