package com.ssafy.happyhouse.model;

public class SchoolDto {
	private String schoolId;
	private String schoolNm;
	private String schoolType;
	private String schoolAddr;
	private String lat;
	private String lng;
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolNm() {
		return schoolNm;
	}
	public void setSchoolNm(String schoolNm) {
		this.schoolNm = schoolNm;
	}
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	public String getSchoolAddr() {
		return schoolAddr;
	}
	public void setSchoolAddr(String schoolAddr) {
		this.schoolAddr = schoolAddr;
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
}
