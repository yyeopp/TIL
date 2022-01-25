package com.company.exception;

public class NumberFormatExTest {

	public static void main(String[] args) {
//		String s = "25살";	//  나이를 입력하라고 했는데 숫자만 입력하지 않을 수도 있는 것
//		String s = "25";
		String s = null; // NullPointerException을 유발하는 부분
//		int age10 = Integer.parseInt(s)+10;		// parseInt가 불가능

//		try {
//			int age10 = Integer.parseInt(s)+10;
//			System.out.println("10년 후 나이: "+age10);
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			System.out.println("나이는 숫자만 입력해야 합니다.");
//		}		// 방금도 설명했던, 잘못된 예외처리방법

		if (isNumber(s)) { // 런타임에러에 대한 로직처리
			int age10 = Integer.parseInt(s) + 10;
			System.out.println("10년 후 나이 : " + age10);
		} else {
			System.out.println("나이는 숫자만 입력");
		}

	}

	private static boolean isNumber(String s) { // is나 get, set같은 이름을 달고 메서드처럼 해놓으면 자동완성해주는 기능 있다. ctrl+클릭
		boolean flag = true;
		if (s != null) {		// 널포인터 ex를 해결하기 위한 로직 수정
			for (int i = 0; i < s.length(); i++) { // 배열이랑 달리 length()로 해야하는 점. String 은 원래 클래스다. 배열.length에서의 length는
													// 속성이고 length()는 스트링의 메서드\
				int n = s.charAt(i) - 48; // 널포인터ex는 . 으로 메서드를 소환할 때 그에 해당하는 객체가 존재하지 않으면 발생한다.
				if (n < 0 || n > 9) {
					flag = false;
					break;
				}
			}
		} else {
			flag = false;
		}
		return flag;

	}

}
