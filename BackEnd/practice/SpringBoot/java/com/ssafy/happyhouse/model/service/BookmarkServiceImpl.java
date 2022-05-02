package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.BookMarkDto;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	@Override
	public List<BookMarkDto> bookMarkList(String id) throws Exception {
		return null;
	}

	@Override
	public void insertBookMark(String id, String dongCode) throws Exception {
	}

	@Override
	public void deleteBookMark(String id, String dongCode) throws Exception {
	}

	@Override
	public void deleteId(String id) throws Exception {
	}

}
