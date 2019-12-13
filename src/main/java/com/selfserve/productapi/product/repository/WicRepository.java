package com.selfserve.productapi.product.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.selfserve.productapi.product.entity.Wic;

public interface WicRepository extends MongoRepository<Wic, String> {

	List<Wic> findByCategoryId(String categoryId);

	List<Wic> findByCompleteCd(boolean b);

}
