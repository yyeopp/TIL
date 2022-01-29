package com.ssafy.ws02;

public class Student extends MinStudent {

	public Student(String name, int kor, int eng, int mat) {
		super(name, kor, eng, mat);
	}
	
	public int getSum() {
		int sum = getKor()+getEng()+getMat();
		return sum;
	}
	
	public double getAverage() {
		double average = (getKor()+getEng()+getMat()) / 3;
		return average;
	}
	
	@Override
	public String toString() {
		return name+"\t"+getKor()+"\t"+getEng()+"\t"+getMat();
	}
}
