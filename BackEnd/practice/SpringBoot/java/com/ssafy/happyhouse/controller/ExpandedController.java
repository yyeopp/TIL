package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.HospitalInfoDto;
import com.ssafy.happyhouse.model.HospitalListDto;
import com.ssafy.happyhouse.model.SchoolDto;
import com.ssafy.happyhouse.model.service.ExpandedService;

@RestController
@RequestMapping("/expanded")
public class ExpandedController {
	
	@Autowired
	ExpandedService expandedService;
	
	@GetMapping("/hospitallist/{dongCode}")
	public ResponseEntity<?> getHospitalList(@PathVariable("dongCode") String dongCode) {
		try {
			List<HospitalListDto> list = expandedService.hospitalList(dongCode);
			return new ResponseEntity<List<HospitalListDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/hospitalinfo/{hospitalNo}")
	public ResponseEntity<?> getHospitalInfo(@PathVariable("hospitalNo") String hospitalNo) {
		try {
			HospitalInfoDto list = expandedService.viewHospital(hospitalNo);
			return new ResponseEntity<HospitalInfoDto>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/hospitallist/")
	public ResponseEntity<?> getHospitalListRange(@RequestParam("neLat") String neLat, @RequestParam("neLng") String neLng,
			@RequestParam("swLat") String swLat,@RequestParam("swLng") String swLng) {
		try {
			List<HospitalListDto> list = expandedService.hospitalListRange(neLat,neLng,swLat,swLng);
			return new ResponseEntity<List<HospitalListDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/schoollist/")
	public ResponseEntity<?> getSchoolListRange(@RequestParam("neLat") String neLat, @RequestParam("neLng") String neLng,
			@RequestParam("swLat") String swLat,@RequestParam("swLng") String swLng) {
		try {
			List<SchoolDto> list = expandedService.schoolListRange(neLat,neLng,swLat,swLng);
			return new ResponseEntity<List<SchoolDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
