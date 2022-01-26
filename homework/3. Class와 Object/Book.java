package com.ssafy.ws03.step3;

public class Book {
	public String isbn;
	public String title;
	public String author;
	public String publisher;
	public int price;
	public String desc;
	
	
	public Book(String isbn, String title, String author, String publisher, int price, String desc) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return isbn + "\t" + "| " + title + "\t" + "| " + author + "\t" + "| " + publisher + "\t" + "| " + price + "\t" + "| " + desc;
	}
	
	
}
