package com.selfserve.productapi.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.selfserve.productapi.product.entity.Inventory;
import com.selfserve.productapi.product.entity.Wic;
import com.selfserve.productapi.product.repository.WicRepository;
import com.selfserve.productapi.product.utils.OutputWic;
import com.selfserve.productapi.product.utils.UpdateWic;
import com.selfserve.productapi.product.utils.WicConstant;
import com.selfserve.productapi.product.utils.WicInput;

@Service
public class WicService {
	
	@Autowired private WicRepository wicRepository;
	@Autowired private CategoryService catService;
	@Autowired private InventoryService invService;
	
	//This method will insert a Wic to backend. 
	public String wicInput(WicInput wicInput) {
		System.out.println("Inside WicService.wicInput() method");
		String insertWic = null;
		String fulfiller_type = wicInput.getFulfiller_type();
		double cost = wicInput.getCost();
		double price = wicInput.getPrice();
		String web_exclusive = wicInput.getWeb_exclusive();
		String status = wicInput.getStatus();
		boolean item_status_temp = wicInput.isReorder_status();
		boolean item_status = checkReorder(item_status_temp, fulfiller_type);
		String description = wicInput.getDescription();
		boolean free_item = wicInput.isFree_item();
		boolean free_shipping = wicInput.isFree_shipping();
		int purchase_limit = wicInput.getPurchase_limit();
		String category_name = wicInput.getCategory_name();
		String category_id = catService.findByCatName(category_name);
		if ((validateWicCheck(fulfiller_type, web_exclusive, status, cost, price, purchase_limit)) && 
				mandatoryWicCheck(description, category_id)) {
			boolean complete_cd = setCompleteCode(status);
			Wic sku = new Wic(fulfiller_type.toUpperCase(), cost, price, web_exclusive.toUpperCase(), status.toUpperCase(), complete_cd, item_status, 
						description, free_item, free_shipping, purchase_limit, category_id);
			try {
				wicRepository.save(sku);
				invService.inventoryInsert(sku.getWic());
				insertWic = "Wic Inserted sucessefully in DB";
			}
			catch(Exception e) {
			 System.out.println("Something went wrong while inserting WIC to DB"+e);	
			}
		}
		else {
			insertWic = "Criteria not met while doing the insert. Please check the support doc. or reach to dev. team";
		}
		return insertWic;
	}	
	
	// This method will update a WIC.
	public String wicUpdate(UpdateWic updateWic) {
		System.out.println("Inside WicService.wicUpdate() method");
		String wic = updateWic.getWic();
		Wic wicDetails = new Wic();
		wicDetails = getWicDetailsByWic(wic);
		String updateWicCheck = null;
		if(wicDetails!=null && wic != null && !wic.equals("null") && (wic.length() != 0)) { // Fix for Product-12
			String fulfiller_type = updateWic.getFulfiller_type();
			double cost = updateWic.getCost();
			double price = updateWic.getPrice();
			String web_exclusive = updateWic.getWeb_exclusive();
			String status = updateWic.getStatus();
			boolean item_status_temp = updateWic.isReorder_status();
			boolean item_status = checkReorder(item_status_temp, fulfiller_type);
			String description = updateWic.getDescription();
			boolean free_item = updateWic.isFree_item();
			boolean free_shipping = updateWic.isFree_shipping();
			int purchase_limit = updateWic.getPurchase_limit();
			String category_name = updateWic.getCategory_name();
			String category_id = catService.findByCatName(category_name);
			Date startDate = wicDetails.getStart_date();
			if ((validateWicCheck(fulfiller_type, web_exclusive, status, cost, price, purchase_limit)) && 
				mandatoryWicCheck(description, category_id)) {
				boolean complete_cd = setCompleteCode(status);
				Wic sku = new Wic(wic, fulfiller_type.toUpperCase(), cost, price, web_exclusive.toUpperCase(), status.toUpperCase(), complete_cd, item_status, 
						description, free_item, free_shipping, purchase_limit, category_id, startDate);
			try {
				wicRepository.save(sku);
				updateWicCheck = "Wic Updated sucessefully in DB";
			}
			catch(Exception e) {
			 System.out.println("Something went wrong while updating WIC to DB"+e);	
			}
		}
			else {
				updateWicCheck = "Criteria not met while for doing the update. Please check your support doc. reach to dev.";
			}
		}
		else {
			updateWicCheck = "Either the WIC is not present or the wic is not passed in the input. Update did not happen";
		}
		return updateWicCheck;
	}
	


