package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.ListParameterDto;
import com.ssafy.happyhouse.model.NoticeDto;


public interface NoticeMapper {

	List<NoticeDto> getNoticeList(ListParameterDto listParameterDto) throws SQLException; // 공지사항 전체 출력

	NoticeDto getNotice(int no) throws SQLException; // 공지사항 1개 출력

	void writeNotice(NoticeDto noticeDto) throws SQLException; // 공지사항 작성

	void updateNotice(NoticeDto noticeDto) throws SQLException; // 공지사항 수정

	void deleteNotice(int no) throws SQLException; // 공지사항 삭제

	int getTotalCount(ListParameterDto listParameterDto) throws SQLException; // 글 개수 얻기
}
