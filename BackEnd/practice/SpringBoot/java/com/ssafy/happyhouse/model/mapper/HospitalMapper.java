package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.HospitalInfoDto;
import com.ssafy.happyhouse.model.HospitalListDto;

@Mapper
public interface HospitalMapper {

	List<HospitalListDto> hospitalList(String dongCode) throws SQLException; // 동으로 병원 검색 시, 동에 해당하는 병원 전체 조회
	HospitalInfoDto viewHospital(String hospitalNo) throws SQLException; // 마커 클릭 시, 병원 상세 정보 조회
}