	// This method will check the re-order flag.
	private boolean checkReorder(boolean item_status_temp, String fulfiller_type) {
		// TODO Auto-generated method stub
		System.out.println("Inside wicservice.checkReorder() method:"+item_status_temp+","+fulfiller_type);
		boolean check_reorder = false;
		if(item_status_temp) {
			String[] reorder_def = WicConstant.reorder_fulfiller;
			for (int i=0; i<reorder_def.length; i++) {
				if(reorder_def[i].equals(fulfiller_type)) {
					check_reorder = true;
					break;
				}
			}
		}
		System.out.println("Inside wicservice.checkReorder() method - Return value for reorder:"+check_reorder);
		return check_reorder;	
	}


	// This method will validate the  wic parameters. 
	private boolean validateWicCheck(String fulfiller_type, String web_exclusive, String status, double cost, double price, int purchase_limit ) {
		System.out.println("Inside WicService.validateWicCheck() method");
		boolean wic_check = false;
		boolean fulfiller_type_check = validateFulfillerType(fulfiller_type);
		boolean web_exclusive_check = validateWebExclusion(web_exclusive,cost, price, purchase_limit);
		boolean status_check = validateStatus(status);
		if((fulfiller_type_check) && (web_exclusive_check) && (status_check)) {
			wic_check = true;
			System.out.println("Inside WicService.validateWicCheck() method: WIC validation is true");
		}
		return wic_check;
	}
	
	// This methos will check the wic parameters. 
	private boolean mandatoryWicCheck(String category_id, String description) {
		System.out.println("Inside WicService.mandatoryWicCheck() method");
		boolean wicCheck = false;
		try {
			if((category_id != null && !category_id.equals("null") && (category_id.length() != 0)) 
					&& (description != null && !description.equals("null") && (description.length() != 0))) {
				System.out.println("Inside WicService.mandatoryWicCheck() method: Mandatory wic check is true");
				wicCheck = true;
			}
		}
		catch(Exception e) {
			System.out.println("Somethign went wrong inside the mandatoryWicCheck():"+e); 
		}
			return wicCheck;
	}
	
	// This method will validate the purchase limit.
	private boolean validateLimit(int limit) {
		// TODO Auto-generated method stub
		System.out.println("Inside WicService.validateLimit() method");
		if(limit > 0  && limit <= WicConstant.purchase_limit) {
			return true;
		}
		else
			return false;
	}

	// This method will validate the status.
	// Fix done for Product-6
	private boolean validateStatus(String status) {
		System.out.println("Inside WicService.validateStatus() method");
		boolean validate_status = false;
		try {
			if(status != null && !status.equals("null") && (status.length() != 0)) {
				if(status.toUpperCase().equals(WicConstant.status_active) || status.toUpperCase().equals(WicConstant.status_suspend) || 
						status.toUpperCase().equals(WicConstant.status_deactive) || status.toUpperCase().equals(WicConstant.status_new)){
					System.out.println("Status in input is valid");
					validate_status = true;
				}
			}
			else {
				System.out.println("Status in the input is not correct");
			}
		}
		catch (Exception e) {
			System.out.println("Something went wrong in the validateStatus() method");
		}
			
		return validate_status;
	}

