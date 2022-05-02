package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.BookMarkDto;

public interface BookmarkMapper {

	List<BookMarkDto> bookMarkList(String id) throws SQLException; // 북마크 전체 조회

	void insertBookMark(String id, String dongCode) throws SQLException; // 북마크 추가

	void deleteBookMark(String id, String dongCode) throws SQLException; // 북마크 삭제

	void deleteId(String id) throws SQLException; // 아이디 삭제
}
