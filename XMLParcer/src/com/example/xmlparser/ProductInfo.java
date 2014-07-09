package com.example.xmlparser;

public class ProductInfo {
	 
    String seqNo = null;
    String itemNumber = "";
    String quantity = "";
    String price = "";
    String extra = "";
 
    public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getSeqNo() {
        return seqNo;
    }
 
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
 
    public String getItemNumber() {
        return itemNumber;
    }
 
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }
 
    public String getQuantity() {
        return quantity;
    }
 
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
 
    public String getPrice() {
        return price;
    }
 
    public void setPrice(String price) {
        this.price = price;
    }
}