

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/com/ssafy/ws02/step3/input.txt"));	// 이거도 api에서 찾아와야 함. java.io에서 inputstream 클래스에 있음. System.in에서 in의 속성을 바꾼다는 느낌
		Scanner sc = new Scanner(System.in);	// throws 예외처리는 일단 나중에 배우자
		int T = sc.nextInt();	// 이러면 txt파일의 첫줄 첫 공백 전 int를 읽어오는 것.
		
		for (int tc=1; tc<=T; tc++) {
			int N = sc.nextInt();	// 이러면 또 알아서 그 다음줄의 int를 읽어준다..
			char [][] map = new char[N][N];
			for (int i=0; i<N; i++) {
				for (int j=0; j<N; j++) {		// 명시적 형변환은 서로 가능할 때만 된다. String과 char 사이 형변환은 안되고, boolean과 int 사이 형변환도 안된다.
					map[i][j] = sc.next().charAt(0); 	// next()는 공백 이전 str을 읽어온다. charAt(0)으로 그 str의 첫 문자를 char로 따온다.
				}	  // 이렇게만 하면 쉽게 입력 가능...
			}
			
			
			
			int[] dir_x = {1,0,-1,0};   // 하, 우, 상, 좌 순서
			int[] dir_y = {0,1,0,-1};
			int[] cor_x = {1,1,-1,-1};
			int[] cor_y = {1,-1,-1,1};
			int[][] Bcnt = new int [N][N];
			
			// Bcnt 채우기
			for (int i=0; i<N; i++) {
				for (int j=0; j<N; j++) {
					
					boolean isPark = false;
					if (map[i][j] == 'G') {
						isPark = true;
					}
					
					if (!isPark) {		// B인 곳에 대해서만 연산 시작
						
						boolean arPark = false;
						for (int k=0; k<4; k++) {
							int sw_x = i + dir_x[k];
							int sw_y = j + dir_y[k];
							if (sw_x<0 || sw_x>=N || sw_y<0 || sw_y>=N) {
								continue;
							}
							if (map[sw_x][sw_y] == 'G') {
								arPark = true;		
							}
							
						}
						
						for (int m=0; m<4; m++) {
							int cr_x = i + cor_x[m];
							int cr_y = j + cor_y[m];
							if (cr_x<0 || cr_x>=N || cr_y<0 || cr_y>=N) {
								continue;
							}
							if (map[cr_x][cr_y] == 'G') {
								arPark = true;
							}
							
						}
						if (arPark) {
							Bcnt[i][j] = 2;
						}
						if (!arPark) {
							Bcnt[i][j] = -1;
							for (int l=0; l<N; l++) {
								if (map[i][l] == 'B') {
									Bcnt[i][j]++;
								}
								if (map[l][j] == 'B') {
									Bcnt[i][j]++;
								}
							}
							
														
							
							
							
						}	
							
							
							
							
							
//							for (int n=1; n<N-i; n++) {
//								if (i+1>=N) {
//									break;
//								}
//								if (map[i+n][j] == 'B') {
//									Bcnt[i][j] ++;
//								}
//							}
//							for (int n=1; n<N-i; n++) {
//								if (i-1<0) {
//									break;
//								}
//								if (map[i-n][j] == 'B') {
//									Bcnt[i][j] ++;
//								}
//							}
//							for (int o=1; o<N-j; o++) {
//								if (j+1>=N) {
//									break;
//								}
//								if (map[i][j+o] == 'B') {
//									Bcnt[i][j] ++;
//								}
//							}
//							for (int o=1; o<N-j; o++) {
//								if (j-1 <0) {
//									break;
//								}
//								System.out.println(i+" "+(j-o));
//								if (map[i][j-o] == 'B') {
//									Bcnt[i][j] ++;
//								}
//							}
//							
//						}
					}
				}
			}
			
			int result=0;
			for (int p=0; p<N; p++) {
				for (int q=0; q<N; q++) {
					if (Bcnt[p][q] >= result) {
						result = Bcnt[p][q];
					}
				}
			}
			
			System.out.printf("#%d %d\n", tc, result);
			
		}
		
	}
}

// 이 밑은 txt 파일 인풋하는 방법을 모르고 있을 때 풀던 흔적입니다.

//
//// 변수 초기화
//char [][] map = null;
//int result;
//int Gcnt;
//int Bcnt;
//
//// 테스트 케이스 입력
//Scanner sc = new Scanner(System.in);
//int repeat = Integer.parseInt(sc.nextLine());
//
//
//for (int i=0; i<repeat; i++) {
//	// 배열 크기 입력
//	int size = Integer.parseInt(sc.nextLine());
//	
//	// 배열 값 입력
//	map = new char [size][size];
//	for (int j=0; j<size; j++) {
//		String S = sc.nextLine();
//		for (int k=0; k<size; k++) {
//			map[j][k] = S.charAt(k);
//		}
//	}
//
//	// 하나씩 검증 돌입
//	int[] dir_x = {1,0,-1,0};
//	int[] dir_y = {0,1,0,-1};
//	int[] cor_x = {1,1,-1,-1};
//	int[] cor_y = {1,-1,-1,1};
//	
//	for (int l=0; l<size; l++) {
//		for (int m=0; m<size; m++) {
//			
//			
//			
//			// 인접구역에 G가 있을 시 result 2
//			
//			
//		}
//	}
//	
//	
//	
//}