package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.happyhouse.model.ListParameterDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.mapper.NoticeMapper;
import com.ssafy.happyhouse.util.PageNavigation;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeMapper noticeMapper;

	@Override
	public List<NoticeDto> getNoticeList(@RequestParam Map<String, String> map) throws Exception {
		int countPerPage = 5;
		int start = (Integer.parseInt(map.get("pg")) - 1) * countPerPage;

		ListParameterDto listParameterDto = new ListParameterDto();
		listParameterDto.setKey(map.get("key"));
		listParameterDto.setWord(map.get("word"));
		listParameterDto.setStart(start);
		listParameterDto.setCurrentPerPage(countPerPage);
		return noticeMapper.getNoticeList(listParameterDto);

	}

	@Override
	public NoticeDto getNotice(int no) throws Exception {
		return noticeMapper.getNotice(no);
	}

	@Override
	public void writeNotice(NoticeDto noticeDto) throws Exception {
		noticeMapper.writeNotice(noticeDto);
	}

	@Override
	public void updateNotice(NoticeDto noticeDto) throws Exception {
		noticeMapper.updateNotice(noticeDto);
	}

	@Override
	public void deleteNotice(int no) throws Exception {
		noticeMapper.deleteNotice(no);
	}

	@Override
	public PageNavigation makePageNavigation(@RequestParam Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		int currentPage = Integer.parseInt(map.get("pg"));
		int naviSize = 5;
		int countPerPage = 5;
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setCountPerPage(countPerPage);
		pageNavigation.setNaviSize(naviSize);

		ListParameterDto listParameterDto = new ListParameterDto();
		listParameterDto.setKey(map.get("key"));
		listParameterDto.setWord(map.get("word"));

		int totalCount = noticeMapper.getTotalCount(listParameterDto);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / countPerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		pageNavigation.setStartRange(currentPage <= naviSize);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;

	}

}
