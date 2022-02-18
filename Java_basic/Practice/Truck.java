package com;

public class Truck extends Car {

	private static final long serialVersionUID = 1L;
	private int weight;

	public Truck(int carNum, String carName, int weight) {
//		this.carNum = carNum; 	// error

		// 해결법 1
//		setCarNum(carNum);
//		setCarName(carName);
//		this.weight = weight;

		// 해결법 2
		super(carNum, carName); // 상위 클래스의 생성자를 소환.

//		super();		// 이렇게 쓰면 안된다. 상위클래스 생성자를 그대로 소환하기 때문에 carNum에 0 들어간다.
		this.weight = weight; // this()와 super()는 무조건 가장 위에 있어야 한다. 둘 중 하나만 가능하다는 뜻

	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
