package com.company.hw02;

public class HealthStudent {
	private String name;
	private int date;
	private int weight;
	
	public HealthStudent(String name, int date, int weight) {
		this.name = name;
		this.date = date;
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}

	public int getDate() {
		return date;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "이름: "+name+"\t"+"측정 일자: "+date+"\t"+"몸무게(kg): "+weight;
	}
		
		
}


