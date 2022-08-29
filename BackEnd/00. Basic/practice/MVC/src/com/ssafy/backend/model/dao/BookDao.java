package com.ssafy.backend.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.backend.model.BookDto;

public interface BookDao {
	
	String registerBook(BookDto bookDto) throws SQLException;
	List<BookDto> getBook() throws SQLException;
	boolean verifyBook(BookDto bookDto) throws SQLException;


}
