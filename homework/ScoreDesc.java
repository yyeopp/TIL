package com.company.ws05;

public class ScoreDesc {
	public static void main(String[] args) {
		
		Student s1 = new Student("라이언", 56, 78, 91);
		Student s2 = new Student("케빈",89,43,60);
		Student s3 = new Student("장길산",76,88,79);
		Student s4 = new Student("김두한",69,92,64);
		
		Student [] Students = {s1,s2,s3,s4};
		
		
		int totalKor=0;
		int totalEng=0;
		int totalMat=0;
		
		
		for (int j = 0; j < Students.length; j++) {
			totalKor += Students[j].scoreKor;
		}
		for (int j = 0; j < Students.length; j++) {
			totalEng += Students[j].scoreEng;
		}
		for (int j = 0; j < Students.length; j++) {
			totalMat += Students[j].scoreMat;
		}
		
		float avgKor = totalKor / Students.length;
		float avgEng = totalEng / Students.length;
		float avgMat = totalMat / Students.length;
		
		System.out.println("이름"+"\t"+"국어점수"+"\t"+"영어점수"+"\t"+"수학점수"+"\t"+"총합"+"\t"+"평균");
		
		for (int i = 0; i < Students.length; i++) {
			Students[i].getInfo();
		}
		System.out.printf("\t%d/%f\t%d/%f\t%d/%f\t\t",totalKor,avgKor,totalEng,avgEng,totalMat,avgMat);
		
		
	}

}
