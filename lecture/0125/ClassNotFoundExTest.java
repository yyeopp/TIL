package com.company.exception;

import java.awt.*;		// awt 하부에 있는 패키지는 죄다 import하겠다는.

public class ClassNotFoundExTest {

	public static void main(String[] args)  {
		Button b;
		Checkbox d;
		Label l;
		Frame f;		// 죄다 awt에서 import하는 것들
		TextField t;	// ctrl + shift + o 로 import를 한방에 하는 방법도 있으니 알아두고 쓰기.
		
		
		
		try {
			Class c = Class.forName("com.ssafy.exception.ArithExTest2");	// API를 잘 뒤져보자. Class 클래스 - 생성자는 막혀있는 singleton - 객체를 리턴하는 메서드 호출 - forName 메서드
			System.out.println("ArithExTest.class 정상입력");		// 해당 메서드는 이미 CNFE를 throws하고 있다. 그러니까 내가 명시적으로 처리해줘야 함
			
		} catch (ClassNotFoundException e) {		// throws해버리거나, try~catch로 처리하거나
			e.printStackTrace();			// 겸사겸사 TODO 로 라벨링하는 방법도 알려주심. Tasks에서 찾아갈 수 있음
			System.out.println("class이름을 확인해 주세요");
		}
	}

}
