package com.selfserve.productapi.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.selfserve.productapi.product.entity.Inventory;

public interface InvRepository extends MongoRepository<Inventory, String>{

	Inventory findByWic(String wic);

}
