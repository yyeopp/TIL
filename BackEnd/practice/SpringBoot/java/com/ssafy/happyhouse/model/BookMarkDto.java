package com.ssafy.happyhouse.model;

public class BookMarkDto {

	String dongCode;
	String addr;

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "BookMarkDto [dongCode=" + dongCode + ", addr=" + addr + "]";
	}

}
