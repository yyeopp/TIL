

public class TaxiTest {

	public static void main(String[] args) {

		Taxi t1 = new Taxi();
		System.out.println("Taxi " + t1.toString()); // public한 메서드를 가져다가 사용
		System.out.println("t1택시 20번 가속");
		for (int i = 0; i < 20; i++) {
			t1.speedUp();
		}
		System.out.println("t1속도: " + t1.getSpeed());
//		System.out.println("이름: " + t1.carName + "\t색상: " + t1.carColor + "\t제조사: " + t1.maker);

		Taxi t2 = new Taxi("K5", "감홍색", "기아");
//		t2.setCarName("아반떼"); // setter를 통해 private한 전역변수 속성 바꾸기
		System.out.println("Taxi " + t2); // toString이 자동으로 소환된다

		System.out.println(t2.info());

		Taxi t3 = new Taxi("K5", "감홍색", "기아");

		System.out.println("t2: " + t2);
		System.out.println("t3: " + t3);

		if (t2 == t3) {
			System.out.println("t2와 t3는 주소값이 같은 차");
		} else {
			System.out.println("t2와 t3는 주소값이 다른 차");
		}

		Taxi t4 = t2;
		if (t2 == t4) {
			System.out.println("t2와 t4는 주소값이 같은 차");
		}

//		if (t2.carName.equals(t3.carName)) {		// 원래 가장 정확하게 하는 방법
//			System.out.println("t2와 t3은 이름이 같은 차");
//		}
//		else {
//			System.out.println("t2와 t3는 이름이 다른 차");
//		}
		System.out.println(t2);		// 이거 왜 toString() 된게 나오지????????????????????????????????????????????????
		if (t2.equals(t3)) { // Car 클래스에서 equals를 오버라이딩하고 온 결과
			System.out.println("t2와 t3은 이름이 같은 차");
		} else {
			System.out.println("t2와 t3는 이름이 다른 차");
		}
		if (t2 == t3) {
			System.out.println("t2와 t3은 이름이 같은 차");
		} else {
			System.out.println("t2와 t3는 이름이 다른 차");
		}
	}

}
