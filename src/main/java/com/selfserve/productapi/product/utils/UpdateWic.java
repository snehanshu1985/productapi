package com.selfserve.productapi.product.utils;

public class UpdateWic {
		private String wic;
		private String fulfiller_type;
		private double cost; 
		private double price;
		private String web_exclusive;
		private String status;
		private boolean reorder_status;
		private String description;
		private boolean free_item;
		private boolean free_shipping; // true - item will be shipped for free.
		private int purchase_limit; // This is only mandatory for web items.
		private String category_name;
		
		public String getWic() {
			return wic;
		}
		public void setWic(String wic) {
			this.wic = wic;
		}
		public String getFulfiller_type() {
			return fulfiller_type;
		}
		public void setFulfiller_type(String fulfiller_type) {
			this.fulfiller_type = fulfiller_type;
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
		public String getWeb_exclusive() {
			return web_exclusive;
		}
		public void setWeb_exclusive(String web_exclusive) {
			this.web_exclusive = web_exclusive;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public boolean isReorder_status() {
			return reorder_status;
		}
		public void setReorder_status(boolean reorder_status) {
			this.reorder_status = reorder_status;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public boolean isFree_item() {
			return free_item;
		}
		public void setFree_item(boolean free_item) {
			this.free_item = free_item;
		}
		public boolean isFree_shipping() {
			return free_shipping;
		}
		public void setFree_shipping(boolean free_shipping) {
			this.free_shipping = free_shipping;
		}
		public int getPurchase_limit() {
			return purchase_limit;
		}
		public void setPurchase_limit(int purchase_limit) {
			this.purchase_limit = purchase_limit;
		}
		public String getCategory_name() {
			return category_name;
		}
		public void setCategory_name(String category_name) {
			this.category_name = category_name;
		}
		
}
