package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BookMarkDto;

public interface BookmarkService {

	List<BookMarkDto> bookMarkList(String id) throws Exception; // 북마크 전체 조회

	void insertBookMark(String id, String dongCode) throws Exception; // 북마크 추가

	void deleteBookMark(String id, String dongCode) throws Exception; // 북마크 삭제

	void deleteId(String id) throws Exception; // 아이디 삭제
}
