package com.ssafy.ws.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.PageNavigation;
import com.ssafy.ws.dto.BookDto;
import com.ssafy.ws.model.repo.BookRepo;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private BookRepo bookRepo;

	public BookRepo getBookRepo() throws Exception {
		return bookRepo;
	}

	public void setBookRepo(BookRepo bookRepo) throws Exception {
		this.bookRepo = bookRepo;
	}

	@Override
	@Transactional
	public void insert(BookDto bookDto) throws Exception {
		bookRepo.insert(bookDto);
		if (bookDto.getImg() != null) {
			bookRepo.insertImg(bookDto.getImg());
		}
	}

	@Override
	public void update(BookDto bookDto) throws Exception {
		bookRepo.update(bookDto);
	}

	@Override
	@Transactional
	public void delete(String isbn) throws Exception {
		String realPath = servletContext.getRealPath("/upload");
		BookDto bookDto = bookRepo.select(isbn);
		if (bookDto.getImg() != null) {
			bookRepo.deleteFile(isbn);
			File file = new File(realPath + File.separator + bookDto.getImg().getSaveFolder() + File.separator
					+ bookDto.getImg().getSaveFile());
			file.delete();
		}
		bookRepo.delete(isbn);
	}

	@Override
	public BookDto select(String isbn) throws Exception {
		return bookRepo.select(isbn);
	}

	@Override
	public List<BookDto> search(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pg") == null ? "1" : map.get("pg"));
		int sizePerPage = Integer.parseInt(map.get("spp"));
		int start = (currentPage - 1) * sizePerPage;
		param.put("start", start);
		param.put("spp", sizePerPage);
		return bookRepo.search(param);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = 10;
		int currentPage = Integer.parseInt(map.get("pg"));
		int sizePerPage = Integer.parseInt(map.get("spp"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		int totalCount = bookRepo.getTotalCount(map);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

}
