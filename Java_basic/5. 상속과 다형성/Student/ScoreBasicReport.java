package com.ssafy.ws02;

import java.io.*;


public class ScoreBasicReport {
	public static void main(String[] args) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		Student s1 = new Student("라이언", 56, 78, 91);
		Student s2 = new Student("케빈",89,43,60);
		Student s3 = new Student("장길산",76,88,79);
		Student s4 = new Student("김두한",69,92,64);
		
		
		bw.write("이름"+"\t"+"국어"+"\t"+"영어"+"\t"+"수학"+"\t"+"합계"+"\t"+"평균"+"\n");
		bw.write(s1.toString()+"\t"+s1.getSum()+"\t"+s1.getAverage());
		bw.write("\n"+s2.toString()+"\t"+s2.getSum()+"\t"+s2.getAverage()+"\n");
		bw.write(s3.toString()+"\t"+s3.getSum()+"\t"+s3.getAverage()+"\n");
		bw.write(s4.toString()+"\t"+s4.getSum()+"\t"+s4.getAverage());
		bw.flush();
	}
}
