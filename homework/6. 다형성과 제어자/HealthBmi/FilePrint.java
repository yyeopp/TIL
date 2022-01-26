package com.ssafy.hw06.step2;

import java.io.FileWriter;
import java.io.IOException;

public class FilePrint extends PrintProcess{

	@Override
	public void print() throws IOException {
		FileWriter fw = new FileWriter("fileout_hw06.txt");	
		
		

		fw.write("이름" + "\t\t" + "측정일" + "\t\t" + "몸무게" + "\t" + "키"+"\n");
		for (int i = 0; i < HealthBmi.scanData().length; i++) {
			String temp = HealthBmi.scanData()[i].toString();
			fw.write(temp+"\n");
		}
		
		
		fw.flush();
		fw.close();
		System.out.println("파일입력 완료");
		System.out.println("파일입력 완료");
		System.out.println("파일입력 완료");
	}

	
	
	
	
}
