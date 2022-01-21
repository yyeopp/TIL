package com.company.classtest;

public class STService {
	
	private static STService stService;
	
	private STService() {
	} 	// ��ü ������ ���� ������ ȣ����, ���� �� Ŭ���� �ȿ����� ����������.
	
	public static STService getStService() {	// static area�� �����Ƿ� ����ϴ� ������ ��� static�ؾ��Ѵ�.
		if (stService == null)
			stService = new STService();
		return stService;
	}
	
	
}
