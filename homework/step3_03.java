package com.ssafy.ws01.step3;

import java.util.Random;
import java.util.Scanner;

public class GameTest {
	public static void main(String[] args) {
		// 시작 부분
		System.out.println("가위바위보 게임을 시작합니다. 아래 보기 중 하나를 고르세요.");
		System.out.println("1. 5판 3승"+"\n"+"2. 3판 2승"+"\n"+"3. 1판 1승");
		Scanner sc = new Scanner(System.in);		// Scanner 한번 호출해놨으면
		System.out.print("번호를 입력하세요.");
		int num = Integer.parseInt(sc.nextLine());		// 이유는 잘 모르겠지만 nextInt()쓰면 에러가 잘 뜬다고. 그냥 Integer.parseInt()를 하나 더 사용
		
		// 3판 2선 시작
		if (num == 2) {
			int wincnt = 0;
			int loscnt = 0;
			for (int i=0; i<20; i++) {			// 계속 비기는 경우를 상정한다고 할 때 i는 무한히 커질 수 있으므로, 여기서는 while이 더 적절하다. 그러면 break를 사용할 필요도 없다
				System.out.print("가위바위보 중 하나 입력: ");
				String user = sc.nextLine();		// 그냥 계속 이렇게 사용하면 된다. nextLine() 메서드를 주로 활용하게 될 것
				Random r = new Random();
				int oppo = r.nextInt(3)+1;
//				int oppo = (int)(Math.random()*3)+1;
				if (user.equals("가위")) {		// user는 String 클래스이기 때문에 == 연산자를 쓸 수 없다.
					if (oppo==1) {
						System.out.println("비겼습니다!!");
					}
					else if (oppo==2) {
						System.out.println("졌습니다!!");
						loscnt ++;
					}
					else if (oppo==3) {
						System.out.println("이겼습니다!!");
						wincnt ++;
					}
				}
				if (user.equals("바위")) {
					if (oppo==1) {
						System.out.println("이겼습니다!!");
						wincnt ++;
					}
					else if (oppo==2) {
						System.out.println("비겼습니다!!");
					}
					else if (oppo==3) {
						System.out.println("졌습니다!!");
						loscnt ++;
					}
				}					
				if (user.equals("보")) {
					if (oppo==1) {
						System.out.println("졌습니다!!");
						loscnt	++;
					}
					else if (oppo==2) {
						System.out.println("이겼습니다!!");
						wincnt ++;
					}
					else if (oppo==3) {
						System.out.println("비겼습니다!!");
					}
				}
				if (wincnt==2) {
					System.out.println("### 사용자 승!!!");
					break;
				}
				if (loscnt==2) {
					System.out.println("### 컴퓨터 승!!!");
					break;
				}
				
					
			}				
		
				
		}
			
			
		
	}
}
