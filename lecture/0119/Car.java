package com.ssafy.classtest;

public class Car {
	String carName;
	String carColor;
	String maker;
	int speed;
	
	public Car() {	// �̷������� �޼��� ���� ����?�� ������ �ϴٴ� ��
//		carName = "�Ÿ";
//		carColor = "���";
//		maker = "����";
//		new Car("�Ÿ, ���, ����");	// ���ο� ��ü�� ���� �����ع����ϱ� ����
		this("�Ÿ", "���", "����"); 	// Ŭ���� �� �����ڸ޼��� ȣ��.
		speed = 10;
	}

	public Car(String carName) {
//		Car car = new Car();
//		car.carName = carName;
//		this.carName = carName;
//		carColor = "���";
//		maker = "����";
		this(carName, "���", "����");	// this()�� �����ڸ޼��� ������ ù�ٿ����� �� �� �ִ�. ������ ���°� �Ұ����ϴٴ� ��
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
