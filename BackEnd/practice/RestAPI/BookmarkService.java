package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.HouseList;

public interface BookmarkService {

	List<HouseList> bookmarkList(String memberId) throws Exception; // 북마크 전체 조회

	void insertBookmark(String memberId, long aptCode) throws Exception; // 북마크 추가

	void deleteBookmark(String memberId, long aptCode) throws Exception; // 북마크 삭제

	void deleteBookmarkById(String memberId) throws Exception; // 아이디 삭제
}
