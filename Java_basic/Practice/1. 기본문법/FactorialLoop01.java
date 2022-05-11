package com.company.ws01;

import java.util.Scanner;

public class FactorialLoop01 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int inputNo = sc.nextInt();
	    int result = 1;
	    
	    
	    while (inputNo >=1) {
	    	result *= inputNo;
	    	inputNo --;
	    }
	    
	    System.out.println(result);
	    
	}

}
