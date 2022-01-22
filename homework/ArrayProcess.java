package com.company.ws01;

public class ArrayProcess {
	
	
	
	public static void main(String[] args) {
		
		int [][] dataSet = new int [6][5];
		int [] s1 = {4,4,5,5,3};
		int [] s2 = {5,5,5,5,4};
		int [] s3 = {3,2,3,2,1};
		int [] s4 = {4,5,5,5,5};
		int [] s5 = {4,4,4,3,5};
		int [] s6 = {5,5,5,4,5};
		
		dataSet[0] = s1;
		dataSet[1] = s2;
		dataSet[2] = s3;
		dataSet[3] = s4;
		dataSet[4] = s5;
		dataSet[5] = s6;
		
		for (int i = 0; i < dataSet.length; i++) {
			int total = 0;
			
			for (int j = 0; j < dataSet[i].length; j++) {
				total += dataSet[i][j];
				System.out.printf("%d\t",dataSet[i][j]);
			}
			System.out.printf("%d\t%.1f\n",total,(float)total/dataSet[i].length);
		}
		
		
	}

}
