package com.selfserve.productapi.product.entity;



import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="category")
public class Category {
	@Id private String categoryId;
	private String categoryName;
	private String parentCategoryId;
	private String categoryLevel;
	private Date categoryStartDate;
	private Date categoryUpdateDate;
	public Category() {}
	
	
	
	public Category(String cat_name, String parent_cat_id, String cat_level) {
		this.categoryName = cat_name;
		this.parentCategoryId = parent_cat_id;
		this.categoryLevel = cat_level;
		this.setCategorystartdate();
		this.setCategoryupdatedate();
	}



	public Category(String category_id, String cat_name, String cat_level, String parent_cat_id, Date start_date) {
		this.categoryId = category_id;
		this.categoryName = cat_name;
		this.categoryLevel = cat_level;
		this.parentCategoryId = parent_cat_id;
		this.categoryStartDate = start_date;
		this.setCategoryupdatedate();
	}



	public String getCategoryid() {
		return categoryId;
	}
	public void setCategoryid(String categoryid) {
		this.categoryId = categoryid;
	}
	public String getCategoryname() {
		return categoryName;
	}
	public void setCategoryname(String categoryname) {
		this.categoryName = categoryname;
	}
	public String getParentcategoryid() {
		return parentCategoryId;
	}
	public void setParentcategoryid(String parentcategoryid) {
		this.parentCategoryId = parentcategoryid;
	}
	public String getCategorylevel() {
		return categoryLevel;
	}
	public void setCategorylevel(String categorylevel) {
		this.categoryLevel = categorylevel;
	}



	public Date getCategorystartdate() {
		return categoryStartDate;
	}



	public void setCategorystartdate() {
		this.categoryStartDate = new Date();
	}



	public Date getCategoryupdatedate() {
		return categoryUpdateDate;
	}



	public void setCategoryupdatedate() {
		this.categoryUpdateDate = new Date();
	}
	
	
	
	
	
}
