package com.ssafy.classtest;

public class CarTest {

	public static void main(String[] args) {
		Car car1 = new Car();
		System.out.println(car1.carName+"\t"+car1.carColor+"\t"+car1.maker);
//		car1.carName = "쏘나타";
//		car1.carColor = "흰색";
//		car1.maker = "현대";
//		System.out.println(car1.carName+"\t"+car1.carColor+"\t"+car1.maker);
		
		System.out.println("car1을 3회 가속: " + car1.speed);
		for(int i=0; i<3 ; i++)
			car1.speedUp();
		System.out.println("가속 후 car1의 속도: " + car1.speed);
		// 콜바이 레퍼런스가 뭔지 확인하자
		
		System.out.println("car1을 5회 감속 " + car1.speed);
		for (int i=0; i<5; i++)
			car1.speedDown();
		System.out.println("감속 후 car1의 속도: "+car1.speed);
		
		
		Car car2 = new Car();
		System.out.println(car2.carName+"\t"+car2.carColor+"\t"+car2.maker);
//		car2.carName = "그랜저";
//		car2.carColor = "검정색";
//		car2.maker = "기아";
//		System.out.println(car2.carName+"\t"+car2.carColor+"\t"+car2.maker);
		car2.speedDown();
		System.out.println(car2.speed);
		
		Car car3 = new Car();
		System.out.println(car3.carName+"\t"+car3.carColor+"\t"+car3.maker);
	}

}
