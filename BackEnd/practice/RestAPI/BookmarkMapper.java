package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.HouseList;

public interface BookmarkMapper {

	List<HouseList> bookmarkList(String memberId) throws SQLException; // 북마크 전체 조회

	void insertBookmark(String memberId, long aptCode) throws SQLException; // 북마크 추가

	void deleteBookmark(String memberId, long aptCode) throws SQLException; // 북마크 삭제

	void deleteBookmarkById(String memberId) throws SQLException; // 아이디 삭제
}
