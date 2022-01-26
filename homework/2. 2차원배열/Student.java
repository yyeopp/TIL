package com.company.ws05;


public class Student {
	
	String name;
	int scoreKor;
	int scoreEng;
	int scoreMat;
	int totalScore;
	float avg;
	
	public Student(String name, int scoreKor, int scoreEng, int scoreMat) {
		this.name = name;
		this.scoreKor = scoreKor;
		this.scoreMat = scoreEng;
		this.scoreEng = scoreMat;
	}
	
	
	int getTotal() {
		return totalScore = scoreKor + scoreEng + scoreMat;
	}
	
	float getAvg() {
		return avg = totalScore / 3;
	}
	
	void getInfo() {
		System.out.println(name +"\t"+ scoreKor +"\t\t" + scoreEng+"\t\t"+scoreMat+"\t\t"+getTotal()+"\t"+getAvg()); 
	}
	
	
	
}
