package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.happyhouse.model.BaseAddress;
import com.ssafy.happyhouse.model.DongCodeDto;
import com.ssafy.happyhouse.model.GugunCodeDto;
import com.ssafy.happyhouse.model.SidoCodeDto;
import com.ssafy.happyhouse.model.service.LegalDongCodeService;

@Controller
@RequestMapping("/legaldong")
public class LegalDongCodeController {

	@Autowired
	private LegalDongCodeService legalDongCodeService;
	
	@GetMapping("/sido")
	public ResponseEntity<?> getSidoCode() {
		try {
			List<SidoCodeDto> list = legalDongCodeService.searchSidoList();
			System.out.println(list.get(0));
			return new ResponseEntity<List<SidoCodeDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<?> getGugunCode(@RequestParam("code") String sidoCode) {

		try {
			List<GugunCodeDto> list = legalDongCodeService.searchGugunList(sidoCode);
			return new ResponseEntity<List<GugunCodeDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/dong")
	public ResponseEntity<?> getDongCode(@RequestParam("code") String gugunCode) {
		try {
			List<DongCodeDto> list = legalDongCodeService.searchDongList(gugunCode);
			System.out.println(list.get(0).getCode());
			return new ResponseEntity<List<DongCodeDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/center/{dongCode}")
	public ResponseEntity<?> getCenter(@PathVariable("dongCode") String dongCode) {
		try {
			BaseAddress addr = legalDongCodeService.searchCenter(dongCode);
			return new ResponseEntity<BaseAddress>(addr, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
