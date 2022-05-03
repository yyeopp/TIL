package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BookmarkDto;

public interface BookmarkService {

	List<BookmarkDto> bookmarkList(String id) throws Exception; // 북마크 전체 조회

	void insertBookmark(String id, String dongCode) throws Exception; // 북마크 추가

	void deleteBookmark(String id, String dongCode) throws Exception; // 북마크 삭제

	void deleteId(String id) throws Exception; // 아이디 삭제
}
