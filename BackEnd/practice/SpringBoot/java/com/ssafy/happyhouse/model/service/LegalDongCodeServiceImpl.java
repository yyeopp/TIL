package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.BaseAddress;
import com.ssafy.happyhouse.model.DongCodeDto;
import com.ssafy.happyhouse.model.GugunCodeDto;
import com.ssafy.happyhouse.model.SidoCodeDto;
import com.ssafy.happyhouse.model.mapper.LegalDongCodeMapper;

@Service
public class LegalDongCodeServiceImpl implements LegalDongCodeService {
	
	@Autowired
	private LegalDongCodeMapper legalDongCodeDao;

	@Override
	public List<SidoCodeDto> searchSidoList() throws Exception {
		return legalDongCodeDao.searchSidoList();
	}

	@Override
	public List<GugunCodeDto> searchGugunList(String sidoCode) throws Exception {
		return legalDongCodeDao.searchGugunList(sidoCode);
	}

	@Override
	public List<DongCodeDto> searchDongList(String gugunCode) throws Exception {
		return legalDongCodeDao.searchDongList(gugunCode);
	}

	@Override
	public BaseAddress searchCenter(String dongCode) throws Exception {
		return legalDongCodeDao.searchCenter(dongCode);
	}

}
