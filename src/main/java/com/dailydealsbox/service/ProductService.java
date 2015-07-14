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

  /**
   * delete
   * 
   * @param id
   */
  public void delete(int id);

  /**
   * listAllOnFrontEnd
   * 
   * @param pageable
   * @return
   */
  public Page<Product> listAllOnFrontEnd(Pageable pageable);

  /**
   * listByStoreIdOnFrontEnd
   * 
   * @param storeId
   * @param pageable
   * @return
   */
  public Page<Product> listByStoreIdOnFrontEnd(int storeId, Pageable pageable);

}
