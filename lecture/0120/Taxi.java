package com.ssafy.inheritance;

public class Taxi extends Car {
	
	public Taxi() {		// 이 부분은 자동으로 생성되어 숨어있다.
		super();		// 생성자는 상속이 되지 않음에도 CarTest가 잘 작동하는 이유
	}
	
	public Taxi (String carName, String carColor, String maker) {
		super(carName, carColor, maker);
//		this.carName = carName;
//		this.carColor = carColor;
//		this.maker = maker;
	}
	
	int price;
	
	int calcPrice(int km) {
		price = km*100 + 3800;
		return price;
	}
	@Override
	public String toString() {		// Car의 toString을 가져와서 뒤에 :Taxi를 붙이고 싶은 상태
//		return "Car [carName=" + getCarName() + ", carColor=" + getCarColor() + ", maker=" + getMaker() + ", speed=" + getSpeed() + "] : Taxi";
//		return toString() +" : Taxi";	// this. 이 자동생성됨을 감안할 때, 이거 실행시키면 무한루프돌다가 stack overflow뜸
		return super.toString() + " : Taxi";
	}

	@Override
	void speedUp() {
		super.speedUp();	// 이건 원래 Car가 가지던 speed += 10
		if (getSpeed() >= 150)		// Speed 접근 시 getter, setter 사용
			setSpeed(150);		// 택시에 최적화된 오버라이딩
	}
	
}
