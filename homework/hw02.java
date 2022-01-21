package com.ssafy.hw02;

import java.util.Arrays;

public class BmiCalculator {
	public static void main(String[] args) {
		// input.txt�� �о�;� ������ ������ ������ ���ؼ� �ϴ� �׳� �迭�� ó��
		int [][] dataInput = {{22,100,180}, {22,90,180}, {22,85,180}, {22,80,180}, {22,70,180}, {22,60,180}};
		
		// ���̸� ages �迭�� ����
		int[] ages = new int[dataInput.length];
		for (int i=0; i<dataInput.length; i++) {
			ages[i] = dataInput[i][0];
		}
		
		// �����Ը� weight �迭�� ����
		float[] weight = new float[dataInput.length];
		for (int i=0; i<dataInput.length; i++) {
			weight[i] = dataInput[i][1];
		}
		
		// Ű�� height �迭�� ����
		float[] height = new float[dataInput.length];
		for (int i=0; i<dataInput.length; i++) {
			height[i] = (float)0.01*dataInput[i][2];
		}
		
		// BMI ����
		class Calc {
			float BMI;
			float calcBMI (float w, float h) {
				BMI = w/(h*h);
				return BMI;
			}
		}
		
		// BMI�� ����� BR �迭 �����
		Calc result = new Calc();
		float [] BR = new float[weight.length];
		for (int i=0; i<weight.length; i++) {
			BR[i] = result.calcBMI(weight[i], height[i]);
		}
		
		// BMI �ǵ��ؼ� �񸸵��� biman �迭�� ����
		String [] biman = new String[BR.length];
		for (int i=0; i<BR.length; i++) {
			if (BR[i]>30) {
				biman[i] = "����";
			}
			if (25<BR[i] && BR[i]<=30) {
				biman[i] = "��";
			}
			if (23<BR[i] && BR[i]<=25 ) {
				biman[i] = "��ü��";
			}
			if (18.5<BR[i] && BR[i]<=23) {
				biman[i] = "����";
			}
			if (BR[i]<=18.5) {
				biman[i] = "��ü��";
			}
		}
		
		
		// ��� ���
		for (int i=0; i<biman.length; i++) {
			System.out.printf("BMI ������ %f�� <%s>�Դϴ�.", BR[i], biman[i]);
			System.out.println();
		}
		
		
		
		
		
					
	}
}
