package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.BookmarkDto;

public interface BookmarkMapper {

	List<BookmarkDto> bookmarkList(String id) throws SQLException; // 북마크 전체 조회

	void insertBookmark(String id, String dongCode) throws SQLException; // 북마크 추가

	void deleteBookmark(String id, String dongCode) throws SQLException; // 북마크 삭제

	void deleteId(String id) throws SQLException; // 아이디 삭제
}
