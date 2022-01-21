package com.ssafy.hw04;

public class IoItem {
	private int itemNo;
	private String itemName;
	private String itemInfo;

	
	public int getItemNo() {
		return itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemInfo() {
		return itemInfo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemInfo(String itemInfo) {
		this.itemInfo = itemInfo;
	}

	

	public IoItem() {
	}

	public IoItem(int itemNo, String itemName, String itemInfo) {
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.itemInfo = itemInfo;
	}


	
	
	// toString을 오버라이딩해야하나?
	// main에서,
	// 1. private로 보호되는 item클래스 품목 정보를 메서드를 통해 등록
	// 2. income과 outcome 클래스 내 정보들을 입력하여
	// 3. 그것이 해당 item과 연결점을 가지도록 만들고
	// 4. item으로부터 모든 정보를 한꺼번에 쏟아낼 수 있도록

}
