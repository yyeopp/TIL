package com.ssafy.ws02.step3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Building2 {		// ���۵帮���� ��ĳ�ʺ��� ������!  �׳� ����� ������ print�ϴ� �ͺ��� stringbuilder Ȱ���ؼ� ���� �� �ѹ��� print�ϴ� �� �� ������! 

	public static void main(String[] args) throws NumberFormatException, IOException {	// �ڹ� API �� Ȱ���ؼ�, sysout ���� �͵������ Ŭ����, ����, �޼��带 �� �����ϰ� Ȱ���غ��� ������ ���� ��
		long stime = System.nanoTime();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/ssafy/ws02/step3/input.txt")));	// ���۵帮���� Ȱ���ϴ� ���. ��ĳ�ʸ� ����� �� �ִ�
		
//		System.setIn(new FileInputStream("src/com/ssafy/ws02/step3/input.txt"));
//		Scanner sc = new Scanner(System.in);	
		int T = Integer.parseInt(in.readLine());	// �̷��� txt������ ù�� ù ���� �� int�� �о���� ��.
		StringBuilder sb = new StringBuilder();		// ���ϴ� String�� build�ϴ� ����.
		for (int tc=1; tc<=T; tc++); {
			sb.append('#').append(T).append(" ");
			int N = Integer.parseInt(in.readLine());	// �̷��� �� �˾Ƽ� �� �������� int�� �о��ش�..
			char [][] map = new char[N][N];
			for (int i=0; i<N; i++) {
//				String str = in.readLine(); // ù�� �״�� �о��. G B G G B B �̷���
				StringTokenizer st = new StringTokenizer(in.readLine(), " ");		// util API���� ã�ƿ´�. str�� token���� �ٲٴ� ��. ������ ������ �ȴٰ� ���
				for (int j=0; j<N; j++) {		// ���ٿ� �ִ� str ������ ��� ������ �ʿ䰡 ����. �׳� tokenizer ������ ���� �Է��ص� ��	
					map[i][j] = st.nextToken().charAt(0); 	// next()�� ���� ���� str�� �о�´�. charAt(0)���� �� str�� ù ���ڸ� char�� ���´�.
				}	 
			}
			
			int result = 0;
			for (int i=0; i<N; i++) {
				for (int j=0; j<N; j++) {
					boolean isPark = false;
					if(map[i][j] == 'B') {
					
						if(i-1 >=0 && map[i-1][j] == 'G') {
							isPark = true;
						}	
						
						if(!isPark) {	
						
					
						}
					}
					
				}

			}
//			System.out.println("#"+T+" "+result);
			sb.append(result);		// �ϳ��� ����Ʈ�ϴ� �� ��ü
			
			
			
//			for (int i =0; i<N; i++) {
//				for (int j =0; j<N; j++) {
//					System.out.print(map[i][j] + " ");
//				}
//				System.out.println();
//			}
		}
		System.out.println(sb);		// �̰� �� ��û ����
		System.out.println(System.nanoTime() - stime); // ���α׷� ���� �ð�  1156534  (1���� 70���� ����)
	}

}
