package com.ssafy.happyhouse.model;

public class ListParameterDto {

	private String key;
	private String word;
	private int start;
	private int currentPerPage;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCurrentPerPage() {
		return currentPerPage;
	}

	public void setCurrentPerPage(int currentPerPage) {
		this.currentPerPage = currentPerPage;
	}

}