	// This method will validate the web_exclusion. 
	// Fix done for Product-6
	private boolean validateWebExclusion(String web_exclusive, double cost, double price, int purchase_limit ) {
		System.out.println("Inside WicService.validateWebExclusion() method");
		System.out.println(web_exclusive+","+cost+","+price+","+purchase_limit);
		boolean web_exclusion = false;
		try {
			if(web_exclusive != null && !web_exclusive.equals("null") && (web_exclusive.length() != 0)) {
				if(web_exclusive.toUpperCase().equals(WicConstant.exclusive_web)  || web_exclusive.toUpperCase().equals(WicConstant.exclusive_web_store)) {
					if((cost > 0) && (price > 0) && validateLimit(purchase_limit) && (price > cost)) {
					System.out.println("Web Exclusion in input is valid");
					web_exclusion = true;
					}
				}
			else if(web_exclusive.toUpperCase().equals(WicConstant.exclusive_store)) {
				web_exclusion = true;
			}
			else {
				System.out.println("Web Exclusion in input is not valid");
				}
			}
		}
		catch(Exception e) {
			System.out.println("Something went wrong in the validateWebExclusion():"+e);
		}
		return web_exclusion;
	}

	
	// This method will validate the fulfiller type.
	// Changes done for fixing Product-6.
	private boolean validateFulfillerType(String fulfiller_type) {
		// TODO Auto-generated method stub
		System.out.println("Inside WicService.validateFulfillerType() method");
		boolean fulfiller = false;
		try {
		if(fulfiller_type != null && !fulfiller_type.equals("null") && (fulfiller_type.length() != 0)) {
		if(fulfiller_type.equals(WicConstant.fulfiller_ch) || fulfiller_type.equals(WicConstant.fulfiller_cl) || 
				fulfiller_type.equals(WicConstant.fulfiller_dc)) {
			fulfiller = true;
			System.out.println("Fulfiller type is valid");
			}
		}
		else {
			System.out.println("Fulfiller type in the input is not valid");
		}
		}
		catch(Exception e) {
			System.out.println("Something went wrong in the validateFulfillerType method:"+e);
		}
		return fulfiller;
	}
	
	// This method will set the complete code. 
	// Fix for Product-6
	private boolean setCompleteCode(String status) {
		System.out.println("Inside WicService.setCompleteCode() method");
		boolean complete_cd = false;
		if (status.toUpperCase().equals(WicConstant.status_active)) {
				complete_cd = true;
		}
		return complete_cd;
	}
	
	// This is the main method responsible for getting data from DB for all WIC's and converting to a expected format. 
	public List<Wic> getAllWicDetails() {
		System.out.println("Inside WicService.getAllWic() method");
		List<Wic> list_wic= new ArrayList<Wic>();
		try {
		wicRepository.findAll().forEach(list_wic::add);
		}
		catch(Exception e) {
			System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
		}
		return list_wic;
	}
	
	public List<OutputWic> getLiveWic() {
		System.out.println("Inside WicService.getLiveWic() method");
		List<OutputWic> listOutputWic = new ArrayList<OutputWic>();
		List<Wic> listWic = new ArrayList<Wic>();
		Wic wicDetails = new Wic();
		Inventory inv = new Inventory();
		try {
			wicRepository.findByCompleteCd(true).forEach(listWic::add);
		}
		catch(Exception e) {
			System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
		}
		if(listWic != null) {
			for (int i=0; i<listWic.size(); i++) {
				wicDetails = listWic.get(i);
				try {
					inv = invService.getInvDetails(wicDetails.getWic());	
				}
				catch(Exception e) {
					System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
				}
				if(inv != null) {
					OutputWic outWic = new OutputWic();
					outWic.setWic(wicDetails.getWic());
					outWic.setCategoryId(wicDetails.getCategoryId());
					outWic.setDescription(wicDetails.getDescription());
					outWic.setFulfillerType(wicDetails.getFulfiller_type());
					outWic.setItemStatus(wicDetails.isItemStatus());
					outWic.setPrice(wicDetails.getPrice());
					outWic.setPurchaseLimit(wicDetails.getPurchaseLimit());
					outWic.setReorderStatus(wicDetails.isReorderStatus());
					outWic.setShippingStatus(wicDetails.isShippingStatus());
					outWic.setWebExclusive(wicDetails.getWebExclusive());
					if(wicDetails.getWebExclusive().equals(WicConstant.exclusive_web) || wicDetails.getWebExclusive().equals(WicConstant.exclusive_web_store)) {
					outWic.setAvailStatus(inv.getAvailStatus());
					outWic.setStockLevel(inv.getStockLevel());
					outWic.setThreshold(inv.getStockLevel());
					}
					else if(wicDetails.getWebExclusive().equals(WicConstant.exclusive_store)) {
						outWic.setAvailStatus(null);
						outWic.setStockLevel(-1);
						outWic.setThreshold(-1);
					}
					listOutputWic.add(outWic);
				}
				else {
					System.out.println("Wic Exist but without inventory:"+wicDetails.getWic());
				}
			}
		}
		return 	listOutputWic;
	}
	
