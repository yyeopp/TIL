package com.ssafy.happyhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.service.BookmarkService;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {
	
	@Autowired
	BookmarkService bookmarkService;
	
	
}
