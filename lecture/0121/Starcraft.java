package com.company.classtest;

// my, com marine ��ü ����
// ���� ����. �Ϲ� ���� �� ���ü�� -10
// ��� ����. �����Ѱ��� �� ���ü�� -15. ��� ���� �� �� ü�� -25. �� �� ü���� 40 ���ϸ� ��� ���� �Ұ�
// ���� �� ����

public class Starcraft {
	public static void main(String[] args) {
		Marine my = new Marine();
		Marine com = new Marine();

		System.out.println("���� ���� 3ȸ ����");
		my.attack(com, 3);
		Marine.getStatus(my, com);
		// �� ���� : 100 �� ���� : 70
		System.out.println("���� ���� 4ȸ ����");
		com.attack(my, 4);
		Marine.getStatus(my, com);
		// �� ���� : 60 �� ���� : 70
		System.out.println("���� ���� 3�� ����");
		my.attack(com, 3);
		Marine.getStatus(my, com);
		// �� ���� : 60 �� ���� : 40
		// �� ���� ��� ����
		my.changeMode();
		Marine.getStatus(my, com);
		// �� ���� : 35 �� ���� : 40
		System.out.println("���� ���� 2ȸ ����");
		my.attack(com, 2);
		Marine.getStatus(my, com);
		// �� ���� : 35 �� ���� : 10
		// �� ���� ��� ����
		my.changeMode();
		Marine.getStatus(my, com);
		// ���: ü���� 40�̸��� ��� ��� ���� �Ұ�
		// �� ���� : 35 �� ���� : 10
		System.out.println("���� ���� 10ȸ ����");
		my.attack(com, 10);
		Marine.getStatus(my, com);
		// �� ���� : 35 �� ���� : ��!!
	}
}
