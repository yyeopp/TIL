package com.company.classtest;

// my, com marine 객체 생성
// 서로 공격. 일반 공격 시 상대체력 -10
// 모드 변경. 스팀팩공격 시 상대체력 -15. 모드 변경 시 내 체력 -25. 단 내 체력이 40 이하면 모드 변경 불가
// 변경 후 공격

public class Starcraft {
	public static void main(String[] args) {
		Marine my = new Marine();
		Marine com = new Marine();

		System.out.println("내가 컴을 3회 공격");
		my.attack(com, 3);
		Marine.getStatus(my, com);
		// 내 상태 : 100 컴 상태 : 70
		System.out.println("컴이 나를 4회 공격");
		com.attack(my, 4);
		Marine.getStatus(my, com);
		// 내 상태 : 60 컴 상태 : 70
		System.out.println("내가 컴을 3번 공격");
		my.attack(com, 3);
		Marine.getStatus(my, com);
		// 내 상태 : 60 컴 상태 : 40
		// 내 마린 모드 변경
		my.changeMode();
		Marine.getStatus(my, com);
		// 내 상태 : 35 컴 상태 : 40
		System.out.println("내가 컴을 2회 공격");
		my.attack(com, 2);
		Marine.getStatus(my, com);
		// 내 상태 : 35 컴 상태 : 10
		// 내 마린 모드 변경
		my.changeMode();
		Marine.getStatus(my, com);
		// 경고: 체력이 40미만일 경우 모드 변경 불가
		// 내 상태 : 35 컴 상태 : 10
		System.out.println("내가 컴을 10회 공격");
		my.attack(com, 10);
		Marine.getStatus(my, com);
		// 내 상태 : 35 컴 상태 : 펑!!
	}
}
