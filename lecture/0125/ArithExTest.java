package com.company.exception;

import java.util.Random;

public class ArithExTest {

	public static void main(String[] args) {
		Random random = new Random();
		int num = random.nextInt(3);	//0,1,2 중에 아무거나 나올 것.
//		int div = 10 / num;			// 0이 랜덤으로 걸리는 순간 런타임에러가 발생한다. ArithmeticException
		
//		try {
//			int div = 10/num;
//			System.out.println("result : "+div);
//		}catch (ArithmeticException e) {		// 에러를 캐치해서 다른 문장을 출력.
//			e.printStackTrace();
//			System.out.println("숫자를 0으로 나눌 수 없음");		// 근데 이거 기본적으로 런타임에러라서 다른 방식으로 해결하는 게 맞다
//		}		// 지금은 에러를 유발한 다음에 땜질하는 방식에 불과한 것. 좋은 방법이 아니다!!
		
		
		if (num == 0) {
			System.out.println("숫자를 0으로 나눌 수 없습니다");			// 에러발생 자체를 차단. 로직으로 처리하는 방법
			return;		// 밑으로 내려가지 못하게 막아버림
		}
		int div = 10/ num;
		System.out.println("result : " + div);		// 아니면 아예 랜덤숫자에서 0을 빼버리는 방법 정도가 있다. nextInt(2)+1;
		
	}	// 다시말해서, 런타임에러는 '무조건' 로직으로 처리할 수 있는 부분인데 왜 예외발생을 용인하고나서 그걸 무마하는 방식을 사용하나? 이런말

}
