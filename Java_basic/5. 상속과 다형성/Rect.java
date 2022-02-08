package com.company.ws01.step5;

public class Rect {
	
	double width;
	double length;
	public Rect(double d, double e) {
		this.width = d;
		this.length = e;
	}
	public double getSpace() {
		double space;
		return space = width*length;
	}
	public double getArea() {
		double area;
		return area = 2*(width+length);
	}
	
	

}
