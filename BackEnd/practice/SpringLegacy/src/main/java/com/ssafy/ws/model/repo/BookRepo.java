package com.ssafy.ws.model.repo;

import java.util.List;
import java.util.Map;

import com.ssafy.ws.dto.BookDto;
import com.ssafy.ws.dto.ImgDto;

public interface BookRepo {

	void insert(BookDto bookDto) throws Exception;

	void insertImg(ImgDto imgDto) throws Exception;

	void update(BookDto bookDto) throws Exception;

	void delete(String isbn) throws Exception;
	
	void deleteFile(String isbn) throws Exception;

	BookDto select(String isbn) throws Exception;

	List<BookDto> search(Map<String, Object> map) throws Exception;

	int getTotalCount(Map<String, String> map) throws Exception;
}
