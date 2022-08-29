package com.ssafy.ws.dto;

public class ImgDto {
	private String isbn;

	private String saveFolder;
	private String originFile;
	private String saveFile;

	public ImgDto() {

	}

	public String getSaveFolder() {
		return saveFolder;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	public String getOriginFile() {
		return originFile;
	}

	public void setOriginFile(String originFile) {
		this.originFile = originFile;
	}

	public String getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}

}
