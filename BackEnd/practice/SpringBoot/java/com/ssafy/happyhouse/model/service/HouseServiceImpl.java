package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.DongCodeDto;
import com.ssafy.happyhouse.model.HouseDealInfo;
import com.ssafy.happyhouse.model.HouseList;
import com.ssafy.happyhouse.model.mapper.HouseMapper;

@Service
public class HouseServiceImpl implements HouseService {
	
	@Autowired
	HouseMapper houseMapper;
	
	@Override
	public List<HouseList> searchHouseList(String dongCode) throws Exception {
		return houseMapper.searchHouseList(dongCode);
	}

	@Override
	public List<HouseDealInfo> searchHouseDealInfo(int aptCode) throws Exception {
		return houseMapper.searchHouseDealInfo(aptCode);
	}

	@Override
	public List<HouseList> searchHouseListRange(String neLat, String neLng, String swLat, String swLng)
			throws Exception {
		return houseMapper.searchHouseListRange(neLat,neLng,swLat,swLng);
	}

}
