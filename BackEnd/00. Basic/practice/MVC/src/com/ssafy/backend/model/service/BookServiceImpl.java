package com.ssafy.backend.model.service;

import java.util.List;

import com.ssafy.backend.model.BookDto;
import com.ssafy.backend.model.dao.BookDao;
import com.ssafy.backend.model.dao.BookDaoImpl;

public class BookServiceImpl implements BookService {

	private static BookService bookService = new BookServiceImpl();

	private BookServiceImpl() {
	}

	public static BookService getBookService() {
		return bookService;
	}

	BookDao bookDao = BookDaoImpl.getBookDao();

	@Override
	public String registerBook(BookDto bookDto) throws Exception {
		return bookDao.registerBook(bookDto);
	}

	@Override
	public List<BookDto> getBook() throws Exception {
		List<BookDto> list = bookDao.getBook();
		return list;
	}

	@Override
	public boolean verifyBook(BookDto bookDto) throws Exception {

		return bookDao.verifyBook(bookDto);
	}

}
