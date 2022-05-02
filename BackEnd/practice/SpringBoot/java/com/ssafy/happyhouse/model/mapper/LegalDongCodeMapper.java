package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.BaseAddress;
import com.ssafy.happyhouse.model.DongCodeDto;
import com.ssafy.happyhouse.model.GugunCodeDto;
import com.ssafy.happyhouse.model.SidoCodeDto;

@Mapper
public interface LegalDongCodeMapper {

	/*
	 * 1. 시,도 가져오기
	 * 2. 시,도 입력시 구군 리스트 가져오기
	 * 3, 구군 입력시 동 가져오기
	 * 4, 동 입력되면 센터 좌표 구하기
	 */
	
	public List<SidoCodeDto> searchSidoList() throws Exception;
	public List<GugunCodeDto> searchGugunList(String sidoCode) throws Exception;
	public List<DongCodeDto> searchDongList(String gugunCode) throws Exception;
	public BaseAddress searchCenter(String dongCode) throws Exception;
	
}
