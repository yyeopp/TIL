package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.util.PageNavigation;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Override
	public List<NoticeDto> getNoticeList(String pg, String key, String word) throws Exception {
		return null;
	}

	@Override
	public NoticeDto getNotice(int noticeNo) throws Exception {
		return null;
	}

	@Override
	public void writeNotice(NoticeDto noticeDto) throws Exception {
	}

	@Override
	public void updateNotice(NoticeDto noticeDto) throws Exception {
	}

	@Override
	public void deleteNotice(int noticeNo) throws Exception {
	}

	@Override
	public PageNavigation makePageNavigation(String pg, String key, String word) throws Exception {
		return null;
	}

}
