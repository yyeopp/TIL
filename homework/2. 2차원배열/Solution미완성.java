

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		
		// 변수 초기화
		char [][] map = null;
		int result;
		int Gcnt;
		int Bcnt;
		
		// 테스트 케이스 입력
		Scanner sc = new Scanner(System.in);
		int repeat = Integer.parseInt(sc.nextLine());
		
		
		for (int i=0; i<repeat; i++) {
			// 배열 크기 입력
			int size = Integer.parseInt(sc.nextLine());
			
			// 배열 값 입력
			map = new char [size][size];
			for (int j=0; j<size; j++) {
				String S = sc.nextLine();
				for (int k=0; k<size; k++) {
					map[j][k] = S.charAt(k);
				}
			}

			// 하나씩 검증 돌입
			int[] dir_x = {1,0,-1,0};
			int[] dir_y = {0,1,0,-1};
			int[] cor_x = {1,1,-1,-1};
			int[] cor_y = {1,-1,-1,1};
			
			for (int l=0; l<size; l++) {
				for (int m=0; m<size; m++) {
					
					
					
					// 인접구역에 G가 있을 시 result 2
					
					
				}
			}
			
			
			
		}
		
		
	}

}
