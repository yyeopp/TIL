package com.company.ws06.step3;


public class BooktTest {

	public static void main(String[] args) {
		BookManagerImpl.getInstance().add(new Book("21424","Java Pro","김하나", "jaen.kr", 15000,"Java 기본 문법"));
		BookManagerImpl.getInstance().add(new Book("21425", "Java Pro2", "김하나","jaen.kr",25000,"Java 응용"));
		BookManagerImpl.getInstance().add(new Book("35355", "분석설계","소나무","jaen.kr",30000,"SW 모델링"));
		BookManagerImpl.getInstance().add(new Magazine("45678", "월간 알고리즘", "홍길동", "jaen.kr", 10000, "1월 알고리즘",2021, 1));
		BookManagerImpl.getInstance().getList();
		BookManagerImpl.getInstance().getBooks();
		BookManagerImpl.getInstance().getMagazines();
		
		BookManagerImpl.getInstance().searchByTitle("Java");
		BookManagerImpl.getInstance().getTotalPrice();
		BookManagerImpl.getInstance().getPriceAvg();
	}

}
