/**
 *
 */
package com.dailydealsbox.database.service;

import com.dailydealsbox.database.model.Product;

/**
 * @author x_ye
 */
public interface SpiderService {

  /**
   * getProduct
   *
   * @param url
   * @return
   */
  public Product getProduct(String url);
}
