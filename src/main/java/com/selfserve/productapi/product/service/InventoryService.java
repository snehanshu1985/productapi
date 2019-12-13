package com.selfserve.productapi.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.selfserve.productapi.product.entity.Inventory;
import com.selfserve.productapi.product.repository.InvRepository;
import com.selfserve.productapi.product.utils.InventoryInput;
import com.selfserve.productapi.product.utils.WicConstant;

/* 
 * This class defines Services to insert inventory for an item, update inventory for an item, 
 * get inventory status, get stock level and update inventory.
 */
@Service
public class InventoryService {
	
	@Autowired InvRepository invRepository;
	
	
		void inventoryInsert(String wic) {
			System.out.println("Inside WicService.inventoryInsert() method");
			Inventory inv = new Inventory();
			Inventory tempInv = new Inventory();
			inv.setStockLevel(0);
			inv.setThreshold(0);
			inv.setWic(wic);
			inv.setCreateDttm();
			inv.setUpdateDttm();
			inv.setAvailStatus(inv.getStockLevel(), inv.getThreshold());
			inv.setSource(WicConstant.source_business);
			try {
				tempInv = invRepository.findById(wic).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching data from DB. Exception is:"+e);
			}
			if(tempInv == null) {
				try {
					invRepository.save(inv);
				}
				catch(Exception e) {
					System.out.println("Something went wrong while inserting data to DB. Exception is:"+e);
				}
			}
		}
		
		public String inventoryUpdate(InventoryInput invInput) {
			System.out.println("Inside InventoryService.inventoryUpdate() method");
			String updateStatus = null;
			Inventory inv = null;
			int stockLevel = -1; 
			int thresHold = -1;
			try {
				inv = invRepository.findById(invInput.getWic()).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching data from DB. Exception is:"+e);
			}
			if(inv == null) {
				updateStatus = "Wic does not exist in Inventory Table. Please create the WIC";
			}
			else {
				try {
					inv.setWic(invInput.getWic());
					stockLevel = Integer.parseInt(invInput.getStockLevel());
					thresHold = Integer.parseInt(invInput.getThreshold());
				}
				catch(Exception e) {
					System.out.println("Something wrong in the input. Exception is:"+e);
					updateStatus = "Something wrong in the input. Please correct it and retry";
					return updateStatus;
				}
				inv.setStockLevel(stockLevel);
				inv.setThreshold(thresHold);
				inv.setAvailStatus(inv.getStockLevel(), inv.getThreshold());
				inv.setUpdateDttm();
				inv.setSource(WicConstant.source_business);
				try {
					invRepository.save(inv);
				}
				catch(Exception e) {
					System.out.println("Something went wrong while inserting data to DB. Exception is:"+e);
				}
				updateStatus = "Inventory updated sucessefully";
				}
				return updateStatus;
		}
		
		public String getInvStatus(String wic) {
			System.out.println("Inside InventoryService.getInvStatus() method");
			String invStatus = null;
			Inventory inv = null;
			try {
				inv = invRepository.findById(wic).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching data from DB. Exception is:"+e);
			}
			if(inv != null) {
				invStatus=inv.getAvailStatus();
			}
			return invStatus;
		}
		
		public Inventory getInvDetails(String wic) {
			System.out.println("Inside InventoryService.getInvDetails() method");
			Inventory inv = new Inventory();
			try {
				inv = invRepository.findById(wic).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching data from DB. Exception is:"+e);
			}
			return inv;
		}
		
		public int getStockLevel(String wic) {
			System.out.println("Inside InventoryService.getStockLevel() method");
			int stockLevel = -1;
			Inventory inv = null;
			try {
				inv = invRepository.findById(wic).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching data from DB. Exception is:"+e);
			}
			if(inv != null) {
				stockLevel = inv.getStockLevel();
			}
			return stockLevel;
		}
		
		public boolean updateInventory(String wic, int quantity) {
			System.out.println("Inside InventoryService.updateInventory() method");
			int newInv;
			boolean updateInv = false;
			Inventory inv = null;
			try {
				inv = invRepository.findById(wic).orElse(null);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while fetching data from DB. Exception is:"+e);
			}
			if (inv != null) {
				newInv = inv.getStockLevel() - quantity;
				inv.setStockLevel(newInv);
				inv.setUpdateDttm();
				inv.setAvailStatus(newInv, inv.getThreshold());
				inv.setSource(WicConstant.source_app);
			try {
				invRepository.save(inv);
			}
			catch(Exception e) {
				System.out.println("Something went wrong while inserting data to DB. Exception is:"+e);
			}
			updateInv = true;
			}
			return updateInv;
		}
		
}
