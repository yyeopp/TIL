package com.ssafy.inheritance;

public class Car {
	private String carName; // ���������� ���� ��ȣ.
	private String carColor; // �Ժη� �ٲ��� ���ϰ�..
	private String maker; // ������ ���� getter�� setter �޼��带 ������
	private int speed; // alt+ S+R+A+R. generate�ϴ� ��

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
		this("�Ÿ", "���", "����");
		System.out.println("Car() call");
		
	}

	public Car(String carName) {
		this(carName, "���", "����");
	}

	public Car(String carName, String carColor) {
		this(carName, carColor, "����");
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
	public boolean equals(Object obj) { // lang�� equals �޼��忡 ���� �������̵� �۾�. ��ü�� �ּҰ��� �ƴ� carName ������ ���ϰ� �����.
		if (obj != null && obj instanceof Car) { // ����ó�� �κ�. ��Ÿ�� ������ �����Ѵ�. -- ���������� ������ �����ϱ� IDE�� ���� ���ϴµ�, ���� ���� �� �߻��� ��
													// �ִ� ����.
			Car car = (Car) obj; // ����ȯ�� �����. Object Ÿ�԰� Car Ÿ���� ����� �ٸ��� ����
			if (carName == car.carName) // �̰� true�� ����� ��. ��?
				return true;
			// String Ŭ������ reference ġ��� Ư���� ����. primitive�� ������ �Բ� ������. �׷��� ���� �Ǵ°�..
		} // equals�� ���°� ���� �����ϴ�.
		return false;

	}
	void speedUp() {
		speed += 10;
//		if (speed >150)		// �ýø� ���� ������ �ɾ��µ�, Car�� ��ӹ޴� ��ΰ� ���࿡ �ɸ� ����
//			speed = 150;	// ���ֿ� �ڵ����� �ڰ����� �̷��� �ȵ�
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
