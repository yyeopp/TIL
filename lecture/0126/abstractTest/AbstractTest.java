package com.company.abstractTest;

import java.io.IOException;
import java.util.Calendar;

public class AbstractTest {

	public static void main(String[] args) throws IOException {
			
//		Calendar cal = new Calendar();	//Calendar는 추상클래스라서 객체 만드는 게 불가능
		// API 열어보면, 추상메서드만 8개있음. 일일이 지정하는게 말이 안됨
		// 보통 이런경우 이거를 상속받아서 좀더 세분화해놓은 클래스가 존재하고 있음.
//		Calendar cal = new GregorianCalendar();		// 즉, 다형성을 이용해서 이렇게 만들면 된다. 만들기도 쉽고 쓰기도 쉽고.
		
		
		Calendar cal = Calendar.getInstance();		// 자신의 객체를 리턴하는 static method를 이용하는 방법.
		int y = cal.get(Calendar.YEAR);		// Calendar 클래스의 객체의 API를 찾아보자. get(int field)의 메서드가 있는데, 그러면 get 안에다가 Calendar클래스의 필드네임, 속성이름을 집어넣으면 된다.
		int m =cal.get(Calendar.MONTH)+1;		// 근데 이거 실행하면 0월로 나오는데, MONTH가 Jan~Dec로 관리되고 이게 index로 설정되어있어서 Jan = 0이기 때문. 
		int d =cal.get(Calendar.DATE);		// MONTH+1로 해결한다.
		int h =cal.get(Calendar.HOUR_OF_DAY);
		int mi =cal.get(Calendar.MINUTE);
		int s =cal.get(Calendar.SECOND);
		System.out.println("오늘은 "+y+"년 "+m+"월 "+d+"일 "+h+"시 "+ mi+"분 "+s+"초입니다");
		
//		Runtime r = new Runtime();		// 얘는 뒤져보면 싱글톤이라서 자신의 객체를 리턴하는 static method를 찾아와야 한다.
		Runtime r = Runtime.getRuntime();		//  이러면 됨.
		
//		Process p = new Process();	//추상클래스인데 서브클래스도 따로 존재하지 않는 경우에 해당함 + 근데 자신의 객체를 리턴하는 static method도 따로 없다. == 객체를 만들 수가 없다.. 사용할 수가 없다?
		Process p = r.exec("calc.exe");		// Runtime 클래스에서 Process 쓰는 방법을 찾아올 수 있음. 이렇게 하면 계산기가 켜진다..!
		
		
	}

}
