package com.company.classtest;

import java.util.Calendar;

public class STTest {

	public static void main(String[] args) {
//		STService st1 = new STService();	// 디폴트 생성자가 있으니까 STService를 비워놨어도 객체 생성 가능
//		STService st2 = new STService();	// private으로 막아놓고 오면 바로 에러가 뜬다.
		// toString() 오버라이딩 안했으니까 주소값 호출
				// 둘은 각기 다른 객체이므로 주소가 다르다.
				
				// 근데 매번 객체를 만들어서 접근하는 건 지나친 메모리 낭비
		STService st1 = STService.getStService();
		STService st2 = STService.getStService();
		// 이러면 객체가 하나밖에 안 생긴다. st1과 st2가 같은 주소를 참조한다.
		
		System.out.println(st1 + "\t" + st2);
		
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		System.out.println(cal1 + "\t" + cal2);
		
	}

}
