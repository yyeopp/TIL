package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.CommentDto;

public interface BoardService {

	List<BoardDto> getQnAList() throws Exception;

	BoardDto getBoard(int boardId) throws Exception;

	void writeBoard(BoardDto boardDto) throws Exception;

	void writeComment(CommentDto commentDto) throws Exception;

	void updateBoard(BoardDto boardDto) throws Exception;

	void deleteBoard(BoardDto boardDto) throws Exception;
	
	

}
