package com.ssafy.hw02;

import java.util.Arrays;

public class BmiCalculator {
	public static void main(String[] args) {
		// input.txt를 읽어와야 하지만 도저히 구현을 못해서 일단 그냥 배열로 처리
		int [][] dataInput = {{22,100,180}, {22,90,180}, {22,85,180}, {22,80,180}, {22,70,180}, {22,60,180}};
		
		// 나이를 ages 배열에 저장
		int[] ages = new int[dataInput.length];
		for (int i=0; i<dataInput.length; i++) {
			ages[i] = dataInput[i][0];
		}
		
		// 몸무게를 weight 배열에 저장
		float[] weight = new float[dataInput.length];
		for (int i=0; i<dataInput.length; i++) {
			weight[i] = dataInput[i][1];
		}
		
		// 키를 height 배열에 저장
		float[] height = new float[dataInput.length];
		for (int i=0; i<dataInput.length; i++) {
			height[i] = (float)0.01*dataInput[i][2];
		}
		
		// BMI 계산기
		class Calc {
			float BMI;
			float calcBMI (float w, float h) {
				BMI = w/(h*h);
				return BMI;
			}
		}
		
		// BMI를 계산한 BR 배열 만들기
		Calc result = new Calc();
		float [] BR = new float[weight.length];
		for (int i=0; i<weight.length; i++) {
			BR[i] = result.calcBMI(weight[i], height[i]);
		}
		
		// BMI 판독해서 비만도를 biman 배열에 저장
		String [] biman = new String[BR.length];
		for (int i=0; i<BR.length; i++) {
			if (BR[i]>30) {
				biman[i] = "고도비만";
			}
			if (25<BR[i] && BR[i]<=30) {
				biman[i] = "비만";
			}
			if (23<BR[i] && BR[i]<=25 ) {
				biman[i] = "과체중";
			}
			if (18.5<BR[i] && BR[i]<=23) {
				biman[i] = "정상";
			}
			if (BR[i]<=18.5) {
				biman[i] = "저체중";
			}
		}
		
		
		// 결과 출력
		for (int i=0; i<biman.length; i++) {
			System.out.printf("BMI 지수는 %f로 <%s>입니다.", BR[i], biman[i]);
			System.out.println();
		}
		
		
		
		
		
					
	}
}
