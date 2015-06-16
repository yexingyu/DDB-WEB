/**
 * 
 */
package com.dailydealsbox.service;

import java.util.List;

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

}
