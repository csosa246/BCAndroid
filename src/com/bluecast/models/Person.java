package com.bluecast.models;

public class Person {
	String lastName, linkedInID, firstName, distance, pictureURL;

	public Person(String lastName, String firstName, String distance,
			String pictureURL) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.distance = distance;
		this.pictureURL = pictureURL;
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

	public String getLinkedInID() {
		return linkedInID;
	}

	public String getPictureURL() {
		return pictureURL;
	}
	
	public String getFullName(){
		return firstName + " " + lastName;
	}
}
