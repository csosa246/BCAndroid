package com.bluecast.models;

import java.util.ArrayList;

public class Job {
	String title,position,term,description ;
	ArrayList<String> acceptableMajors; 
	public Job(String title, String position, String term, String description, ArrayList<String> acceptableMajors) {
		super();
		this.title = title;
		this.position = position;
		this.term = term;
		this.description = description;
		this.acceptableMajors = acceptableMajors;
	}

	public String getTitle() {
		return title;
	}

	public String getPosition() {
		return position;
	}

	public String getTerm() {
		return term;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ArrayList<String> getAcceptableMajors() {
		return acceptableMajors;
	}


	

}
