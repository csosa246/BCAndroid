package com.bluecast.models;


public class ModelBusiness {
	String businessID, title, description, url, picture_url, user_id,
			created_at, updated_at, event_id;

	public ModelBusiness(String businesID, String title, String description,
			String url, String picture_url, String user_id, String created_at,
			String updated_at, String event_id) {
		// this.minor = minor;
		this.businessID = businesID;
		this.title = title;
		this.description = description;
		this.url = url;
		this.picture_url = picture_url;
		this.user_id = user_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.event_id = event_id;

	}

	public String getBusinessID() {
		return businessID;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getDescription() {
		return description;
	}

	public String getEvent_id() {
		return event_id;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public String getTitle() {
		return title;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public String getUrl() {
		return url;
	}

	public String getUser_id() {
		return user_id;
	}

}
