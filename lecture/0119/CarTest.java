package com.ssafy.classtest;

public class CarTest {

	public static void main(String[] args) {
		Car car1 = new Car();
		System.out.println(car1.carName+"\t"+car1.carColor+"\t"+car1.maker);
//		car1.carName = "�Ÿ";
//		car1.carColor = "���";
//		car1.maker = "����";
//		System.out.println(car1.carName+"\t"+car1.carColor+"\t"+car1.maker);
		
		System.out.println("car1�� 3ȸ ����: " + car1.speed);
		for(int i=0; i<3 ; i++)
			car1.speedUp();
		System.out.println("���� �� car1�� �ӵ�: " + car1.speed);
		// �ݹ��� ���۷����� ���� Ȯ������
		
		System.out.println("car1�� 5ȸ ���� " + car1.speed);
		for (int i=0; i<5; i++)
			car1.speedDown();
		System.out.println("���� �� car1�� �ӵ�: "+car1.speed);
		
		
		Car car2 = new Car();
		System.out.println(car2.carName+"\t"+car2.carColor+"\t"+car2.maker);
//		car2.carName = "�׷���";
//		car2.carColor = "������";
//		car2.maker = "���";
//		System.out.println(car2.carName+"\t"+car2.carColor+"\t"+car2.maker);
		car2.speedDown();
		System.out.println(car2.speed);
		
		Car car3 = new Car();
		System.out.println(car3.carName+"\t"+car3.carColor+"\t"+car3.maker);
	}

}
