package com.company.ws05;

import java.util.Scanner;

public class BirthSpec {

	public static void main(String[] args) {

		int year;
		int month;
		int day;

		Scanner sc = new Scanner(System.in);
		System.out.println("����⵵�� �Է��Ͻÿ�: ");
		year = sc.nextInt();
		System.out.println("������� �Է��Ͻÿ�: ");
		month = sc.nextInt();
		System.out.println("������� �Է��Ͻÿ�: ");
		day = sc.nextInt();

		System.out.printf("����� %d�� %d�� %d�� ���Դϴ�.\n", year, month, day);
		System.out.printf("����� %s�� �Դϴ�.\n", BirthAnimal.getD(year));
		System.out.printf("�׸��� ���ڸ��� %s�ڸ� �Դϴ�.", BirthStar.getStar(month, day));
	}

}
