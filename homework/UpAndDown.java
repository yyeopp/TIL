package com.company.ws01.step2;

import java.util.Scanner;

public class UpAndDown {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int trial = 0;

		int answer = (int) (Math.random() * 99 + 1);

		while (true) {
			int inputNo = sc.nextInt();
			if (inputNo != answer) {
				trial++;
				if (inputNo < answer) {
					System.out.println("UP!!");
				}
				if (inputNo > answer) {
					System.out.println("DOWN!!");
				}
			}
			if (inputNo == answer) {
				System.out.println("정답입니다!! you tried: " + trial);
				break;
			}
		}

	}

}
