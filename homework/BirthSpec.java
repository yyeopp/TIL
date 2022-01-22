package com.company.ws05;

import java.util.Scanner;

public class BirthSpec {

	public static void main(String[] args) {

		int year;
		int month;
		int day;

		Scanner sc = new Scanner(System.in);
		System.out.println("출생년도를 입력하시오: ");
		year = sc.nextInt();
		System.out.println("출생월을 입력하시오: ");
		month = sc.nextInt();
		System.out.println("출생일을 입력하시오: ");
		day = sc.nextInt();

		System.out.printf("당신은 %d년 %d월 %d일 생입니다.\n", year, month, day);
		System.out.printf("당신은 %s띠 입니다.\n", BirthAnimal.getD(year));
		System.out.printf("그리고 별자리는 %s자리 입니다.", BirthStar.getStar(month, day));
	}

}
