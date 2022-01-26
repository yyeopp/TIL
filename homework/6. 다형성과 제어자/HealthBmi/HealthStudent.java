package com.company.hw06.step2;

public class HealthStudent {
	private String name;
	private String date;
	private float weight;
	private float height;

	public HealthStudent(String name, String date, float weight, float height) {
		this.name = name;
		this.date = date;
		this.weight = weight;
		this.height = height;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public float getWeight() {
		return weight;
	}

	public float getHeight() {
		return height;
	}
	@Override
	public String toString() {
		return name + "\t" + date + "\t" + weight +"\t"+height;
	}

}
