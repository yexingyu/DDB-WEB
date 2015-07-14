/**
 * 
 */
package com.dailydealsbox.service;

import org.springframework.data.domain.Page;
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
   * @param pageable
   * @return
   */
  public Page<Product> getAll(Pageable pageable);

  /**
   * findByStoreId
   * 
   * @param storeId
   * @return
   */
  public Page<Product> findByStoreId(int storeId, Pageable pageable);

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
