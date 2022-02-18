package com;

import java.io.Serializable;

public class Car implements Serializable, Comparable<Car> {
	// Serializable 인터페이스는 구현해야 할 메서드를 하나도 갖고 있지 않다. "implements" 한다고 해서 추가적으로 오버라이딩할 게 없다.
	// 그냥 직렬화가 필요한 객체에 달아만 주면, 직렬화가 가능하다는 일종의.. "자격증"이 발급된다.
	
	// Comparable 인터페이스를 구현해놔야, Collections.sort 메서드에 CarList를 넣었을 때 정상작동할 수 있다.
	
	private static final long serialVersionUID = 1L;
	private int carNum;
	private String carName; // 외부에서 쓸 수 없게 막음.

	// 디폴트 생성자는 굳이 만들지 않아도 알아서 생성되지만, 따로 생성자를 직접 만들었다면 사라진다.

	public Car() {

	}

	public Car(int carNum, String carName) { // 생성자 오버로딩
		this.carNum = carNum;
		this.carName = carName;
	}

	// getter와 setter 만들기
	public int getCarNum() {
		return carNum;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	@Override
	public String toString() {
		return "Car [carNum=" + carNum + ", carName=" + carName + "]";
	}

	@Override
	public int compareTo(Car o) {		// Comparable 인터페이스에서 오버라이딩해야 할 메서드
		return carName.compareTo(o.carName);	// String이 지원하고 있는 compareTo 메서드를 활용
	}

}
