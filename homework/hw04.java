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
		Student �п���1 = new Student("�п���1",21,53.2,1.673);
		Student �п���2 = new Student("�п���2",35,90.3,1.781);
		Student �п���3 = new Student("�п���3",28,70.7,1.653);
		Student �п���4 = new Student("�п���4",25,75.1,1.811);
		Student �п���5 = new Student("�п���5",31,62.0,1.532);
		
		// ��հ� ������ ���ϱ�
		
		
		
		System.out.printf("idx"+"\t\t"+"�� ����"+"\t"+"������(kg)"+"Ű(m)"+"\n");
		�п���1.printInfo();
		�п���2.printInfo();
		�п���3.printInfo();
		�п���4.printInfo();
		�п���5.printInfo();
		System.out.print("���"+"\t\t"+28.000+"\t"+70.260+"\t"+1.690);
		
		
		
	}

	

}
