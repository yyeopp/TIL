package com.ssafy.hw04;

public class IoMain {

	public static void main(String[] args) {
		// ǰ�� ���
		
		IoIncome ColaIn = new IoIncome( "Cocacola Co.", 20210312, 30000,300,30000*300);
		ColaIn.setItem(55315,"��ī�ݶ�", "Cocacola Co.");
		IoOutcome ColaOut = new IoOutcome(55315, "��ī�ݶ�", "Cocacola Co.", "�̸�Ʈ", 20210412, 30000,600,30000*600);
		
		
		IoIncome CiderIn = new IoIncome("Cocacola Co.", 20220113, 40000,250,40000*250);
		CiderIn.setItem(73994, "��������Ʈ", "Cocacola Co.");
		IoOutcome CiderOut = new IoOutcome(73994, "��������Ʈ", "Cocacola Co.", "Ȩ�÷���", 20220211, 40000, 700, 40000*700);
	
		
	
	}

}
