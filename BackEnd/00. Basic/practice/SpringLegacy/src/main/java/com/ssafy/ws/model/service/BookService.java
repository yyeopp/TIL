package com.ssafy.ws.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.util.PageNavigation;
import com.ssafy.ws.dto.BookDto;
import com.ssafy.ws.model.repo.BookRepo;

public interface BookService {

	BookRepo getBookRepo() throws Exception;

	void setBookRepo(BookRepo bookRepo) throws Exception;

	void insert(BookDto bookDto) throws Exception;

	void update(BookDto bookDto) throws Exception;

	void delete(String isbn) throws Exception;

	BookDto select(String isbn) throws Exception;

	List<BookDto> search(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
}
