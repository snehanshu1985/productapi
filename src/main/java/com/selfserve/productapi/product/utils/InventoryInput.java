package com.selfserve.productapi.product.utils;

import javax.validation.constraints.NotNull;

public class InventoryInput {
		@NotNull
		String wic;
		@NotNull
		String stockLevel;
		@NotNull
		String threshold;
		
		public String getWic() {
			return wic;
		}
		public void setWic(String wic) {
			this.wic = wic;
		}
		public String getStockLevel() {
			return stockLevel;
		}
		public void setStockLevel(String stockLevel) {
			this.stockLevel = stockLevel;
		}
		public String getThreshold() {
			return threshold;
		}
		public void setThreshold(String threshold) {
			this.threshold = threshold;
		}
}
