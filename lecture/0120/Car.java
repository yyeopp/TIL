package com.ssafy.inheritance;

public class Car {
	private String carName; // 전역변수에 대한 보호.
	private String carColor; // 함부로 바꾸지 못하게..
	private String maker; // 접근을 위해 getter와 setter 메서드를 만들자
	private int speed; // alt+ S+R+A+R. generate하는 것

	public String getCarName() {
		return carName;
	}

	public String getCarColor() {
		return carColor;
	}

	public String getMaker() {
		return maker;
	}

	public int getSpeed() {
		return speed;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Car() {
		this("쏘나타", "흰색", "현대");
		System.out.println("Car() call");
		
	}

	public Car(String carName) {
		this(carName, "흰색", "현대");
	}

	public Car(String carName, String carColor) {
		this(carName, carColor, "현대");
	}

	public Car(String carName, String carColor, String maker) {
		this.carName = carName;
		this.carColor = carColor;
		this.maker = maker;
	}

	public String info() {
		return carName + "\t" + carColor + "\t" + maker;
	}

	
	
	@Override
	public String toString() {
		return "Car [carName=" + carName + ", carColor=" + carColor + ", maker=" + maker + ", speed=" + speed + "]";
	}

	
	
	@Override
	public boolean equals(Object obj) { // lang의 equals 메서드에 대한 오버라이딩 작업. 객체의 주소값이 아닌 carName 변수를 비교하게 만드는.
		if (obj != null && obj instanceof Car) { // 예외처리 부분. 런타임 에러를 방지한다. -- 문법적으로 오류는 없으니까 IDE가 잡지 못하는데, 막상 돌릴 때 발생할 수
													// 있는 에러.
			Car car = (Car) obj; // 형변환을 해줘야. Object 타입과 Car 타입은 명백히 다르기 때문
			if (carName == car.carName) // 이게 true로 출력이 됨. 왜?
				return true;
			// String 클래스는 reference 치고는 특이한 성질. primitive의 성질도 함께 가진다. 그래서 가끔 되는것..
		} // equals를 쓰는게 가장 안전하다.
		return false;

	}
	void speedUp() {
		speed += 10;
//		if (speed >150)		// 택시를 위해 제한을 걸었는데, Car를 상속받는 모두가 제약에 걸릴 위험
//			speed = 150;	// 경주용 자동차나 자가용은 이러면 안됨
	}

	void speedDown() {
		speed -= 10;
		if (speed <= 0) {
			speed = 0;
		}
	}

	void stop() {
		speed = 0;
	}
}
