package com;

import java.io.*;
import java.util.*;

public class CarManagerImpl implements CarManager {
	private List<Car> list;

	// 싱글톤 구현
	private CarManagerImpl() {

		list = new ArrayList<Car>(); // 객체 생성될 시 List를 만들면 됨.

	} // 1. 생성자를 private으로 막는다.

	private static CarManager carManager = new CarManagerImpl(); // 2-1. static으로 객체를 만들어두는 방법

	public static CarManager getCarManager() { // 3. 만들어진 객체를 소환할 수 있는 getter 메서드를 public으로 선언한다.
//		if (carManager == null) { 				// 2-2. getter 메서드 소환 시 "딱 한번만" 객체를 만들어내는 방법.
//			carManager = new CarManagerImpl();
//		}

		return carManager;
	}

	@Override
	public void add(Car car) throws SameNumberException {

		try {
			this.searchByNum(car.getCarNum());
			// 위를 통과했다면, 목록 상 동일 차번이 존재했단 것 = list에 add할 수 없다. 예외를 "던져야" 하는 상황.
			throw new SameNumberException(car.getCarNum() + "번 자동차는 이미 등록되어 있습니다."); // throw로 잡자.

		} catch (CarNotFoundException e) {

			// 만약 this.searchByNum()에서 NotFound가 발생했다면, list 저장이 가능하다는 뜻이다.
			// catch 블록은 단순히 에러가 떴다고 표시하는 부분이 아니고, 발생한 에러에 대해 대처하는 부분.
			list.add(car); // 이렇게 잡으면 된다.

		}

	}

	@Override
	public List<Car> search() {
		return list;
	}

	@Override
	public Car searchByNum(int carNum) throws CarNotFoundException { // throws는 "가능성"을 내포. 미래에 던질 수도 있다는 뜻
		for (Car car : list) {
			if (car.getCarNum() == carNum)
				return car;
		} // 목록을 다 돌았는데 없었다면? 예외를 던지자 - "throw"를 사용. 예외가 "던져져야 한다"는 것.
		throw new CarNotFoundException(carNum + "번 자동차는 없습니다."); // 사용자정의예외를 사용하는 방식.

	}

	@Override
	public void update(int carNum, String carName) {

		try {
			Car car = this.searchByNum(carNum);
			car.setCarName(carName);

		} catch (CarNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(int carNum) {
//		for(Car car : list) {		// 이거 돌리다가 remove해버리면 list가 변형되면서 에러가 발생한다. 사용할 수 없는 방법
//			if(car.getCarNum() == carNum) {
//				list.remove(car);		
//			}
//		}

		// #### 방법 1.
//		for (int i = 0; i < list.size(); i++) {		// remove되면 size가 변하긴 하지만 어차피 찾는 게 하나니까 별로 상관없다.
//			if(carNum == list.get(i).getCarNum()) {
//				list.remove(i);
//				break;		// break로 끊어주는 게 아무래도 중요
//			}
//		}

		// #### 방법 2.
		try { // 이 방법도 사용 가능 - 이미 만들어놓은 search 메서드이므로 활용. 이게 좀더 괜찮은듯? (예외처리도 해놨으니)
			Car car = this.searchByNum(carNum);
			list.remove(car);
		} catch (CarNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(String carName) {

		for (int i = list.size() - 1; i >= 0; i--) { // ArrayList를 사용하고 있기 때문에 index 혼란이 올 수 있다 = 뒤에서부터 탐색하는 방법이 필요
			if (list.get(i).getCarName().equals(carName)) {
				list.remove(i);
			}
		}

	}

	@Override
	public void fileSave() { // 문자열이나 수를 저장하는 게 아니기 때문에 FileWriter를 사용하지 않는다. List라는 Object를 저장해야 한다.

		// 1. try with resource를 사용하지 않는 옛날 방식.

//		ObjectOutputStream objectOutputStream = null;		// finally 구문으로 접근하기 위해 밖에서 생성해줘야 함
//		try {
//			objectOutputStream = new ObjectOutputStream(new FileOutputStream("car.dat")); // 스트림 사용법..
//
//			objectOutputStream.writeObject(list); // 메서드 활용.
//			objectOutputStream.flush();		// 스트림 활용 시 flush가 반드시 필요
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		
//		} finally {
//			try {
//				if (objectOutputStream != null)		// try 첫 줄에서 생성이 안될 시 close 메서드가 널포인터를 발생시킬 수 있음.
//					objectOutputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}

		// 2. try with resource 방식.
		
		
		// Autocloseable 인터페이스를 구현하고 있는 클래스인 경우에만 사용 가능한 점 주의!
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("car.dat"))) { 

			objectOutputStream.writeObject(list); // 그냥 돌리면 예외 발생 - list 객체가 "직렬화(Serializable)" 되어있지 않기 때문
			objectOutputStream.flush(); // 쉽게 생각하면 file로 변환시키기 위한 준비가 안되어있다.
										// String다발을 파일로 저장하는 데 아무 장애가 없는 이유: String 클래스가 Serializable 인터페이스를 이미 구현하고 있기
										// 때문
										// 지금 list가 Car라는 객체들을 담고 있기 때문에, Car 객체를 "implements Serializable"하면 정상적으로 작동
										// 가능하다.
		} catch (Exception e) {
			e.printStackTrace();
		} // finally 구문에서 close할 필요 없이 알아서 닫아준다.

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Car> fileLoad() {

		File file = new File("car.dat"); // 존재 여부를 확인하기 위해 그 이름에 해당하는 File 객체를 생성
		if (file.exists()) { // 존재하는 경우에만.

			// 아까랑 반대방향의 스트림, try with resource
			try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("car.dat"))) {
				return (List<Car>) objectInputStream.readObject(); // 스트림에서 object를 읽고, List 형으로 명시적 형변환.

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return Collections.EMPTY_LIST; // exception 발생 시 여기로

		} else { // file이 존재하지 않을 시 여기로
			return Collections.EMPTY_LIST;		// 그냥 비어있는 list라도 리턴해야, 뒤에서 널포인터 같은 일이 발생하지 않는다.
		}

	}

}
