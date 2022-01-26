package com.company.abstractTest;

public class Circle extends Shape{
	private int radius;
	
	public Circle() {
		this(3);
	}

	public Circle(int radius) {
		this.radius = radius;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public double calcArea() {
		return radius*radius*Math.PI;		// Math 클래스의 필드값(상수, final로 지정됨)에 해당하는 PI. static double로 정의되어 있기 때문에 객체 만들 필요도 없다.
	}

	@Override
	public double calcRound() {
		return 2*Math.PI*radius;		// 마찬가지로 제곱할때 Math.pow() 활용해보기
	}
	
}
