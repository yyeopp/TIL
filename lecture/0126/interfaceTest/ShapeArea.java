package com.company.interfaceTest;

public interface ShapeArea {  		// Shape 클래스로부터 상속받는 대신, 인터페이스로 두 개의 메서드를 각자 구현해놓으면 각 도형에서 원하는 기능을 끌어다가 쓸 수 있다
	public abstract double calcArea();	
}
