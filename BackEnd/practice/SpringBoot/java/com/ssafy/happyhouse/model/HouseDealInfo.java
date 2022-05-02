package com.ssafy.happyhouse.model;

public class HouseDealInfo {

	private String aptName;
	private String dongName;
	private int buildYear;
	private String jibun;
	private String dealAmount;
	private String dealYear;
	private String dealMonth;
	
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	private String dealDay;
	private String area;
	private String floor;
	
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public int getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}

	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getDealYear() {
		return dealYear;
	}
	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}
	public String getDealMonth() {
		return dealMonth;
	}
	public void setDealMonth(String dealMonth) {
		this.dealMonth = dealMonth;
	}
	public String getDealDay() {
		return dealDay;
	}
	public void setDealDay(String dealDay) {
		this.dealDay = dealDay;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	@Override
	public String toString() {
		return "HouseDealInfo [aptName=" + aptName + ", buildYear=" + buildYear + ", jibun="
				+ jibun + ", dealAmount=" + dealAmount + ", dealYear=" + dealYear + ", dealMonth=" + dealMonth
				+ ", dealDay=" + dealDay + ", area=" + area + ", floor=" + floor + "]";
	}
	
	

	
}
