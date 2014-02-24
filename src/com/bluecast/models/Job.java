package com.bluecast.models;

public class Job {
	String title,position,term,description ;

	public Job(String title, String position, String term, String description) {
		super();
		this.title = title;
		this.position = position;
		this.term = term;
		this.description = description;
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


	

}
