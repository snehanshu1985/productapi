package com.selfserve.productapi.product.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.selfserve.productapi.product.utils.WicConstant;

@Document(collection="Inventory")
public class Inventory {
	
	@Id private String wic;
	private int stockLevel;
	private String availStatus;
	private String source;
	private Date createDttm;
	private Date updateDttm;
	private int threshold;
	
	public String getWic() {
		return wic;
	}
	public void setWic(String wic) {
		this.wic = wic;
	}
	public int getStockLevel() {
		return stockLevel;
	}
	public void setStockLevel(int stockLevel) {
		if(stockLevel > 0) {
			this.stockLevel = stockLevel;
		}
		else
			this.stockLevel = 0;
	}
	public String getAvailStatus() {
		return availStatus;
	}
	public void setAvailStatus(int stockLevel, int thresHold) {
		if(stockLevel >= thresHold && stockLevel > 0) {
			this.availStatus = WicConstant.avail_status_instock;
		}
		else {
			this.availStatus = WicConstant.avail_status_outstock;
		}
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getCreateDttm() {
		return createDttm;
	}
	public void setCreateDttm() {
		this.createDttm = new Date();
	}
	public Date getUpdateDttm() {
		return updateDttm;
	}
	public void setUpdateDttm() {
		this.updateDttm = new Date();
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		if(threshold > 0) {
			this.threshold = threshold;
		}
		else
			this.threshold = 0;
	}
}
