package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.DongCodeDto;
import com.ssafy.happyhouse.model.HouseDealInfo;
import com.ssafy.happyhouse.model.HouseList;
import com.ssafy.happyhouse.model.service.HouseService;

@RestController
@RequestMapping("/house")
public class HouseController {
	
	@Autowired
	HouseService houseService;
	
	@GetMapping("/houselist/{dongCode}")
	public ResponseEntity<?> getHouseList(@PathVariable("dongCode") String dongCode) {
		try {
			List<HouseList> list = houseService.searchHouseList(dongCode);
			return new ResponseEntity<List<HouseList>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/housedeal/{aptcode}")
	public ResponseEntity<?> getHouseDealInfo(@PathVariable("aptcode") int aptcode) {
		try {
			List<HouseDealInfo> list = houseService.searchHouseDealInfo(aptcode);
			return new ResponseEntity<List<HouseDealInfo>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/houselist")
	public ResponseEntity<?> getHouseListRange(@RequestParam("neLat") String neLat, @RequestParam("neLng") String neLng,
			@RequestParam("swLat") String swLat,@RequestParam("swLng") String swLng) {
		try {
			List<HouseList> list = houseService.searchHouseListRange(neLat,neLng,swLat,swLng);
			return new ResponseEntity<List<HouseList>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
