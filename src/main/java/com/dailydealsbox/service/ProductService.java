/**
 * 
 */
package com.dailydealsbox.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dailydealsbox.database.model.Product;

/**
 * @author x_ye
 */
public interface ProductService {
  /**
   * get
   * 
   * @param id
   * @return
   */
  public Product get(int id);

  /**
   * getAll
   * 
   * @return
   */
  public List<Product> getAll();

  /**
   * findByStoreId
   * 
   * @param storeId
   * @return
   */
  public List<Product> findByStoreId(int storeId, Pageable pageable);

  /**
   * update
   * 
   * @param product
   * @return
   */
  public Product update(Product product);

  /**
   * insert
   * 
   * @param product
   * @return
   */
  public Product insert(Product product);
}
