package com.company.ws06.step3;

import java.util.*;


public class BookManagerImpl extends Book implements IBookManager {
	private static int MAX_SIZE = 100;

	private static Set<Book> books = new HashSet<Book>();

	public static final BookManagerImpl INSTANCE = new BookManagerImpl();
	public static BookManagerImpl getInstance() {
		return INSTANCE;
	}
	
	
	public void add(Book book) {
		if (books.size() == MAX_SIZE) {
			System.out.println("더 이상 추가할 수 없습니다.");
		} else {
			books.add(book);
		}
	}

	public void remove(String isbn) {
		Iterator<Book> iter = books.iterator();

		System.out.printf("******************도서삭제: %s******************\n", isbn);

		while (iter.hasNext()) {
			Book temp = iter.next();
			if (temp.getIsbn() == isbn) {
				books.remove(temp);
				break;
			}
		}
		INSTANCE.getList();

	}

	public void getList() {
		Iterator<Book> iter = books.iterator();
		System.out.println("******************도서목록******************");
		while (iter.hasNext()) {
			System.out.println(iter.next().toString());
		}
	}

	public void searchByIsbn(String isbn) {
		Iterator<Book> iter = books.iterator();
		System.out.printf("******************도서조회: %s******************\n", isbn);

		while (iter.hasNext()) {
			Book temp = iter.next();
			if (temp.getIsbn() == isbn) {
				System.out.println(temp.toString());
				break;
			}
		}

	}

	public void searchByTitle(String title) {
		Iterator<Book> iter = books.iterator();
		System.out.printf("******************도서 제목 포함검색: %s******************\n", title);

		while (iter.hasNext()) {
			Book temp = iter.next();
			if (temp.getTitle().contains(title)) {
				System.out.println(temp.toString());

			}
		}

	}

	public void getBooks() {
		Iterator<Book> iter = books.iterator();
		System.out.println("******************일반 도서 목록******************");

		while (iter.hasNext()) {
			Book temp = iter.next();
			if (!(temp instanceof Magazine)) {
				System.out.println(temp.toString());

			}
		}
	}

	public void getMagazines() {
		Iterator<Book> iter = books.iterator();
		System.out.println("******************잡지 목록******************");

		while (iter.hasNext()) {
			Book temp = iter.next();
			if (temp instanceof Magazine) {
				System.out.println(temp.toString());

			}
		}
	}

	public int totalPrice() {
		Iterator<Book> iter = books.iterator();
		int total = 0;
		while (iter.hasNext()) {
			Book temp = iter.next();
			total += temp.getPrice();
		}
		return total;
	}
	public void getTotalPrice() {
		System.out.println("도서 가격 총합: " + totalPrice());
	}

	public double priceAvg() {
		
		double avg = totalPrice() / books.size();
		return avg;
	}
	public void getPriceAvg() {
		System.out.println("도서 가격 평균: " + priceAvg());
	}

}