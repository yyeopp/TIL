package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.HospitalInfoDto;
import com.ssafy.happyhouse.model.HospitalListDto;
import com.ssafy.happyhouse.model.SchoolDto;
import com.ssafy.happyhouse.model.mapper.ExpandedMapper;

@Service
public class ExpandedServiceImpl implements ExpandedService {
	
	@Autowired
	private ExpandedMapper expandedMapper;

	@Override
	public List<HospitalListDto> hospitalList(String dongCode) throws SQLException {
		return expandedMapper.hospitalList(dongCode);
	}

	@Override
	public HospitalInfoDto viewHospital(String hospitalNo) throws SQLException {
		return expandedMapper.viewHospital(hospitalNo);
	}

	@Override
	public List<HospitalListDto> hospitalListRange(String neLat, String neLng, String swLat, String swLng) {
		return expandedMapper.hospitalListRange(neLat,neLng,swLat,swLng);
	}

	@Override
	public List<SchoolDto> schoolListRange(String neLat, String neLng, String swLat, String swLng) {
		return expandedMapper.schoolListRange(neLat, neLng, swLat, swLng);
	}

}