	public List<OutputWic> getLiveInstockWic() {
		System.out.println("Inside WicService.getLiveInstockWic() method");
		List<OutputWic> listOutputWic = new ArrayList<OutputWic>();
		List<Wic> listWic = new ArrayList<Wic>();
		Wic wicDetails = new Wic();
		Inventory inv = new Inventory();
		try {
			wicRepository.findByCompleteCd(true).forEach(listWic::add);
		}
		catch(Exception e) {
			System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
		}
		if(listWic != null) {
			for (int i=0; i<listWic.size(); i++) {
				wicDetails = listWic.get(i);
				try {
					inv = invService.getInvDetails(wicDetails.getWic());	
				}
				catch(Exception e) {
					System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
				}
				if(inv != null && inv.getAvailStatus().equals(WicConstant.avail_status_instock) && 
						(wicDetails.getWebExclusive().equals(WicConstant.exclusive_web) || wicDetails.getWebExclusive().equals(WicConstant.exclusive_web_store))) {
					OutputWic outWic = new OutputWic();
					outWic.setWic(wicDetails.getWic());
					outWic.setCategoryId(wicDetails.getCategoryId());
					outWic.setDescription(wicDetails.getDescription());
					outWic.setFulfillerType(wicDetails.getFulfiller_type());
					outWic.setItemStatus(wicDetails.isItemStatus());
					outWic.setPrice(wicDetails.getPrice());
					outWic.setPurchaseLimit(wicDetails.getPurchaseLimit());
					outWic.setReorderStatus(wicDetails.isReorderStatus());
					outWic.setShippingStatus(wicDetails.isShippingStatus());
					outWic.setWebExclusive(wicDetails.getWebExclusive());
					outWic.setAvailStatus(inv.getAvailStatus());
					outWic.setStockLevel(inv.getStockLevel());
					outWic.setThreshold(inv.getStockLevel());
					listOutputWic.add(outWic);
					}
				else {
					System.out.println("WIC either does not have inventory entry or WIC is OOS:"+wicDetails.getWic());
				}
			}
		}
		return 	listOutputWic;
	}
	
