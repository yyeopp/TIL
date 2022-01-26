package com.company.abstractTest;

public class Rect extends Shape {		// 추상클래스를 상속받았다.

	private int width;
	private int height;
	
	public Rect() {
		this(4,3);
		
	}

	public Rect(int width, int height) {
		
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public double calcArea() {		// 추상메서드를 반드시 오버라이딩
		return width*height;
		
	}
	
	@Override
	public double calcRound() {
		return (width+height) * 2;
		
	}
}
