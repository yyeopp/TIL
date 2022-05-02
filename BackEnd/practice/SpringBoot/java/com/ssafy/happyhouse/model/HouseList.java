package com.ssafy.happyhouse.model;

public class HouseList {

	private int aptCode;
	private String aptName;
	private String lat;
	private String lng;
	
	public int getAptCode() {
		return aptCode;
	}
	public void setAptCode(int aptCode) {
		this.aptCode = aptCode;
	}
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	@Override
	public String toString() {
		return "HouseList [aptCode=" + aptCode + ", aptName=" + aptName + ", lat=" + lat + ", lng=" + lng + "]";
	}

}
