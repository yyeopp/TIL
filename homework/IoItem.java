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


	
	
	// toString�� �������̵��ؾ��ϳ�?
	// main����,
	// 1. private�� ��ȣ�Ǵ� itemŬ���� ǰ�� ������ �޼��带 ���� ���
	// 2. income�� outcome Ŭ���� �� �������� �Է��Ͽ�
	// 3. �װ��� �ش� item�� �������� �������� �����
	// 4. item���κ��� ��� ������ �Ѳ����� ��Ƴ� �� �ֵ���

}
