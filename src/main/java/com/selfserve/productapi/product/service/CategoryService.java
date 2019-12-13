package com.selfserve.productapi.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.selfserve.productapi.product.entity.Category;
import com.selfserve.productapi.product.repository.CatRepository;
import com.selfserve.productapi.product.utils.InsertCat;
import com.selfserve.productapi.product.utils.UpdateCat;
import com.selfserve.productapi.product.utils.WicConstant;


@Service
public class CategoryService {

	@Autowired CatRepository catRepository;
	
	// This method will fetch all the Category available in backend. 
	public List<Category> getAllCategory() {
		System.out.println("Inside CategoryService.getAllCategory() method");
		List<Category> list_wic= new ArrayList<Category>();
		try {
		catRepository.findAll().forEach(list_wic::add);
		return list_wic;
		}
		catch(Exception e) {
			System.out.println("Something went wrong while fetching the Category List. Exception observed:"+e);
			return null;
		}
	}

	public String insertCat(InsertCat insertCat) {
		// TODO Auto-generated method stub
		System.out.println("Inside CategoryService.insertCat() method");
		String insertcat = null;
		String cat_name = insertCat.getCategory_name();
		String parent_cat_id = insertCat.getParent_category_id();
		String cat_level = insertCat.getCategory_level();
		if (validateCat(cat_name, parent_cat_id, cat_level,"create")) {
			Category cat = new Category(cat_name.toUpperCase(),parent_cat_id,cat_level);
			try {
				catRepository.save(cat);
				insertcat = "Category sucessefully created in Database";
			}
			catch(Exception e) {
				insertcat = "Something went wrong during category creation. Exception is:" +e;
			}
		}
		else
			insertcat = "Category cannot be created. Either the category name already exists or Parent category ID provided does not exist or the"+
						" Cateory level does not look correct";
		return insertcat;
	}

	private boolean validateCategory_level(String cat_level) {
		System.out.println("Inside CategoryService.validateCategory_level() method");
		boolean val_cat_level = false;
		if(cat_level != null && !cat_level.equals(null)) {
		for (int i=0; i<WicConstant.cat_level.length; i++) {
			if (cat_level.equals(WicConstant.cat_level[i])) {
				val_cat_level = true;
				System.out.println("validateCategory_level looks good");
				break;
			}
		}
		}
		return val_cat_level;
	}

	private boolean validateParent_category_id(String parent_cat_id) {
		System.out.println("Inside CategoryService.validateParent_category_id() method");
		boolean val_parcat_id = false;
		if(parent_cat_id!=null && !parent_cat_id.equals(null)) {
			try {
			Category cat = catRepository.findById(parent_cat_id).orElse(null);
			if (cat != null) 
				val_parcat_id = true;
			else
				System.out.println("Parent category is not present in the backend");
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching parent category Id from DB. Exception is:"+e);
			}
			}
		return val_parcat_id;
	}

	private boolean validateCategory_name(String cat_name) {
		System.out.println("Inside CategoryService.validateCategory_name() method");
		boolean val_cat_name = false;
		if(cat_name != null && !cat_name.equals(null)) {
		try {
		Category cat = catRepository.findByCategoryName(cat_name.toUpperCase());
		if(cat == null) {
			System.out.println("Category is not present in DB.");
			val_cat_name = true;
		}
		else
			System.out.println("Category already present. Not proceding further with category creation");
		}
		catch(Exception e) {
			System.out.println("Something went wrong while checking Category Name. Exception is:"+e);
		}
		}
		return val_cat_name;
	}
	
	private boolean validateCategoryNameForUpdate(String cat_name) {
		System.out.println("Inside CategoryService.validateCategoryNameForUpdate() method");
		boolean val_cat_name = false;
		if(cat_name != null && !cat_name.equals(null)) {
		try {
		Category cat = catRepository.findByCategoryName(cat_name.toUpperCase());
		if(cat != null) {
			System.out.println("Category is present in DB.");
			val_cat_name = true;
		}
		else
			System.out.println("Category not present. Not proceding further with category creation. Please create the category.");
		}
		catch(Exception e) {
			System.out.println("Something went wrong while checking Category Name. Exception is:"+e);
		}
		}
		return val_cat_name;
	}

	public Category findByCatId(String category_id) {
		Category cat = null;
		System.out.println("Inside CategoryService.findByCatId() method");
		if(category_id!=null && !category_id.equals(null)) {
			try {
				cat = catRepository.findById(category_id).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching the data. Exception is:"+e);
			}
				if(cat == null) {
					System.out.println("Category ID not present in DB");
				}
			}
		return cat;
	}
	
