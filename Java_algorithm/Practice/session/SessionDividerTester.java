package com.ssafy.session;

import java.util.Arrays;

public class SessionDividerTester {
	public static final int nPairs = 13; // 페어의 개수
	public static final int nSessions = 4; // 세부세션의 개수
	public static final int maxPerSessions = 4; // 세션별 페어 수의 최대값
	public static final int[] perSessions = { 3, 3, 3, 4 }; // 세션별 페어 수 (정렬됨)
	
	public static double scoreDivider(ISessionDivider divider) throws SessionFormatException {
		return scoreDivider(divider, 10); // 기본 설정
	}

	/**
	 * 세부세션 배정 알고리즘을 채점하는 메서드
	 * 
	 * @param sd: ISessionDivider의 구현 객체
	 * @param N: assignSessions의 호출 횟수
	 * @return sd로 N번 세부세션 배정을 수행한 뒤의 점수
	 * @throws SessionFormatException
	 */
	public static double scoreDivider(ISessionDivider divider, int N) throws SessionFormatException {
		int[][] matrix = new int[nPairs + 1][nPairs + 1];
		for (int i = 0; i < N; i++) {
			int[][] sessions = divider.assignSessions(); // 파라미터로 받은 ISessionDivider의 구현체의 assignSessions 메서드를 호출
			System.err.println(formatSessions(sessions));
			validateSessions(sessions); // sessions의 형식이 안 맞으면 SessionFormatException을 던진다.
			updateMatrix(sessions, matrix); // sessions에 따라 matrix를 업데이트
		}

		System.err.println(formatMatrix(matrix));
		return scoreMatrix(matrix); // matrix의 점수를 매겨 리턴
	}

	/**
	 * 세부세션 배정 정보 sessions가 형식에 맞는지 검사하는 메서드
	 * 
	 * @param sessions
	 * @return 아래 형식에 다른 검사 결과 1. sessions의 크기는 int[4][4]이다. 2. sessions의 원소들 중 0은
	 *         빈칸을, 1~13 범위의 숫자는 해당 세부세션에 배정된 페어 번호를 의미한다. 3. 1~13 범위의 숫자가 중복없이 한 번씩
	 *         들어가야 한다. 4. 3개의 세부세션에는 3개 조가, 1개의 세부세션에는 4개 조가 배정되어야 한다.
	 * @throws SessionFormatException
	 */
	public static boolean validateSessions(int[][] sessions) throws SessionFormatException {
		// 1. sessions의 크기는 int[4][4]이다.
		if (sessions.length != nSessions)
			throw new SessionFormatException(String.format("sessions는 int[%d][%d]여야 합니다. sessions.length=%d", nSessions,
					maxPerSessions, sessions.length));

		for (int i = 0; i < sessions.length; i++)
			if (sessions[i].length != maxPerSessions)
				throw new SessionFormatException(String.format("sessions는 int[%d][%d]여야 합니다. sessions[%d].length=%d",
						nSessions, maxPerSessions, i, sessions[i].length));

		// 3. 1~13 범위의 숫자가 중복없이 한 번씩 들어가야 한다.
		int bitset = 0;
		for (int[] session : sessions) {
			for (int i : session) {
				if (i == 0)
					continue;

				if ((bitset & 1 << i) != 0) // 중복 발견!
					throw new SessionFormatException(String.format("1~%d까지의 수가 한 번씩만 들어가야 합니다. 중복된 수 = %d", nPairs, i));

				bitset |= 1 << i;
			}
		}

		if (bitset != (1 << nPairs + 1) - 2) // 빠진 숫자 발견!
			throw new SessionFormatException(
					String.format("1~%d까지의 수가 한 번씩 들어가야 합니다. 비트셋 = %s", nPairs, Integer.toBinaryString(bitset)));

		// 4. 3개의 세부세션에는 3개 조가, 1개의 세부세션에는 4개 조가 배정되어야 한다.
		int[] cnt = new int[sessions.length];
		for (int i = 0; i < sessions.length; i++) {
			for (int j : sessions[i]) {
				if (j != 0) {
					cnt[i]++;
				}
			}
		}
		Arrays.sort(cnt);
		for (int i = 0; i < cnt.length; i++) {
			if (cnt[i] != perSessions[i])
				throw new SessionFormatException(String.format("세션 별 페어의 수가 잘못 되었습니다. 현재 페어 수=%s perSessions=%s",
						Arrays.toString(cnt), Arrays.toString(perSessions)));
		}

		return true; // 다 통과했으면 true
	}

	/**
	 * 세부세션 배정 정보 sessions에 따라 matrix를 업데이트하는 메서드
	 * 
	 * @param sessions: 각 세부세션에 배정된 페어 정보. ISessionDivider에서 형식 참고
	 * @param matrix: 서로 만난 횟수를 나타내는 이차원 배열. 메서드에서 업데이트됨
	 */
	public static void updateMatrix(int[][] sessions, int[][] matrix) {
		for (int[] session : sessions)
			for (int i = 0; i < session.length; i++)
				for (int j = i + 1; j < session.length; j++) {
					if (session[i] == 0 || session[j] == 0)
						continue;
					matrix[session[i]][session[j]]++;
					matrix[session[j]][session[i]]++;
				}
	}

	/**
	 * matrix로부터 점수를 계산하는 메서드
	 * @param matrix: 서로 만난 횟수를 나타내는 이차원 배열
	 * @return 계산된 점수 값 (현재로서는 공지 PPT의 '모든 조의 격차')
	 */
	public static double scoreMatrix(int[][] matrix) {
		int[] maxDiffs = new int[nPairs + 1];
		for (int i = 1; i <= nPairs; i++) {
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (int j = 1; j <= nPairs; j++) {
				if (i == j) continue; // i==j인 대각성분은 건너뛴다.
				
				min = Math.min(min, matrix[i][j]); // 각 조에서 만난 횟수 최소값 구하기
				max = Math.max(max, matrix[i][j]); // 각 조에서 만난 횟수 최대값 구하기
			}
			
			maxDiffs[i] = max - min; // i조의 격차 = 최대값 - 최소값
		}
		
		int maxDiff = Integer.MIN_VALUE;
		for (int diff : maxDiffs) { // 구해둔 모든 i조의 격차로부터, 그 중 최대값인 '모든 조의 격차'를 구한다.
			maxDiff = Math.max(maxDiff, diff);
		}

		return maxDiff;
	}
	
	/**
	 * sessions의 StringBuilder 만들어주는 메서드
	 * @param sessions
	 * @return
	 */
	public static StringBuilder formatSessions(int[][] sessions) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < sessions.length; i++) {
			sb.append("세부세션").append(i+1).append(":");
			for (int j = 0; j < sessions[i].length; j++) {
				if (sessions[i][j] == 0) continue;
				sb.append(' ').append(sessions[i][j]).append('조');
			}
			sb.append('\n');
		}
		
		return sb;
	}
	
	/**
	 * matrix의 StringBuilder 만들어주는 메서드
	 * @param sessions
	 * @return
	 */
	public static StringBuilder formatMatrix(int[][] matrix) {
		StringBuilder sb = new StringBuilder();
		
		for (int[] row : matrix) {
			for (int i : row) {
				sb.append(i).append(' ');
			}
			sb.append('\n');
		}
		
		return sb;
	}
}
