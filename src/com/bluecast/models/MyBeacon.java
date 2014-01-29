package com.bluecast.models;

import com.radiusnetworks.ibeacon.IBeacon;

public class MyBeacon extends IBeacon {
	
	String timeStamp;
	
	public void setTimeStamp() {
		this.timeStamp = String.valueOf(System.currentTimeMillis());
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	
	
	
}
