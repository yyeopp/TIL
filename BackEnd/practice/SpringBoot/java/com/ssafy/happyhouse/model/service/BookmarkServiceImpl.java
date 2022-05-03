package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.BookmarkDto;
import com.ssafy.happyhouse.model.mapper.BookmarkMapper;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	@Autowired
	BookmarkMapper bookmarkMapper;

	@Override
	public List<BookmarkDto> bookmarkList(String id) throws Exception {

		return bookmarkMapper.bookmarkList(id);
	}

	@Override
	@Transactional
	public void insertBookmark(String id, String dongCode) throws Exception {
		bookmarkMapper.insertBookmark(id, dongCode);
	}

	@Override
	@Transactional
	public void deleteBookmark(String id, String dongCode) throws Exception {
		bookmarkMapper.deleteBookmark(id, dongCode);
	}

	@Override
	@Transactional
	public void deleteId(String id) throws Exception {
		bookmarkMapper.deleteId(id);
	}

}
