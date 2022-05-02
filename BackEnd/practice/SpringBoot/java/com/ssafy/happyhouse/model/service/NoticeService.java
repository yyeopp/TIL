package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.util.PageNavigation;


public interface NoticeService {

	List<NoticeDto> getNoticeList(String pg, String key, String word) throws Exception; // 공지사항 전체 출력
	NoticeDto getNotice(int noticeNo) throws Exception; // 공지사항 1개 출력
	void writeNotice(NoticeDto noticeDto) throws Exception; // 공지사항 작성
	void updateNotice(NoticeDto noticeDto) throws Exception; // 공지사항 수정
	void deleteNotice(int noticeNo) throws Exception; // 공지사항 삭제
	PageNavigation makePageNavigation(String pg, String key, String word) throws Exception; // 페이징
}
