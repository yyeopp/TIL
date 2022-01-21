package com.ssafy.inheritance;

public class Taxi extends Car {
	
	public Taxi() {		// �� �κ��� �ڵ����� �����Ǿ� �����ִ�.
		super();		// �����ڴ� ����� ���� �������� CarTest�� �� �۵��ϴ� ����
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
	public String toString() {		// Car�� toString�� �����ͼ� �ڿ� :Taxi�� ���̰� ���� ����
//		return "Car [carName=" + getCarName() + ", carColor=" + getCarColor() + ", maker=" + getMaker() + ", speed=" + getSpeed() + "] : Taxi";
//		return toString() +" : Taxi";	// this. �� �ڵ��������� ������ ��, �̰� �����Ű�� ���ѷ������ٰ� stack overflow��
		return super.toString() + " : Taxi";
	}

	@Override
	void speedUp() {
		super.speedUp();	// �̰� ���� Car�� ������ speed += 10
		if (getSpeed() >= 150)		// Speed ���� �� getter, setter ���
			setSpeed(150);		// �ýÿ� ����ȭ�� �������̵�
	}
	
}
