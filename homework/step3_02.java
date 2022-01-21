package com.ssafy.ws01.step3;

public class DigitTest2 {
	public static void main(String[] args) {
		int count = 1;
		for (int i=0; i<5; i++) {
			if (i==0) {
				for(int j=0; j<5; j++) {
					System.out.print(count+"\t");
					count++;
				}
				System.out.println();
			}
			if (i==1) {
				System.out.print("\t");
				for(int j=0; j<3; j++) {
					System.out.print(count+"\t");
					count++;
				}
				System.out.print("\t");
				System.out.println();
			}
			if (i==2) {
				System.out.print("\t"+"\t"+count+"\t"+"\t");
				count++;
				System.out.println();
			}
			if (i==3) {
				System.out.print("\t");
				for(int j=0; j<3; j++) {
					System.out.print(count+"\t");
					count++;
				}
				System.out.print("\t");
				System.out.println();
			}
			if (i==4) {
				for(int j=0; j<5; j++) {
					System.out.print(count+"\t");
					count++;
				}
				System.out.println();
			}
			
			
		}
		
		
	}
}
