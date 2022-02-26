package com.ssafy.session.submit.yyeopp;

import java.io.Serializable;
import java.util.Random;

import com.ssafy.session.*;

public class NPsessionDivider implements ISessionDivider, Serializable {		
	
	// 혹시 가산점 받을까봐 Serializable은 달아놨습니다.

	private static final long serialVersionUID = 1L;
	
	static int[][] permutations;
	private Random rd;

	public NPsessionDivider() {
		long seed = 19970211;
		rd = new Random(seed);
	}

	public static void main(String[] args) throws SessionFormatException {
		int[] sessionNo = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4 };
		// 기본 아이디어는, 1~13조가 1,2,3,4 세션에 배정될 수 있고, 각 세션 별로 3,3,3,4개 조가 배정되기 때문에
		// 위의 sessionNo 배열에 저장된 세션을 "순열"로 재배치해서 그 모든 결과를 2차원 배열(permutations)에 입력합니다.
		// 그리고 permutations 배열의 인덱스 중 하나를 랜덤으로 추출해서, 해당 행에 있는 세션 배치를 각 조의 배정 결과로 활용합니다.

		// 순열은 NextPermutation 기법으로 돌렸습니다. (이하 다시 설명)

		permutations = new int[1201200][13];
		// 순열 돌린 결과를 저장하는 배열입니다. 크기는 [ 13! / (3! * 3! * 3! * 4!) ] 으로 구했습니다.
		// (중복된 값이 존재하는 수열)
		// 이 시점에서 조의 개수 또는 세션의 개수가 달라졌을 때에 대한 적용은 포기했습니다. (메모리도 함께 포기)

		makePermutations(sessionNo);
		// permutations 배열을 만들어주는 메서드입니다.

		ISessionDivider divider = new NPsessionDivider();
		System.out.println(SessionDividerTester.scoreDivider(divider, 10));
	}

	private static void makePermutations(int[] sessionNo) {
		// 중복된 값이 존재하는 수열이라는 점 + nPn 의 상황이라는 점 + 이미 메모리는 미국갔다는 점을 감안해서
		// NextPermutation을 활용하기로 했습니다.
		int count = 0;
		do {
			// NP를 돌린 결과물이 하나씩 permutations 배열에 쌓이게 됩니다.
			for (int i = 0; i < sessionNo.length; i++) {
				permutations[count][i] = sessionNo[i];
			}
			count++;
		} while (np(sessionNo));

	}

	@Override
	public int[][] assignSessions() {

		// 랜덤으로 0~1201199 중 하나를 뽑아서 idx로 삼습니다.
		// idx는 아까 만들어낸 permutations 배열 중 한 지점을 가리키게 됩니다.
		int idx = rd.nextInt(1201200);

		int[][] matrix = new int[4][4];
		// 리턴해줄 matrix입니다.
		// 0행에 1세션이 배정되고, 3행=4세션에 4팀이 들어갑니다.

		matrix[0][3] = 0;
		matrix[1][3] = 0;
		matrix[2][3] = 0;
		// 1,2,3 세션의 마지막 자리는 0으로 채웁니다.

		int a = 0, b = 0, c = 0, d = 0;
		// matrix를 채울 때 "열" 번호를 관리하기 위한 숫자입니다. 각각 0~3행에 대응됩니다.

		for (int i = 0; i < 13; i++) { // 13조까지 배정합니다.
			int team = i + 1;

			int session = permutations[idx][i] - 1;
			// permutations[idx] 배열에는 1,2,3,4 중 하나가 들어있으니, 값을 추출하고 1을 빼서 배열의 인덱스와 동기화시킵니다.

			switch (session) {
			case 0:
				matrix[session][a] = team;
				a++;
				break;
			case 1:
				matrix[session][b] = team;
				b++;
				break;
			case 2:
				matrix[session][c] = team;
				c++;
				break;
			case 3:
				matrix[session][d] = team;
				d++;
				break;

			}

		}
		return matrix;
	}

	// 여기부턴 NextPermutation 작동부분입니다. 수업 때 배운거 그대로 가져왔습니다.
	private static boolean np(int[] sessionNo) {

		int i = 12;

		while (i > 0 && sessionNo[i - 1] >= sessionNo[i]) {
			i--;
		}

		if (i == 0) {
			return false;
		}

		int j = 12;
		while (sessionNo[i - 1] >= sessionNo[j]) {
			j--;
		}

		swap(i - 1, j, sessionNo);

		int k = 12;
		while (i < k) {
			swap(i, k, sessionNo);
			i++;
			k--;
		}

		return true;
	}

	private static void swap(int i, int j, int[] sessionNo) {
		int temp = sessionNo[i];
		sessionNo[i] = sessionNo[j];
		sessionNo[j] = temp;
	}

}
