package com.ssafy.hw06.step2;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		FilePrint f = new FilePrint();
		SystemPrint s = new SystemPrint();
		
		System.out.println(HealthBmi.scanData()[1]);
		
		String inputName = "라이언";
		s.print();
		System.out.println("\n"+"학원생 이름을 입력하세요. >> "+"\n" + inputName);
		s.printWho(inputName);
		f.print();
		
	}

}
