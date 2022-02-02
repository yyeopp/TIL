package com.company.ws06.step3;

public interface IBookManager {
	public void add(Book book);
	public void remove(String isbn);
	public void getList();
	public void searchByIsbn(String isbn);
	public void searchByTitle(String title);
	public void getMagazines();
	public void getBooks();
	public void getTotalPrice();
	public void getPriceAvg();
}
