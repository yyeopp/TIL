package com.ssafy.classtest;

public class Car {
	String carName;
	String carColor;
	String maker;
	int speed;
	
	public Car() {	// 이런식으로 메서드 간의 소통?도 가능은 하다는 것
//		carName = "쏘나타";
//		carColor = "흰색";
//		maker = "현대";
//		new Car("쏘나타, 흰색, 현대");	// 새로운 객체를 만들어서 지정해버리니까 에러
		this("쏘나타", "흰색", "현대"); 	// 클래스 내 생성자메서드 호출.
		speed = 10;
	}

	public Car(String carName) {
//		Car car = new Car();
//		car.carName = carName;
//		this.carName = carName;
//		carColor = "흰색";
//		maker = "현대";
		this(carName, "흰색", "현대");	// this()는 생성자메서드 내에서 첫줄에서만 쓸 수 있다. 여러번 쓰는게 불가능하다는 뜻
	}
	
	public Car(String carName, String carColor, String maker) {
		this.carName = carName;
		this.carColor = carColor;
		this.maker = maker;
	}
	
	

	void speedUp() {
		speed += 10;
	}
	
	void speedDown() {
		speed -= 10;
		if (speed <= 0) {
			speed=0;
		}
	}
	
	void stop() {
		speed = 0;
	}
}
