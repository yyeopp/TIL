package com.company.interfaceTest;

public abstract class Shape {
	
	public abstract double calcArea();		// 하위 도형에서 이런 계산메서드를 사용하고자 할 때,
	
	public abstract double calcRound();	// 이렇게 상위클래스를 추상으로 선언하고 추상메서드로 통일된 계산메서드를 만들면 하위클래스가 이에 구속된다.

}		// 단, 추상메서드 만들 때 자료형을 잘 정해야한다. 여기서 int로 선언해버리면 밑에서 오버라이딩할 때 double같은게 구현이 안된다.
