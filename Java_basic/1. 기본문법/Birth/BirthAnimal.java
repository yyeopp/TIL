package com.company.ws05;

public class BirthAnimal {
	
	public static String [] D = {"��", "��", "ȣ����", "�䳢", "��", "��", "��", "��", "������", "��", "��", "����"};
	
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
