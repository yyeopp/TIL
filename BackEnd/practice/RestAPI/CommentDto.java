package com.ssafy.happyhouse.model;

public class CommentDto {

	private int commentId;
	private int boardId;
	private String content;
	private String createdDate;

	@Override
	public String toString() {
		return "CommentDto [commentId=" + commentId + ", boardId=" + boardId + ", content=" + content + ", createdDate="
				+ createdDate + "]";
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
