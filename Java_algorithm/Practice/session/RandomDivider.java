package com.ssafy.session;

import java.util.*;

public class RandomDivider implements ISessionDivider {
	private static final int[] perSession = {3, 3, 3, 4}; // 각 세션 당 배정되는 페어의 수 배열
	
	private Random random;
	private ArrayList<Integer> list;
	
	public RandomDivider() {
		// 랜덤 쓸 때는 꼭! 특정 값으로 랜덤 시드를 지정해주세요.
		// 채점시 일관성을 유지하기 위함입니다.
		random = new Random("코카콜라 맛있다 맛있으면 또 먹어".hashCode()); // 랜덤은 역시 코카콜라!
		list = new ArrayList<>();
		for (int i = 1; i <= 13; i++) {
			list.add(i);
		}
	}

	@Override
	public int[][] assignSessions() {
		Collections.shuffle(list, random); // 지정한 랜덤 시드로 셔플
		int[][] sessions = new int[perSession.length][4];
		int idx = 0; // 현재 list의 인덱스
		for (int i = 0; i < perSession.length; i++) { // 각 세션에 대해
			for (int j = 0; j < perSession[i]; j++) { // 세션 별 페어 수만큼
				sessions[i][j] = list.get(idx++); // 셔플된 리스트에서 배정
			}
		}
		
		return sessions;
	}

	public static void main(String[] args) throws SessionFormatException { // N번의 세션 배정 결과를 보기 위한 테스트 메서드
		ISessionDivider divider = new RandomDivider();
		System.out.println(SessionDividerTester.scoreDivider(divider, 10));
	}
}
