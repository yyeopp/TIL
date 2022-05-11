package com.company.hw06.step2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class HealthBmi {

	public static HealthStudent[] scanData() throws FileNotFoundException {
		List<String> datalist = new ArrayList<>();
		System.setIn(new FileInputStream("src/com/ssafy/hw06/step2/input.txt"));
		Scanner sc = new Scanner(System.in);

		while (sc.hasNext()) {
			datalist.add(sc.nextLine());
		}
		sc.close();

		HealthStudent[] students = new HealthStudent[datalist.size()];

		for (int i = 0; i < datalist.size(); i++) {
			String each[] = datalist.get(i).split(",");
			students[i] = new HealthStudent(each[0], each[1], Float.parseFloat(each[2]), Float.parseFloat(each[3]));
		}
		return students;
	}

}
