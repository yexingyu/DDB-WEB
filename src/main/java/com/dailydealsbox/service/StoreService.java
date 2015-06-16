/**
 * 
 */
package com.dailydealsbox.service;

import java.util.List;

import com.dailydealsbox.database.model.Store;

/**
 * @author x_ye
 */
public interface StoreService {
  /**
   * get
   * 
   * @param id
   * @return
   */
  public Store get(int id);

  /**
   * getAll
   * 
   * @return
   */
  public List<Store> getAll();

}
