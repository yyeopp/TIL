package com.ssafy.backend.model.service;

import java.util.List;

import com.ssafy.backend.model.BookDto;

public interface BookService {
	
	String registerBook(BookDto bookDto) throws Exception;
	List<BookDto> getBook() throws Exception;
	boolean verifyBook(BookDto bookDto) throws Exception;

}
