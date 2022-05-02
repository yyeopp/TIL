package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.DongCodeDto;
import com.ssafy.happyhouse.model.HouseDealInfo;
import com.ssafy.happyhouse.model.HouseList;


public interface HouseService {

	public List<HouseList> searchHouseList(String dongCode) throws Exception;
	public List<HouseList> searchHouseListRange(String neLat, String neLng, String swLat, String swLng) throws Exception;
	public List<HouseDealInfo> searchHouseDealInfo(int aptCode) throws Exception;
	
}
