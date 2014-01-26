package com.bluecast.models;

public class Beacon {
	
	String uuid,minor,major;
	
	public Beacon(String uuid, String minor, String major){
		this.uuid = uuid; 
		this.minor = minor; 
		this.major = major; 
	}
	
	public String getMajor() {
		return major;
	}
	
	public String getMinor() {
		return minor;
	}
	
	public String getUuid() {
		return uuid;
	}
	
}
