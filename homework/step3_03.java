package com.ssafy.ws01.step3;

import java.util.Random;
import java.util.Scanner;

public class GameTest {
	public static void main(String[] args) {
		// ���� �κ�
		System.out.println("���������� ������ �����մϴ�. �Ʒ� ���� �� �ϳ��� ������.");
		System.out.println("1. 5�� 3��"+"\n"+"2. 3�� 2��"+"\n"+"3. 1�� 1��");
		Scanner sc = new Scanner(System.in);		// Scanner �ѹ� ȣ���س�����
		System.out.print("��ȣ�� �Է��ϼ���.");
		int num = Integer.parseInt(sc.nextLine());		// ������ �� �𸣰����� nextInt()���� ������ �� ��ٰ�. �׳� Integer.parseInt()�� �ϳ� �� ���
		
		// 3�� 2�� ����
		if (num == 2) {
			int wincnt = 0;
			int loscnt = 0;
			for (int i=0; i<20; i++) {			// ��� ���� ��츦 �����Ѵٰ� �� �� i�� ������ Ŀ�� �� �����Ƿ�, ���⼭�� while�� �� �����ϴ�. �׷��� break�� ����� �ʿ䵵 ����
				System.out.print("���������� �� �ϳ� �Է�: ");
				String user = sc.nextLine();		// �׳� ��� �̷��� ����ϸ� �ȴ�. nextLine() �޼��带 �ַ� Ȱ���ϰ� �� ��
				Random r = new Random();
				int oppo = r.nextInt(3)+1;
//				int oppo = (int)(Math.random()*3)+1;
				if (user.equals("����")) {		// user�� String Ŭ�����̱� ������ == �����ڸ� �� �� ����.
					if (oppo==1) {
						System.out.println("�����ϴ�!!");
					}
					else if (oppo==2) {
						System.out.println("�����ϴ�!!");
						loscnt ++;
					}
					else if (oppo==3) {
						System.out.println("�̰���ϴ�!!");
						wincnt ++;
					}
				}
				if (user.equals("����")) {
					if (oppo==1) {
						System.out.println("�̰���ϴ�!!");
						wincnt ++;
					}
					else if (oppo==2) {
						System.out.println("�����ϴ�!!");
					}
					else if (oppo==3) {
						System.out.println("�����ϴ�!!");
						loscnt ++;
					}
				}					
				if (user.equals("��")) {
					if (oppo==1) {
						System.out.println("�����ϴ�!!");
						loscnt	++;
					}
					else if (oppo==2) {
						System.out.println("�̰���ϴ�!!");
						wincnt ++;
					}
					else if (oppo==3) {
						System.out.println("�����ϴ�!!");
					}
				}
				if (wincnt==2) {
					System.out.println("### ����� ��!!!");
					break;
				}
				if (loscnt==2) {
					System.out.println("### ��ǻ�� ��!!!");
					break;
				}
				
					
			}				
		
				
		}
			
			
		
	}
}
