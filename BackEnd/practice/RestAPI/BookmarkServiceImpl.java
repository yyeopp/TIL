package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.dto.HouseList;
import com.ssafy.happyhouse.model.mapper.BookmarkMapper;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	@Autowired
	BookmarkMapper bookmarkMapper;

	@Override
	public List<HouseList> bookmarkList(String memberId) throws Exception {
		return bookmarkMapper.bookmarkList(memberId);
	}

	@Override
	@Transactional
	public void insertBookmark(String memberId, long aptCode) throws Exception {
		bookmarkMapper.insertBookmark(memberId, aptCode);
	}

	@Override
	@Transactional
	public void deleteBookmark(String memberId, long aptCode) throws Exception {
		bookmarkMapper.deleteBookmark(memberId, aptCode);
	}

	@Override
	@Transactional
	public void deleteBookmarkById(String memberId) throws Exception {
		bookmarkMapper.deleteBookmarkById(memberId);
	}

}
