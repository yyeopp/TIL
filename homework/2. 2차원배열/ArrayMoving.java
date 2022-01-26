package com.company.hw04;

import java.util.Scanner;

public class ArrayMoving {

	
	
	
	public static void main(String[] args) {
		ArrayMoving av = new ArrayMoving();
		int arr[][] = { {1, 2, 3, 4}, {5, 6, 7, 8},{9, 10, 11, 12}, {13, 14, 15, 16} };    
		
		Scanner sc = new Scanner(System.in);
		int repeat = sc.nextInt();
		 
		
		
		for (int a = 0; a < repeat; a++) {
			// 제목
			if (a==0) {
				System.out.println("Original Matrix");
			}
			if (a>0) {
				System.out.printf("\n%dth Matrix Moving\n",a);
			}
			// 출력
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					System.out.print(arr[i][j]+"\t");
				}
			System.out.println();
			} 
			// 이동
			if (a==repeat-1) {break;}
			int temp;
			temp = arr[0][0];
			arr[0][0] = arr[1][0];
			arr[1][0] = arr[2][0];
			arr[2][0] = arr[3][0];
			arr[3][0] = arr[3][1];
			arr[3][1] = arr[3][2];
			arr[3][2] = arr[3][3];
			arr[3][3] = arr[2][3];
			arr[2][3] = arr[1][3];
			arr[1][3] = arr[0][3];
			arr[0][3] = arr[0][2];
			arr[0][2] = arr[0][1];
			arr[0][1] = temp;			
			temp = arr[1][1];
			arr[1][1] = arr[2][1];
			arr[2][1] = arr[2][2];
			arr[2][2] = arr[1][2];
			arr[1][2] = temp;
		}
		 
		 

		 
		 
		 
	}

}
