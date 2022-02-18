package com;

import java.util.*;

public class CarMain {

	public static void main(String[] args) {

//		CarManager carManager = new CarManagerImpl();		// 싱글톤으로 막지 않으면, 이런 형식의 코드에 의해 carManager라는 중요한 객체가 너무 많이 생성될 수 있다.

		CarManager carManager = CarManagerImpl.getCarManager(); // 싱글톤 구현 완료.

		// 등록
		System.out.println("#####자동차 등록#####");
		try {
			carManager.add(new Car(1111, "소나타")); // SNE를 예외처리해야 함. try~catch로 처리
			carManager.add(new Car(2222, "아반떼"));
			carManager.add(new Car(3333, "소나타"));
			carManager.add(new Car(3333, "그랜저")); // SNE 유발
			carManager.add(new Truck(4444, "포터", 15)); // 다형성 활용, truck 객체 등록, 생성자 다른 거 유의
			carManager.add(new Car(5555, "제네시스"));

		} catch (SameNumberException e) {
			System.out.println(e.getMessage()); // getMessage 메서드 알고가자
		}

		System.out.println();
		// 목록 호출

		System.out.println("#####자동차 목록#####");
		List<Car> list = carManager.search();
		carList(list); // 자주쓰는 기능이니까 메서드를 하나 따로 파면 좋다. ( private static 메서드로 )

		System.out.println();

		// 검색
		int carNum = 2222;
		try {
			Car car = carManager.searchByNum(carNum);
			System.out.println("번호가 " + carNum + "인 자동차의 정보: " + car);
		} catch (CarNotFoundException e) {
			System.out.println(e.getMessage());
		}

		System.out.println();
		// 몇 개 더 추가
		try {
			carManager.add(new Car(5555, "K5"));
			carManager.add(new Car(6666, "그랜저"));
			carManager.add(new Car(7777, "소나타"));
			carManager.add(new Car(8888, "그랜저"));
			carManager.add(new Truck(9999, "포터", 15));

		} catch (SameNumberException e) {
			System.out.println(e.getMessage()); // getMessage 메서드 알고가자
		}
		System.out.println("#####기존 목록#####");
		carList(list);
		System.out.println("-------------------------------------------------");

		System.out.println();
		// 차 이름 업데이트
		carNum = 5555;
		String carName = "SM5";
		carManager.update(carNum, carName);
		System.out.println("#####차 번호가 " + carNum + "인 차 이름을 " + carName + "으로 교체");
		carList(list);

		System.out.println();
		// 차 번호로 삭제
		carNum = 5555;
		carManager.delete(carNum);
		System.out.println("####차 번호가 " + carNum + "인 차량을 삭제####");

		carList(list);

		System.out.println();
		// 차 이름으로 삭제
		carName = "소나타";
		carManager.delete(carName);
		System.out.println("####차 이름이 " + carName + "인 차량을 삭제####");
		carList(list);

		System.out.println();
		// 몇 개 더 다시 추가
		try {
			carManager.add(new Car(1235, "K5"));
			carManager.add(new Car(7486, "그랜저"));
			carManager.add(new Car(4427, "소나타"));
			carManager.add(new Car(8458, "그랜저"));
			carManager.add(new Truck(3369, "포터", 15));

		} catch (SameNumberException e) {
			System.out.println(e.getMessage());
		}

		carList(list);

		System.out.println();
		// list를 dat 파일로 저장
		System.out.println("#####list를 car.dat 파일에 저장#####"); // 상대경로로 저장하고 있기 때문에 project 루트 바로 밑에 저장된다.
		carManager.fileSave();

		System.out.println();
		// 저장된 dat 파일을 로드
		System.out.println("#####car.dat 파일에 저장된 list를 로드#####");
		List<Car> carList = carManager.fileLoad();
		carList(carList);

		System.out.println();
		// 자동차 이름으로 정렬
		System.out.println("#####자동차 이름으로 정렬#####");
		Collections.sort(carList);
		carList(carList);

		System.out.println();
		// 자동차 번호로 오름차순 정렬
		System.out.println("#####자동차 번호로 오름차순 정렬#####");
		Collections.sort(carList, new Comparator<Car>() {		// comparable 인터페이스는 이미 구현됐으므로, comparator를 직접 명시해서 구현
			@Override
			public int compare(Car o1, Car o2) {
				return o1.getCarNum() - o2.getCarNum();
			}
		});
		carList(carList);
		
	}


	private static void carList(List<Car> list) {
		for (Car car : list)
			System.out.println(car);
	}

}
