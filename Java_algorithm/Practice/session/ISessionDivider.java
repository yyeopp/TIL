package com.ssafy.session;

public interface ISessionDivider {
	/**
	 * 이 메서드를 오버라이드하여 ISessionDivider의 구현 클래스를 작성하시오.
	 * 이 메서드 외의 변수/객체/메서드/내부클래스 등 무엇이든 내부적으로 이용 가능합니다.
	 * @return 세션 배정 정보를 담은 int[][] sessions
	 * 리턴 형식:
	 * 1. sessions의 크기는 int[4][4]이다.
	 * 2. sessions의 원소들 중 0은 빈칸을, 1~13 범위의 숫자는 해당 세부세션에 배정된 페어 번호를 의미한다.
	 * 3. 1~13 범위의 숫자가 중복없이 한 번씩 들어가야 한다.
	 * 4. 3개의 세부세션에는 3개 조가, 1개의 세부세션에는 4개 조가 배정되어야 한다.
	 * 5. assignSessions는 N번 호출된다. N은 양의 정수로, 기본값은 10이지만 변별력이 필요할 경우 100, 1000 등 커질 수도 있다.
	 */
	public int[][] assignSessions();
}
