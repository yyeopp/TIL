package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.HospitalInfoDto;
import com.ssafy.happyhouse.model.HospitalListDto;
import com.ssafy.happyhouse.model.service.HospitalService;

@RestController
@RequestMapping("/expanded")
public class ExpandedController {
	
	@Autowired
	HospitalService hospitalService;
	
	@GetMapping("/hospitallist/{dongCode}")
	public ResponseEntity<?> getHospitalList(@PathVariable("dongCode") String dongCode) {
		try {
			List<HospitalListDto> list = hospitalService.hospitalList(dongCode);
			return new ResponseEntity<List<HospitalListDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/hospitalinfo/{hospitalNo}")
	public ResponseEntity<?> getHospitalInfo(@PathVariable("hospitalNo") String hospitalNo) {
		try {
			HospitalInfoDto list = hospitalService.viewHospital(hospitalNo);
			return new ResponseEntity<HospitalInfoDto>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
