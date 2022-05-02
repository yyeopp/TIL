package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.HospitalInfoDto;
import com.ssafy.happyhouse.model.HospitalListDto;
import com.ssafy.happyhouse.model.mapper.HospitalMapper;

@Service
public class HospitalServiceImpl implements HospitalService {
	
	@Autowired
	private HospitalMapper hospitalDaoImpl;

	@Override
	public List<HospitalListDto> hospitalList(String dongCode) throws SQLException {
		return hospitalDaoImpl.hospitalList(dongCode);
	}

	@Override
	public HospitalInfoDto viewHospital(String hospitalNo) throws SQLException {
		return hospitalDaoImpl.viewHospital(hospitalNo);
	}

}
