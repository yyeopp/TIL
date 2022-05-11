

public class IoIncome extends IoItem {
	String buyCom;
	int buyDate;
	long buyQnt;
	long buyPrice;
	long buyMoney;
	
	
	public void setItem(int itemNo, String itemName, String itemInfo) {
		super.setItemNo(itemNo);
		super.setItemName(itemName);
		super.setItemInfo(itemInfo);
	}
	
	public IoIncome(String buyCom, int buyDate, long buyQnt, long buyPrice, long buyMoney) {
		
		this.buyCom = buyCom;
		this.buyDate = buyDate;
		this.buyQnt = buyQnt;
		this.buyPrice = buyPrice;
		this.buyMoney = buyMoney;
	}
	
	
	
	
	
	

}
