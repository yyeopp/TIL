package com.company.hw02;

import java.io.*;
import java.util.*;


public class HealthStudentReport {

	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/com/ssafy/hw02/input.txt"));
		Scanner sc = new Scanner(System.in);
		
		List<String> datalist = new ArrayList<>();
		
//		try {
//			while(true) {
//				String temp = sc.nextLine();
//				if (temp != null) {
//					datalist.add(temp);
//				}
//				else {break;}
//			}
//			
//		} catch (NoSuchElementException e) {
//		}  		// 이럴필요가 없다..
		
		while(sc.hasNext()) {
			datalist.add(sc.nextLine());
		}
		
		HealthStudent [] students = new HealthStudent[datalist.size()];
		
		for (int i = 0; i<datalist.size(); i++) {
			String each [] = datalist.get(i).split(",");
			students [i] = new HealthStudent(each[0], each[1], Float.parseFloat(each[2]), Float.parseFloat(each[3]));
		}
		
		
		System.out.println("이름" + "\t\t" + "측정일" + "\t\t" + "몸무게" + "\t" + "키");
		for (int i = 0; i < datalist.size(); i++) {
			System.out.println(students[i]);
		}
		
		
		
//		String inputName = sc.next();		// setIn으로 in을 다시 콘솔 입력으로 바꿔야하는데 도저히 방법을 모르겠음..
		String inputName = "라이언";		// 일단 포기
		System.out.print("학원생 이름을 입력하세요. >> " + inputName + "\n");
		
		float totalW = 0;
		float totalH = 0;
		float count = 0;
		for (int i = 0; i < students.length; i++) {
			if(inputName.equals(students[i].getName())) {
				totalW += students[i].getWeight();
				totalH += students[i].getHeight();
				count ++;
			}
		}
		System.out.printf("평균 몸무게 : %.3fkg, 평균 키 : %.3fcm", totalW/count, totalH/count);
	}
}
