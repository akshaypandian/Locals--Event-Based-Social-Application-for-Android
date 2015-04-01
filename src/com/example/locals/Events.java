/*Final Project
 * 
 * Events.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;
/*
 * Class to hold event related data
 */
public class Events {
	String latitude;
	String longitude;
	String title;
	String venueName;
	String date;
	String venueAddress;
	String performers;
	String description;
	String city;
	String region;
	String url;
	String image;


	public Events() {

	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getVenueName() {
		return venueName;
	}


	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getVenueAddress() {
		return venueAddress;
	}


	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}


	public String getPerformers() {
		return performers;
	}


	public void setPerformers(String performers) {
		this.performers = performers;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Events(String latitude, String longitude, String title,
			String venueName, String date, String venueAddress,
			String performers, String description, String city, String region,
			String url, String image) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.title = title;
		this.venueName = venueName;
		this.date = date;
		this.venueAddress = venueAddress;
		this.performers = performers;
		this.description = description;
		this.city = city;
		this.region = region;
		this.url = url;
		this.image = image;
	}


	@Override
	public String toString() {
		return "Events [latitude=" + latitude + ", longitude=" + longitude
				+ ", title=" + title + ", venueName=" + venueName + ", date="
				+ date + ", venueAddress=" + venueAddress + ", performers="
				+ performers + ", description=" + description + ", city="
				+ city + ", region=" + region + ", url=" + url + ", image="
				+ image + "]";
	}

	
}