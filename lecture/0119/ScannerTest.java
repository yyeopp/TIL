package com.ssafy.ws02.step3;

import java.util.Scanner;

public class ScannerTest {

	public static void main(String[] args) {
		Scanner sc =  new Scanner(System.in); 	// �ڹٰ� �����ϴ� ScannerŬ�������� default �����ڰ� ����. ���� Scanner()�� ���� �� ���ٴ� ��
//		System.out.print("���� �Է�: ");
//		int num = sc.nextInt();		// �̰͵� API���� �����ؼ� �������� ��������..
//		System.out.println("�Է¹��� ��: "+num);
//		
//		System.out.print("���ڿ� �Է�: ");
//		String str = sc.next();		// token ������ �Էµȴٴ� �� api�� ����. ������ �������� �����شٴ� �ǹ��ε�, �ȳ� �ϼ��並 �Է��ϸ� �ȳ縸 �Էµȴ�.
//		System.out.println("�Է¹��� ���ڿ� " +str);
		
		System.out.print("���ڿ� �Է�: ");
		String srt = sc.nextLine();		// �̰Ŵ� �����̶� ������ �� ���� ��� �о��شٴ� �� api���� ����.
		System.out.println("�Է¹��� ���ڿ� "+srt);
	}

}
