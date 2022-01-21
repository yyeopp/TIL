package com.ssafy.ws02.step3;

import java.util.Scanner;

public class ScannerTest {

	public static void main(String[] args) {
		Scanner sc =  new Scanner(System.in); 	// 자바가 제공하는 Scanner클래스에는 default 생성자가 없다. 원래 Scanner()를 만든 적 없다는 것
//		System.out.print("숫자 입력: ");
//		int num = sc.nextInt();		// 이것도 API에서 공부해서 가져오는 방향으로..
//		System.out.println("입력받은 값: "+num);
//		
//		System.out.print("문자열 입력: ");
//		String str = sc.next();		// token 단위로 입력된다는 게 api상 설명. 공백을 기준으로 끊어준다는 의미인데, 안녕 하세요를 입력하면 안녕만 입력된다.
//		System.out.println("입력받은 문자열 " +str);
		
		System.out.print("문자열 입력: ");
		String srt = sc.nextLine();		// 이거는 공백이랑 무관히 한 줄을 모두 읽어준다는 게 api상의 설명.
		System.out.println("입력받은 문자열 "+srt);
	}

}
