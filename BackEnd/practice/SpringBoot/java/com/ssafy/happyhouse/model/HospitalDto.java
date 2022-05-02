package com.ssafy.happyhouse.model;

public class HospitalDto {
	int hospitalNo;
	String hospitalName;
	String telno;
	String lat;
	String lng;
	String addr;
	String dongCode;
	String ratPsblYn;

	public int getHospitalNo() {
		return hospitalNo;
	}

	public void setHospitalNo(int hospitalNo) {
		this.hospitalNo = hospitalNo;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public String getRatPsblYn() {
		return ratPsblYn;
	}

	public void setRatPsblYn(String ratPsblYn) {
		this.ratPsblYn = ratPsblYn;
	}

	@Override
	public String toString() {
		return "HospitalDto [hospitalNo=" + hospitalNo + ", hospitalName=" + hospitalName + ", telno=" + telno
				+ ", lat=" + lat + ", lng=" + lng + ", addr=" + addr + ", dongCode=" + dongCode + ", ratPsblYn="
				+ ratPsblYn + "]";
	}

}
