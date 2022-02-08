

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("src/com/ssafy/ws02/step3/input.txt"));	// �̰ŵ� api���� ã�ƿ;� ��. java.io���� inputstream Ŭ������ ����. System.in���� in�� �Ӽ��� �ٲ۴ٴ� ����
		Scanner sc = new Scanner(System.in);	// throws ����ó���� �ϴ� ���߿� �����
		int T = sc.nextInt();	// �̷��� txt������ ù�� ù ���� �� int�� �о���� ��.
		
		for (int tc=1; tc<=T; tc++) {
			int N = sc.nextInt();	// �̷��� �� �˾Ƽ� �� �������� int�� �о��ش�..
			char [][] map = new char[N][N];
			for (int i=0; i<N; i++) {
				for (int j=0; j<N; j++) {		// ����� ����ȯ�� ���� ������ ���� �ȴ�. String�� char ���� ����ȯ�� �ȵǰ�, boolean�� int ���� ����ȯ�� �ȵȴ�.
					map[i][j] = sc.next().charAt(0); 	// next()�� ���� ���� str�� �о�´�. charAt(0)���� �� str�� ù ���ڸ� char�� ���´�.
				}	  // �̷��Ը� �ϸ� ���� �Է� ����...
			}
			
			
			
			int[] dir_x = {1,0,-1,0};   // ��, ��, ��, �� ����
			int[] dir_y = {0,1,0,-1};
			int[] cor_x = {1,1,-1,-1};
			int[] cor_y = {1,-1,-1,1};
			int[][] Bcnt = new int [N][N];
			
			// Bcnt ä���
			for (int i=0; i<N; i++) {
				for (int j=0; j<N; j++) {
					
					boolean isPark = false;
					if (map[i][j] == 'G') {
						isPark = true;
					}
					
					if (!isPark) {		// B�� ���� ���ؼ��� ���� ����
						
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

// �� ���� txt ���� ��ǲ�ϴ� ����� �𸣰� ���� �� Ǯ�� �����Դϴ�.

//
//// ���� �ʱ�ȭ
//char [][] map = null;
//int result;
//int Gcnt;
//int Bcnt;
//
//// �׽�Ʈ ���̽� �Է�
//Scanner sc = new Scanner(System.in);
//int repeat = Integer.parseInt(sc.nextLine());
//
//
//for (int i=0; i<repeat; i++) {
//	// �迭 ũ�� �Է�
//	int size = Integer.parseInt(sc.nextLine());
//	
//	// �迭 �� �Է�
//	map = new char [size][size];
//	for (int j=0; j<size; j++) {
//		String S = sc.nextLine();
//		for (int k=0; k<size; k++) {
//			map[j][k] = S.charAt(k);
//		}
//	}
//
//	// �ϳ��� ���� ����
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
//			// ���������� G�� ���� �� result 2
//			
//			
//		}
//	}
//	
//	
//	
//}