package com.ssafy.hw04;

public class IoOutcome extends IoItem {
	String selCom;
	int selDate;
	long selQnt;
	long selPrice;
	long selMoney;

	
	public IoOutcome(int itemNo, String itemName, String itemInfo, String selCom, int selDate, long selQnt, long selPrice, long selMoney) {
		setItemNo(itemNo);
		setItemName(itemName);
		setItemInfo(itemInfo);
		this.selCom = selCom;
		this.selDate = selDate;
		this.selQnt = selQnt;
		this.selPrice = selPrice;
		this.selMoney = selMoney;
	}
	
	
}
