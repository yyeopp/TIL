package com.company.hw06.step2;

import java.io.FileNotFoundException;

public class HealthBmiReport {

	public static void printHealth() throws FileNotFoundException {

		

		System.out.println("이름" + "\t\t" + "측정일" + "\t\t" + "몸무게" + "\t" + "키");
		for (int i = 0; i < HealthBmi.scanData().length; i++) {
			System.out.println(HealthBmi.scanData()[i]);
		}
	}

	public static void printWho(String inputName) throws FileNotFoundException {
		float totalW = 0;
		float totalH = 0;
		float count = 0;
		for (int i = 0; i < HealthBmi.scanData().length; i++) {
			if (inputName.equals(HealthBmi.scanData()[i].getName())) {
				totalW += HealthBmi.scanData()[i].getWeight();
				totalH += HealthBmi.scanData()[i].getHeight();
				count++;
			}
		}
		System.out.printf("평균 몸무게 : %.3fkg, 평균 키 : %.3fcm\n", totalW / count, totalH / count);
	}
}
