package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.CommentDto;
import com.ssafy.happyhouse.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;

	@Override
	public List<BoardDto> getQnAList() throws Exception {
		return boardMapper.getQnAList();
	}

	@Override
	public BoardDto getBoard(int boardId) throws Exception {
		return boardMapper.getBoard(boardId);
	}

	@Override
	public void writeBoard(BoardDto boardDto) throws Exception {
		boardMapper.writeBoard(boardDto);
	}

	@Override
	public void updateBoard(BoardDto boardDto) throws Exception {
		boardMapper.updateBoard(boardDto);
	}

	@Override
	public void deleteBoard(BoardDto boardDto) throws Exception {
		boardMapper.deleteBoard(boardDto);
	}

	@Override
	public void writeComment(CommentDto commentDto) throws Exception {
		boardMapper.writeComment(commentDto);
	}

}
