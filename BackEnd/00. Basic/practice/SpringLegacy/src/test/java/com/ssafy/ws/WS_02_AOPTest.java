package com.ssafy.ws;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.ws.dto.BookDto;
import com.ssafy.ws.model.service.BookService;

public class WS_02_AOPTest extends AbstractTest {

	@Autowired
	BookService bookService;

	Logger logger = LoggerFactory.getLogger(WS_02_AOPTest.class);

	@Test
	public void insertTest() {
		try {
			bookService.insert(new BookDto());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void selectTest() {
		try {
			bookService.select("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public List<BookDto> searchTest(@RequestParam Map<String, String> map) {
		try {
			return bookService.search(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
