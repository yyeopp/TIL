package com.company.classtest;

import java.util.Calendar;

public class STTest {

	public static void main(String[] args) {
//		STService st1 = new STService();	// ����Ʈ �����ڰ� �����ϱ� STService�� ������ ��ü ���� ����
//		STService st2 = new STService();	// private���� ���Ƴ��� ���� �ٷ� ������ ���.
		// toString() �������̵� �������ϱ� �ּҰ� ȣ��
				// ���� ���� �ٸ� ��ü�̹Ƿ� �ּҰ� �ٸ���.
				
				// �ٵ� �Ź� ��ü�� ���� �����ϴ� �� ����ģ �޸� ����
		STService st1 = STService.getStService();
		STService st2 = STService.getStService();
		// �̷��� ��ü�� �ϳ��ۿ� �� �����. st1�� st2�� ���� �ּҸ� �����Ѵ�.
		
		System.out.println(st1 + "\t" + st2);
		
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		System.out.println(cal1 + "\t" + cal2);
		
	}

}
