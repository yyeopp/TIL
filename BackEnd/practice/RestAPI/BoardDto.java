package com.ssafy.happyhouse.model;

public class BoardDto {

	private int boardId;
	private String memberId;
	private String title;
	private String content;
	private String hit;
	private String createdDate;
	private int boardListId;
	private CommentDto commentDto;

	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", memberId=" + memberId + ", title=" + title + ", content=" + content
				+ ", hit=" + hit + ", createdDate=" + createdDate + ", boardListId=" + boardListId + ", commentDto="
				+ commentDto + "]";
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHit() {
		return hit;
	}

	public void setHit(String hit) {
		this.hit = hit;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getBoardListId() {
		return boardListId;
	}

	public void setBoardListId(int boardListId) {
		this.boardListId = boardListId;
	}

	public CommentDto getCommentDto() {
		return commentDto;
	}

	public void setCommentDto(CommentDto commentDto) {
		this.commentDto = commentDto;
	}

}
