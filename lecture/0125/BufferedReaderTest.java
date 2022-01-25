package com.company.exception;

import java.io.*;

public class BufferedReaderTest {
	public static void main(String[] args) {
//		Reader r = new Reader();		// Reader 클래스는 abstract 라서 객체 생성이 안된다. 다형성을 활용해서 하위 클래스 객체를 참조하자. API에서 subclass의 목록을 보고 적합한 것을 활용
		
//		InputStream is = System.in;					// API를 보면 또 InputStream이 abstract. subclass 확인해봐야 함
//		Reader r = new InputStreamReader(is);		// 다형성으로 참조. API상 ISR의 생성자를 확인해보면 매개변수가 InputStream. 만들어줘야 함
//		BufferedReader in = new BufferedReader(r);
		
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));	// 위 세줄을 한 줄로 정리할 수 있다. 참조관계를 한방에 정리한 것에 해당
			System.out.println("파일 이름: ");			// ISR에 표준출력인 system.in을 집어넣음
			String filename = in.readLine();		// readLine이 String을 가져온다는 것도 ,API. 근데 
			BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));	// FNFE를 유발할 수 있는 코드. ISR 입력부분에 새로운 객체를 생성 API, subclass 적극 활용한 것
			FileWriter fw = new FileWriter("copy.txt");		// API 찾아보자. 새로운 객체 생성이 가능하다.
			
			String str = null;
			while((str = fin.readLine()) != null) {		// 파일의 끝부분까지 계속 읽으라는 뜻.
				System.out.println(str);		// 파일 경로를 잘 입력하면, 해당 파일을 쭉 읽어내려서 콘솔에 표시해준다.
				fw.write(str + "\n");				// FW가 원래 가지고 있는 메서드. 똑같이생긴 파일을 복사해준다!!
			}
			fw.flush();		// copy.txt라는 파일이 src 경로 밖에 생겨난다.
		
		} catch (FileNotFoundException e) {	// FNFE는, IOE의 서브클래스에 해당. 다중캐치블럭을 만들려고 시도할 때, IOE보다 FNFE를 무조건 먼저 써줘야 한다. 컴파일에러 유발
			e.printStackTrace();
		} catch (IOException e) {	// 입력 관련한 객체는 항상 IOE의 위험성을 가진다. 
			e.printStackTrace();
		} // 지금 못한 거: finally 구문으로 열어놓은 파일 닫아주기. fin이랑 fw에 노란줄 있는게 사용한 리소스 반납하라는 경고에 해당한다.
		
	}

}
