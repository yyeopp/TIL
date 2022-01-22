package com.company.ws04;

public class XNumberPrint {

	public static void main(String[] args) {
		String [][] X = new String[9][9];
		for (int i = 0; i < X.length; i++) {
			for (int j = 0; j < X[i].length; j++) {
				if (j==i || j==8-i) {
					X[i][j] = "*";
				}
				else {
					X[i][j] = " ";
				}
				System.out.print(X[i][j]);
			}
			System.out.println();
		}
	}

}
