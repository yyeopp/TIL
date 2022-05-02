package com.ssafy.happyhouse.model;

public class HospitalInfoDto {
	String hospitalName;
	String telno;
	String addr;
	String ratPsblYn;

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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getRatPsblYn() {
		return ratPsblYn;
	}

	public void setRatPsblYn(String ratPsblYn) {
		this.ratPsblYn = ratPsblYn;
	}

}
