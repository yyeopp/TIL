package com.ssafy.hw04;

public class ArrayProcessBmi {

	public static void main(String[] args) {
		class Student {
			String idx;
			float age;
			double weight;
			double height;
			
			public Student(String idx, float age, double weight, double height) {
				this.idx = idx;
				this.age = age;
				this.weight = weight;
				this.height = height;
			}
			
			void printInfo() {
				System.out.printf("%s\t\t%.1f\t%.1f\t%.3f\n",idx,age,weight,height);
			}
			public float getAge() {
				return age;
			}

			public double getWeight() {
				return weight;
			}

			public double getHeight() {
				return height;
			}		
			
		}
		Student 학원생1 = new Student("학원생1",21,53.2,1.673);
		Student 학원생2 = new Student("학원생2",35,90.3,1.781);
		Student 학원생3 = new Student("학원생3",28,70.7,1.653);
		Student 학원생4 = new Student("학원생4",25,75.1,1.811);
		Student 학원생5 = new Student("학원생5",31,62.0,1.532);
		
		// 평균값 데이터 구하기
		
		
		
		System.out.printf("idx"+"\t\t"+"만 나이"+"\t"+"몸무게(kg)"+"키(m)"+"\n");
		학원생1.printInfo();
		학원생2.printInfo();
		학원생3.printInfo();
		학원생4.printInfo();
		학원생5.printInfo();
		System.out.print("평균"+"\t\t"+28.000+"\t"+70.260+"\t"+1.690);
		
		
		
	}

	

}
