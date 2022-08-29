package com.ssafy.ws.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.dto.BookDto;
import com.ssafy.ws.model.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/bookapi")
@CrossOrigin("*")
@Api("BookApi Controller V1")
public class BookRestController {

	@Autowired
	BookService bookService;

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(value = "전체 목록 조회")
	@GetMapping("/book")
	public ResponseEntity<?> search(@RequestParam("pg") String pg, @RequestParam("key") String key,
			@RequestParam("word") String word) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pg", pg);
		map.put("key", key);
		map.put("word", word);
		map.put("spp", "10");

		try {
			List<BookDto> books = bookService.search(map);
			if (books != null && books.size() > 0) {
				return new ResponseEntity<List<BookDto>>(books, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@ApiOperation(value = "isbn으로 검색")
	@GetMapping("/book/{isbn}")
	public ResponseEntity<?> select(@PathVariable String isbn) {

		try {
			BookDto book = bookService.select(isbn);
			if (book != null) {
				return new ResponseEntity<BookDto>(book, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "도서 정보 등록")
	@PostMapping("/book")
	public ResponseEntity<?> insert(@RequestBody BookDto bookDto) {
		try {
			bookService.insert(bookDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "도서 정보 수정")
	@PutMapping("/book")
	public ResponseEntity<?> update(@RequestBody BookDto bookDto) {

		try {
			bookService.update(bookDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}

	}

	@ApiOperation(value = "도서 정보 삭제")
	@DeleteMapping("/book/{isbn}")
	public ResponseEntity<?> delete(@PathVariable String isbn) {

		try {
			bookService.delete(isbn);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}

	}

}