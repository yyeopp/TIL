package com.company.abstractTest;

public class MathTest {
	public static void main(String[] args) {
		Rect rect1 = new Rect();
		System.out.println("가로가 " + rect1.getWidth() + "이고 세로가 " + rect1.getHeight() + "인 사각형의 넓이는 " + rect1.calcArea()
				+ "이고 둘레는 " + rect1.calcRound());

		Rect rect2 = new Rect(5, 6);
		System.out.println("가로가 " + rect2.getWidth() + "이고 세로가 " + rect2.getHeight() + "인 사각형의 넓이는 " + rect2.calcArea()
				+ "이고 둘레는 " + rect2.calcRound());
		Circle cir1 = new Circle();
		System.out.println("반지름이 " + cir1.getRadius() + "인 원의 넓이는 " + cir1.calcArea() + "이고 둘레는 " + cir1.calcRound());
		Circle cir2 = new Circle(4);
		System.out.println("반지름이 " + cir2.getRadius() + "인 원의 넓이는 " + cir2.calcArea() + "이고 둘레는 " + cir2.calcRound());
	
//		Shape d1 = new Shape();		// abstract라서 생성 불가
		Shape d1 = new Rect(5,2);	// 다형성을 통해 생성 가능. object - shape - rect 순서로 생성되고 rect를 참조함
		
		
//		System.out.println("가로가 " +d1.getWidth() + "이고 세로가 " + d1.getHeight() + "인 사각형의 넓이는 " +d1.calcArea()			// d1은 어디까지나 Shape형이고, shape 클래스는 width와 height를 속성으로 가지고 있지 않아서 에러가뜬다
//		+ "이고 둘레는 " +d1.calcRound());		
		System.out.println("도형의 넓이는 " +d1.calcArea()	+ "이고 둘레는 " +d1.calcRound());		// 대신 동적바인딩에 의해 오버라이딩된 메서드는 실행 가능
		
		if (d1 instanceof Rect) {		// 이걸로 메모리에 Rect가 존재하는지를 확인. 확인
			System.out.println("사각형입니다");
			Rect r1 = (Rect) d1;		// r1에 d1을 참조. d1은 명시적 형변환이 요구됨
			System.out.println("가로가 " + r1.getWidth() + "이고 세로가 " + r1.getHeight() + "인 사각형의 넓이는 " + r1.calcArea()
			+ "이고 둘레는 " + r1.calcRound());		// 비로소 정상적으로 Rect의 메서드를 활용할 수 있음.
		}	
		
//		Circle c1 = (Circle) d1;		// 문법적 오류는 없다. Circle < Shape이기 때문. 근데 ClassCastException 런타임오류가 발생 = 메모리 상에서는 Circle 객체가 만들어진 바 없기 때문
	}
}
