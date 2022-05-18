package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.CommentDto;
import com.ssafy.happyhouse.model.service.BoardService;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {

	@Autowired
	BoardService boardService;

	@PostMapping("/write")
	public ResponseEntity<?> writeBoard(@RequestBody BoardDto boardDto) {
		try {
			System.out.println(boardDto);
			boardService.writeBoard(boardDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/write/comment")
	public ResponseEntity<?> writeComment(@RequestBody CommentDto commentDto) {
		try {
			System.out.println(commentDto);
			boardService.writeComment(commentDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/QnA/list")
	public ResponseEntity<?> getQnAList() {
		try {
			List<BoardDto> list = boardService.getQnAList();
			return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

	}

}
