/**
 *
 */
package com.dailydealsbox.web.database.service;

import com.dailydealsbox.web.database.model.Product;

/**
 * @author x_ye
 */
public interface SpiderService {

  /**
   * getProduct
   * 
   * @param url
   * @return
   * @throws Exception
   */
  public Product getProduct(String url) throws Exception;
}
