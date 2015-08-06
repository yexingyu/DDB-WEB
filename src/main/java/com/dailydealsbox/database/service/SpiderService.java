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
   * getProductFromBestbuy
   * 
   * @param url
   * @return
   */
  public Product getProductFromBestbuy(String url);
}
