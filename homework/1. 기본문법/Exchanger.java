package com.company.hw04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Exchanger {
	
	void toDollar() {}
	void toKRW() {}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("src/com/ssafy/hw04/input.txt"));
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 2; i++) {
			float result;
			String [] data = sc.nextLine().split(",");
			if (data[0].equals("KRW")) {
				result = Float.parseFloat(data[1]) / 1192;
				System.out.printf("%s원 >> %.2f달러\n\n",data[1], result);
			}
			if (data[0].equals("USD")) {
				result = Float.parseFloat(data[1]) *1192;
				System.out.printf("%s달러 >> %.0f원\n\n",data[1], result);
			}
			
		}
		
	}

}