	public String findByCatName(String category_name) {
		Category category = null;
		String cat_id = null;
		System.out.println("Inside CategoryService.findByCatName() method");
		if(category_name!=null && !category_name.equals(null)) {
			try {
				category = catRepository.findByCategoryName(category_name.toUpperCase());
				if (category != null && category.getCategorylevel().equals(WicConstant.parent_cat)) {
					System.out.println("Category is present in backend.");
					cat_id = category.getCategoryid();
					return cat_id;
				}
				else {
					System.out.println("Category is not present in backend or is not a Tier 3 category.");
					return cat_id;
				}
			}
			catch (Exception e) {
				System.out.println("Something went wrong while fetching the date. Exception is:"+e);
				return cat_id;
			}
		}
		else
			return cat_id;
	}

	
	public String updateCat(UpdateCat updateCat) {
		System.out.println("Inside CategoryService.updateCat() method");
		String update_cat = null;
		String category_id = updateCat.getCategory_id();
		String cat_name = updateCat.getCategory_name();
		String cat_level = updateCat.getCategory_level();
		String parent_cat_id = updateCat.getParent_category_id();
		Category cat_db = findByCatId(category_id);
		if(cat_db != null) {
			if (validateCat(cat_name, parent_cat_id, cat_level,"update")) {
				Date start_date = cat_db.getCategorystartdate();
				try {
					Category new_cat = new Category(category_id, cat_name.toUpperCase(), cat_level, parent_cat_id,  start_date);
					catRepository.save(new_cat);
					update_cat = "Category sucessefully updated in backend";
				}
				catch(Exception e) {
					update_cat = "Something went wrong during update. Exception is:"+e;}
			}
			else
				update_cat ="Category cannot be updated";
		}
		else {
			update_cat = "Category cannot be updated as it does not exist in DB";	
		}
		return update_cat;
	}	
	
	public boolean validateCat(String cat_name,String parent_cat_id, String cat_level, String cat_activity) {
		System.out.println("Inside CategoryService.validateCat() method");
		boolean validate_cat = false;
		if((cat_name.equals(WicConstant.tier_zero)) && (cat_level.equals(WicConstant.cat_level[0])) && (parent_cat_id.equals(WicConstant.master_parent))){
			validate_cat = true;
		}
		if(cat_activity.equals("create")) {
			if((validateCategory_name(cat_name)) && (validateParent_category_id(parent_cat_id)) && (validateCategory_level(cat_level))) { 
				if((!cat_name.equals(WicConstant.tier_zero)) && ((cat_level.equals(WicConstant.cat_level[1])) ||  
					(cat_level.equals(WicConstant.cat_level[2])) || (cat_level.equals(WicConstant.cat_level[3])))){
				System.out.println("ValidateCat looks good.");
				validate_cat = true;
			}
		}
		}
		if(cat_activity.equals("update")) {
			if((validateCategoryNameForUpdate(cat_name)) && (validateParent_category_id(parent_cat_id)) && (validateCategory_level(cat_level))) { 
				if((!cat_name.equals(WicConstant.tier_zero)) && ((cat_level.equals(WicConstant.cat_level[1])) ||  
						(cat_level.equals(WicConstant.cat_level[2])) || (cat_level.equals(WicConstant.cat_level[3])))){
					System.out.println("ValidateCat looks good.");
					validate_cat = true;
				}
		}
		}
		return validate_cat;
	}

	public List<String> findByCategoryName(String cat_name) {
		try {
			Category category = null;
			List<Category> listCategory = new ArrayList<Category>();
			List<String> catNameList = new ArrayList<String>();
			System.out.println("Inside CategoryService.findByCatName() method");
			if(cat_name != null && !cat_name.equals(null) && cat_name.length() != 0) {
				category = catRepository.findByCategoryName(cat_name.toUpperCase());
				if(category != null && (category.getCategorylevel().equals(WicConstant.tier_zeroo) || 
					category.getCategorylevel().equals(WicConstant.tier_one) || category.getCategorylevel().equals(WicConstant.tier_two))) {
						catRepository.findByParentCategoryId(category.getCategoryid()).forEach(listCategory::add);
						for(int i=0; i < listCategory.size(); i++) {
						String catName = listCategory.get(i).getCategoryname();
						catNameList.add(catName);
						}
					}
				else if(category != null && category.getCategorylevel().equals(WicConstant.parent_cat)) {
					System.out.println("This method should not be called for the tier3");
					catNameList = null;
				}
				else {
					System.out.println("Category name not present in backend");
					catNameList = null;
				}
			}
			return catNameList;
		}
		catch(Exception e) {
			System.out.println("Something went wrong in the CategoryService.findByCategoryName method. Exception is:"+e);
			return null;
		}
	}
}
