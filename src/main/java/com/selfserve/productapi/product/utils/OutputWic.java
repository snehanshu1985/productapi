package com.selfserve.productapi.product.utils;

public class OutputWic {
	private String Wic;
	private String fulfillerType;
	private double price;
	private String webExclusive;
	private boolean reorderStatus;
	private String description;
	private boolean itemStatus;
	private boolean shippingStatus;
	private int purchaseLimit;
	private String categoryId;
	private int stockLevel;
	private String availStatus;
	private int threshold;
	
	public String getWic() {
		return Wic;
	}
	public void setWic(String wic) {
		Wic = wic;
	}
	public String getFulfillerType() {
		return fulfillerType;
	}
	public void setFulfillerType(String fulfillerType) {
		this.fulfillerType = fulfillerType;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getWebExclusive() {
		return webExclusive;
	}
	public void setWebExclusive(String webExclusive) {
		this.webExclusive = webExclusive;
	}
	public boolean isReorderStatus() {
		return reorderStatus;
	}
	public void setReorderStatus(boolean reorderStatus) {
		this.reorderStatus = reorderStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(boolean itemStatus) {
		this.itemStatus = itemStatus;
	}
	public boolean isShippingStatus() {
		return shippingStatus;
	}
	public void setShippingStatus(boolean shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	public int getPurchaseLimit() {
		return purchaseLimit;
	}
	public void setPurchaseLimit(int purchaseLimit) {
		this.purchaseLimit = purchaseLimit;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public int getStockLevel() {
		return stockLevel;
	}
	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}
	public String getAvailStatus() {
		return availStatus;
	}
	public void setAvailStatus(String availStatus) {
		this.availStatus = availStatus;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	
}
