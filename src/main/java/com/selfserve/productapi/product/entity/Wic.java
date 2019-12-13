package com.selfserve.productapi.product.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="wic")
public class Wic {
	
	 @Id private String wic;
	 private String fulfillerType;
	 private Date startDate;
	 private Date updatedDate;
	 private double cost; // This is mandatory for only web items. 
	 private double price; // This is mandatory for only web items.
	 private String webExclusive;
	 private String status; 
	 private boolean completeCd; // true = Item_online, false = Item_offline.
	 private boolean reorderStatus; // true = reorder, false = normal. Only DC should be eligible for reorder.
	 private String description;
	 private boolean itemStatus; // true - item will be sold for 0 price in cart.
	 private boolean shippingStatus; // true - item will be shipped for free.
	 private int purchaseLimit; // This is only mandatory for web items.
	 private String categoryId;
	 
	 public Wic() {}

	public Wic(String fulfiller_type2, double cost2, double price2, String web_exclusive, String status2,
			boolean complete_cd, boolean item_status, String description2,
			boolean free_item, boolean free_shipping, int purchase_limit, String category_id) {
		// TODO Auto-generated constructor stub
		setFulfiller_type(fulfiller_type2);
		setCost(cost2);
		setPrice(price2);
		setWebExclusive(web_exclusive);
		setStatus(status2);
		setCompleteCd(complete_cd);
		setItemStatus(item_status);
		setDescription(description2);
		setItemStatus(free_item);
		setShippingStatus(free_shipping);
		setPurchaseLimit(purchase_limit);
		setCategoryId(category_id);
		setStart_date();
		setUpdated_date();
	}

	public Wic(String wic2, String fulfiller_type, double cost2, double price2, String web_exclusive, String status2,
			boolean complete_cd, boolean item_status, String description2, boolean free_item, boolean free_shipping,
			int purchase_limit, String category_id, Date start_date) {
		// TODO Auto-generated constructor stub
		setWic(wic2);
		setFulfiller_type(fulfiller_type);
		setCost(cost2);
		setPrice(price2);
		setWebExclusive(web_exclusive);
		setStatus(status2);
		setCompleteCd(complete_cd);
		setItemStatus(item_status);
		setDescription(description2);
		setItemStatus(free_item);
		setShippingStatus(free_shipping);
		setPurchaseLimit(purchase_limit);
		setCategoryId(category_id);
		setUpdated_date();
		setStart_date(start_date);
	}

	public String getWic() {
		return wic;
	}

	public void setWic(String wic) {
		this.wic = wic;
	}

	public String getFulfiller_type() {
		return fulfillerType;
	}

	public void setFulfiller_type(String fulfiller_type) {
		this.fulfillerType = fulfiller_type;
	}

	public Date getStart_date() {
		return startDate;
	}

	public void setStart_date() {
		this.startDate = new Date();
	}
	
	public void setStart_date(Date startDate) {
		this.startDate = startDate;
	}

	public Date getUpdated_date() {
		return updatedDate;
	}

	public void setUpdated_date() {
		this.updatedDate = new Date();
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isCompleteCd() {
		return completeCd;
	}

	public void setCompleteCd(boolean completeCd) {
		this.completeCd = completeCd;
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
	 
	 


	
}
