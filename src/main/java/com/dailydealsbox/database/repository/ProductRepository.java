/**
 * 
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.Product;

/**
 * @author x_ye
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
