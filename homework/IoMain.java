package com.ssafy.hw04;

public class IoMain {

	public static void main(String[] args) {
		// 품목 등록
		
		IoIncome ColaIn = new IoIncome( "Cocacola Co.", 20210312, 30000,300,30000*300);
		ColaIn.setItem(55315,"코카콜라", "Cocacola Co.");
		IoOutcome ColaOut = new IoOutcome(55315, "코카콜라", "Cocacola Co.", "이마트", 20210412, 30000,600,30000*600);
		
		
		IoIncome CiderIn = new IoIncome("Cocacola Co.", 20220113, 40000,250,40000*250);
		CiderIn.setItem(73994, "스프라이트", "Cocacola Co.");
		IoOutcome CiderOut = new IoOutcome(73994, "스프라이트", "Cocacola Co.", "홈플러스", 20220211, 40000, 700, 40000*700);
	
		
	
	}

}
