package com.ssafy.session;

public class HardCodeDivider implements ISessionDivider {
	private static int cnt; // 현재까지 assignSessions가 호출된 횟수를 저장하는 변수
	private static int[][][] perfectSessions;
	
	public HardCodeDivider() {
		cnt = 0;
		
		// perfectSessions는 sessions의 배열입니다.
		// sessions[i]가 일차원 배열, sessions가 이차원 배열이므로 perfectSessions는 삼차원 배열입니다.
		// 반복하기만 해도 골고루 세션 배정을 할 수 있는 패턴을 찾았다면, 하드코드로 도전해보세요!
		 perfectSessions = new int[][][] { // 여기에 하드코드로 sessions의 배열을 넣으면 됩니다.
					{
						{1,2,3,0},
						{4,5,6,0},
						{7,8,9,0},
						{10,11,12,13},
					},
					{
						{1,4,7,0},
						{2,5,8,0},
						{3,6,9,0},
						{10,11,12,13},
					},
			};
	}

	@Override
	public int[][] assignSessions() {
		// assignSessions가 호출될 때마다 cnt를 1씩 증가시킵니다.
		// 호출 횟수 % 패턴 개수로, 이번에 리턴해야 할 패턴을 찾아 리턴합니다.
		return perfectSessions[cnt++ % perfectSessions.length];
	}
	
	public static void main(String[] args) throws SessionFormatException { // N번의 세션 배정 결과를 보기 위한 테스트 메서드
		ISessionDivider divider = new HardCodeDivider();
		System.out.println(SessionDividerTester.scoreDivider(divider, 10));
	}
}
