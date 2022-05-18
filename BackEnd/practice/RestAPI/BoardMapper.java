package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.CommentDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> getQnAList() throws Exception;

	BoardDto getBoard(int boardId) throws Exception;

	void writeBoard(BoardDto boardDto) throws Exception;

	void updateBoard(BoardDto boardDto) throws Exception;

	void deleteBoard(BoardDto boardDto) throws Exception;
	void writeComment(CommentDto commentDto) throws Exception;

}
