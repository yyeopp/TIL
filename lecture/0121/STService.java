package com.company.classtest;

public class STService {
	
	private static STService stService;
	
	private STService() {
	} 	// 객체 생성을 위한 생성자 호출은, 지금 이 클래스 안에서만 가능해진다.
	
	public static STService getStService() {	// static area가 됐으므로 사용하는 변수도 모두 static해야한다.
		if (stService == null)
			stService = new STService();
		return stService;
	}
	
	
}