	// This method will get the WIC's corresponding to a category. 
	public List<OutputWic> getAllWicDetailsByCatName(String categoryName) {
		System.out.println("Inside WicService.getAllWicByCatName() method");
		List<OutputWic> listOutputWic = new ArrayList<OutputWic>();
		String categoryId = catService.findByCatName(categoryName);
		List<Wic> listWic= new ArrayList<Wic>();
		Wic wicDetails = new Wic();
		Inventory inv = new Inventory();
		try {
			wicRepository.findByCategoryId(categoryId).forEach(listWic::add);
		}
		catch(Exception e) {
			System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
		}
		if(listWic != null) {
			for (int i=0; i<listWic.size(); i++) {
				wicDetails = listWic.get(i);
				try {
					inv = invService.getInvDetails(wicDetails.getWic());	
				}
				catch(Exception e) {
					System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
				}
				if(inv != null && wicDetails.isCompleteCd()) {
					OutputWic outWic = new OutputWic();
					outWic.setWic(wicDetails.getWic());
					outWic.setCategoryId(wicDetails.getCategoryId());
					outWic.setDescription(wicDetails.getDescription());
					outWic.setFulfillerType(wicDetails.getFulfiller_type());
					outWic.setItemStatus(wicDetails.isItemStatus());
					outWic.setPrice(wicDetails.getPrice());
					outWic.setPurchaseLimit(wicDetails.getPurchaseLimit());
					outWic.setReorderStatus(wicDetails.isReorderStatus());
					outWic.setShippingStatus(wicDetails.isShippingStatus());
					outWic.setWebExclusive(wicDetails.getWebExclusive());
					if(wicDetails.getWebExclusive().equals(WicConstant.exclusive_web) || wicDetails.getWebExclusive().equals(WicConstant.exclusive_web_store)) {
						outWic.setAvailStatus(inv.getAvailStatus());
						outWic.setStockLevel(inv.getStockLevel());
						outWic.setThreshold(inv.getStockLevel());
						}
						else if(wicDetails.getWebExclusive().equals(WicConstant.exclusive_store)) {
							outWic.setAvailStatus(null);
							outWic.setStockLevel(-1);
							outWic.setThreshold(-1);
						}
						listOutputWic.add(outWic);
				}
				else {
					System.out.println("WIC either does not have inventory entry or WIC is not live"+wicDetails.getWic());
				}
			}
		}
		return 	listOutputWic;
	}
	
	// This method will get the WIC details based on the WIC number. 
	public OutputWic getOutputWicDetailsByWic(String wic) {
		System.out.println("Inside WicService.getWicDetailsByWic() method");
		OutputWic outputWic = new OutputWic();
		Wic wicDetails = new Wic();
		Inventory inv = new Inventory();
		if(wic != null && !wic.equals("null") && (wic.length() != 0)) {
			try {
				wicDetails = wicRepository.findById(wic).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
			}
			if(wicDetails != null) {
				try {
					inv = invService.getInvDetails(wic);
				}
				catch(Exception e) {
					System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
				}
				if(inv != null && wicDetails.isCompleteCd()) {
					outputWic.setWic(wicDetails.getWic());
					outputWic.setCategoryId(wicDetails.getCategoryId());
					outputWic.setDescription(wicDetails.getDescription());
					outputWic.setFulfillerType(wicDetails.getFulfiller_type());
					outputWic.setItemStatus(wicDetails.isItemStatus());
					outputWic.setPrice(wicDetails.getPrice());
					outputWic.setPurchaseLimit(wicDetails.getPurchaseLimit());
					outputWic.setReorderStatus(wicDetails.isReorderStatus());
					outputWic.setShippingStatus(wicDetails.isShippingStatus());
					outputWic.setWebExclusive(wicDetails.getWebExclusive());
					if(wicDetails.getWebExclusive().equals(WicConstant.exclusive_web) || wicDetails.getWebExclusive().equals(WicConstant.exclusive_web_store)) {
						outputWic.setAvailStatus(inv.getAvailStatus());
						outputWic.setStockLevel(inv.getStockLevel());
						outputWic.setThreshold(inv.getStockLevel());
						}
						else if(wicDetails.getWebExclusive().equals(WicConstant.exclusive_store)) {
							outputWic.setAvailStatus(null);
							outputWic.setStockLevel(-1);
							outputWic.setThreshold(-1);
						}
			}
			else {
				System.out.println("Wic either does not have inventory entry or WIC is not live"+wicDetails.getWic());
			}
		}
	}
	return outputWic;	
	}
	
	public Wic getWicDetailsByWic(String wic) {
		System.out.println("Inside WicService.getWicDetailsByWic() method");
		Wic wicDetails = new Wic();
		if(wic != null && !wic.equals("null") && (wic.length() != 0)) {
			try {
				wicDetails = wicRepository.findById(wic).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching the WIC. Exception is:"+e);
			}
		}
		return wicDetails;
	}
	
}
	
