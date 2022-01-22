package com.company.ws05;

public class BirthAnimal {
	
	public static String [] D = {"Áã", "¼Ò", "È£¶ûÀÌ", "Åä³¢", "¿ë", "¹ì", "¸»", "¾ç", "¿ø¼şÀÌ", "´ß", "°³", "µÅÁö"};
	
	public static String getD(int year) {
		int seed = year % 8;
		if (seed >= 4) {
			return D[seed-4];
		}
		else {
			return D[seed+4];
		}
	}
	
}
