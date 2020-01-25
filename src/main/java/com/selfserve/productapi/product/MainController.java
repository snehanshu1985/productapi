package com.selfserve.productapi.product;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.selfserve.productapi.product.entity.Category;
import com.selfserve.productapi.product.entity.Inventory;
import com.selfserve.productapi.product.entity.Wic;
import com.selfserve.productapi.product.utils.InsertCat;
import com.selfserve.productapi.product.utils.InventoryInput;
import com.selfserve.productapi.product.utils.OutputWic;
import com.selfserve.productapi.product.utils.UpdateCat;
import com.selfserve.productapi.product.utils.UpdateWic;
import com.selfserve.productapi.product.utils.WicInput;
import com.selfserve.productapi.product.service.CategoryService;
import com.selfserve.productapi.product.service.InventoryService;
import com.selfserve.productapi.product.service.WicService;



@RestController
public class MainController {
	
	@Autowired private CategoryService categoryService;
	@Autowired private WicService wicService;
	@Autowired private InventoryService invService;

	@RequestMapping("/product/test")
	public String defaultResponse() {
		return "Service is up and running. Enjoy the pipeline.";
	}
	

// The below section is for category level pages. 
	
	@RequestMapping("/product/category/displaycategory")
	public List<Category> getAllWic() {
		List<Category> list_cat = new ArrayList<Category>(); 
		list_cat=categoryService.getAllCategory();
		if(list_cat!=null) {
			System.out.println("Categories fetched  sucessefully from backend");
			return list_cat;
		}
		else {
			System.out.println("No categories found or some error in fetching the categories. Please try again");
			return list_cat;
		}	
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/product/category/createcategory")
	public String insertCat(@RequestBody InsertCat insertCat) {
		return  categoryService.insertCat(insertCat);
	}
	
	@RequestMapping("/product/category/findbycatid={cat_id}")
	public Category findByCatId(@PathVariable String cat_id) {
		return categoryService.findByCatId(cat_id);
	}
	
	@RequestMapping("/product/category/findbycatname={cat_name}")
	public List<String> findByCatName(@PathVariable String cat_name) {
		return categoryService.findByCategoryName(cat_name);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/product/category/updatecategory")
	public String updateCat(@RequestBody UpdateCat updatecat) {
		return categoryService.updateCat(updatecat);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/product/wic/insertwic")
	public String insertWic(@RequestBody WicInput wicInput) {
		return wicService.wicInput(wicInput);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/product/wic/updatewic")
	public String updateWic(@RequestBody UpdateWic updateWic) {
		return wicService.wicUpdate(updateWic);
	}
	
	@RequestMapping("/product/wic/getAllWic")
	public List<Wic> getAllWics() {
		List<Wic> list_wic = new ArrayList<Wic>();
		list_wic = wicService.getAllWicDetails();
		if(list_wic!=null) {
			System.out.println("Wic's fetched  sucessefully from backend");
			return list_wic;
		}
		else{
			System.out.println("No Wic's found or some error in fetching the Wic's. Please try again");
			return list_wic;
		}	
	}
	
	@RequestMapping("/product/wic/getLiveWic")
		public List<OutputWic> getLiveWic() {
			List<OutputWic> list_wic = new ArrayList<OutputWic>();
			list_wic = wicService.getLiveWic();
			if(list_wic!=null) {
				System.out.println("Wic's fetched  sucessefully from backend");
				return list_wic;
			}
			else{
				System.out.println("No Wic's found or some error in fetching the Wic's. Please try again");
				return list_wic;
			}	
		}
	
	@RequestMapping("/product/wic/getLiveInstockWic")
	public List<OutputWic> getLiveInstockWic() {
		List<OutputWic> list_wic = new ArrayList<OutputWic>();
		list_wic = wicService.getLiveInstockWic();
		if(list_wic!=null) {
			System.out.println("Wic's fetched  sucessefully from backend");
			return list_wic;
		}
		else{
			System.out.println("No Wic's found or some error in fetching the Wic's. Please try again");
			return list_wic;
		}	
	}
	
	@RequestMapping("/product/wic/getAllWicDetailsByCatName={catName}")
	public List<OutputWic> getAllWicDetailsByCatName(@PathVariable String catName) {
		return wicService.getAllWicDetailsByCatName(catName);
	}
	
	@RequestMapping("/product/wic/getWicDetailsByWic={wic}")
	public OutputWic getWicDetailsByWic(@PathVariable String wic) {
		return wicService.getOutputWicDetailsByWic(wic);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/product/inventory/updateinventory") 
	public String updateInventory(@RequestBody InventoryInput inventoryInput) {
		return invService.inventoryUpdate(inventoryInput);
	}
	
	@RequestMapping("/product/inventory/getInvStatus={wic}")
	public String getInvStatus(@PathVariable String wic) {
		return invService.getInvStatus(wic);
	}
	
	@RequestMapping("/product/inventory/getInvDetails={wic}")
	public Inventory getInvDetails(@PathVariable String wic) {
		return invService.getInvDetails(wic);
	}
	
	@RequestMapping("/product/inventory/getStockLevel={wic}")
	public int getStockLevel(@PathVariable String wic) {
		return invService.getStockLevel(wic);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/product/inventory/updateInventory={wic}&quantity={quantity}")
	public boolean updateInventory(@PathVariable String wic, @PathVariable int quantity) {
		return invService.updateInventory(wic, quantity);
	}
}
