

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		
		// ���� �ʱ�ȭ
		char [][] map = null;
		int result;
		int Gcnt;
		int Bcnt;
		
		// �׽�Ʈ ���̽� �Է�
		Scanner sc = new Scanner(System.in);
		int repeat = Integer.parseInt(sc.nextLine());
		
		
		for (int i=0; i<repeat; i++) {
			// �迭 ũ�� �Է�
			int size = Integer.parseInt(sc.nextLine());
			
			// �迭 �� �Է�
			map = new char [size][size];
			for (int j=0; j<size; j++) {
				String S = sc.nextLine();
				for (int k=0; k<size; k++) {
					map[j][k] = S.charAt(k);
				}
			}

			// �ϳ��� ���� ����
			int[] dir_x = {1,0,-1,0};
			int[] dir_y = {0,1,0,-1};
			int[] cor_x = {1,1,-1,-1};
			int[] cor_y = {1,-1,-1,1};
			
			for (int l=0; l<size; l++) {
				for (int m=0; m<size; m++) {
					
					
					
					// ���������� G�� ���� �� result 2
					
					
				}
			}
			
			
			
		}
		
		
	}

}
