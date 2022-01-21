package com.company.classtest;

public class Marine {

	private int hp;
	private boolean sp;

	public int getHp() {
		return hp;

	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public Marine() {
		hp = 100;
	}

	public void attack(Marine marine, int repeat) {
		for (int i = 0; i < repeat; i++) {
			marine.setHp(marine.getHp() - 10);

			if (sp == true) {
				marine.setHp(marine.getHp() - 5);
			}
			if (marine.getHp() < 0) {
				marine.setHp(0);
				break;
			}
		}

	}

	public static void getStatus(Marine m1, Marine m2) {
		if (m1.hp == 0) {
			System.out.println("내 상태 : 펑!!!" + "\t" + "컴 상태 : " + m2.getHp());
		}
		if (m2.hp == 0) {
			System.out.println("내 상태 : " + m1.getHp() + "\t" + "컴 상태 : 펑!!!");
		} else {
			System.out.println("내 상태 : " + m1.getHp() + "\t" + "컴 상태 : " + m2.getHp());
		}
	}

	public void changeMode() {
		if (hp >= 40) {
			sp = true;
			hp -= 25;
		} else {
			System.out.println("경고: 체력이 40미만일 경우 모드 변경 불가");
		}

	}
}
