package com.selfserve.productapi.product.repository;



import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.selfserve.productapi.product.entity.Category;


public interface CatRepository extends MongoRepository<Category, String> {

	List<Category> findByParentCategoryId(String parent_cat_id);

	Category findByCategoryName(String upperCase);

}
