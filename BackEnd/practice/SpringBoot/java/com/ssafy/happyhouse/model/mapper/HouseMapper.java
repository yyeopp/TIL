package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.DongCodeDto;
import com.ssafy.happyhouse.model.HouseDealInfo;
import com.ssafy.happyhouse.model.HouseList;

@Mapper
public interface HouseMapper {

	/*
	 * TODO
	 * 1. 동코드 받아와서 해당 지역의 아파트 리스트 리턴
	 * 2. 마커 클릭 시, 아파트 코드를 받아서 아파트 거래 상세정보 리스트 리턴
	 */
	
	public List<HouseList> searchHouseList(String dongCode) throws Exception;
	public List<HouseList> searchHouseListRange(String neLat, String neLng, String swLat, String swLng);
	public List<HouseDealInfo> searchHouseDealInfo(int aptCode) throws Exception;
	
}
