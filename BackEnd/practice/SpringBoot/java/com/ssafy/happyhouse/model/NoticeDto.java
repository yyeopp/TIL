package com.ssafy.happyhouse.model;

public class NoticeDto {
	int noticeNo;
	String title;
	String content;
	String writeDate;

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
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

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	@Override
	public String toString() {
		return "NoticeDto [noticeNo=" + noticeNo + ", title=" + title + ", content=" + content + ", writeDate="
				+ writeDate + "]";
	}

}
