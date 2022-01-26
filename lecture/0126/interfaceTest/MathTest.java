package com.company.interfaceTest;

public class MathTest {
	public static void main(String[] args) {
		Rect rect1 = new Rect();
		System.out
				.println("가로가 " + rect1.getWidth() + "이고 세로가 " + rect1.getHeight() + "인 사각형의 넓이는 " + rect1.calcArea());

		Rect rect2 = new Rect(5, 6);
		System.out
				.println("가로가 " + rect2.getWidth() + "이고 세로가 " + rect2.getHeight() + "인 사각형의 넓이는 " + rect2.calcArea());
		Circle cir1 = new Circle();
		System.out.println("반지름이 " + cir1.getRadius() + "인 원의 넓이는 " + cir1.calcArea() + "이고 둘레는 " + cir1.calcRound());
		Circle cir2 = new Circle(4);
		System.out.println("반지름이 " + cir2.getRadius() + "인 원의 넓이는 " + cir2.calcArea() + "이고 둘레는 " + cir2.calcRound());

	}
}
