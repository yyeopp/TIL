package com.ssafy.ws02.step3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Building2 {		// 버퍼드리더가 스캐너보다 빠르다!  그냥 결과문 여러번 print하는 것보다 stringbuilder 활용해서 만들어낸 뒤 한번에 print하는 게 더 빠르다! 

	public static void main(String[] args) throws NumberFormatException, IOException {	// 자바 API 잘 활용해서, sysout 같은 것들까지도 클래스, 변수, 메서드를 잘 구분하고 활용해보는 습관을 가질 것
		long stime = System.nanoTime();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/ssafy/ws02/step3/input.txt")));	// 버퍼드리더를 활용하는 방법. 스캐너를 대신할 수 있다
		
//		System.setIn(new FileInputStream("src/com/ssafy/ws02/step3/input.txt"));
//		Scanner sc = new Scanner(System.in);	
		int T = Integer.parseInt(in.readLine());	// 이러면 txt파일의 첫줄 첫 공백 전 int를 읽어오는 것.
		StringBuilder sb = new StringBuilder();		// 원하는 String을 build하는 구문.
		for (int tc=1; tc<=T; tc++); {
			sb.append('#').append(T).append(" ");
			int N = Integer.parseInt(in.readLine());	// 이러면 또 알아서 그 다음줄의 int를 읽어준다..
			char [][] map = new char[N][N];
			for (int i=0; i<N; i++) {
//				String str = in.readLine(); // 첫줄 그대로 읽어옴. G B G G B B 이렇게
				StringTokenizer st = new StringTokenizer(in.readLine(), " ");		// util API에서 찾아온다. str을 token으로 바꾸는 것. 공백이 기준이 된다고 명시
				for (int j=0; j<N; j++) {		// 윗줄에 있는 str 변수는 사실 도입할 필요가 없다. 그냥 tokenizer 안으로 직접 입력해도 됨	
					map[i][j] = st.nextToken().charAt(0); 	// next()는 공백 이전 str을 읽어온다. charAt(0)으로 그 str의 첫 문자를 char로 따온다.
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
			sb.append(result);		// 하나씩 프린트하는 걸 대체
			
			
			
//			for (int i =0; i<N; i++) {
//				for (int j =0; j<N; j++) {
//					System.out.print(map[i][j] + " ");
//				}
//				System.out.println();
//			}
		}
		System.out.println(sb);		// 이게 또 엄청 빠름
		System.out.println(System.nanoTime() - stime); // 프로그램 실행 시간  1156534  (1보다 70배쯤 빠름)
	}

}
