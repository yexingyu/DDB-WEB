/**
 *
 */
package com.dailydealsbox.database.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
   * list
   *
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Store> list(boolean deleted, Pageable pageable);

  /**
   * update
   *
   * @param store
   * @return
   */
  public Store update(Store store);

  /**
   * insert
   *
   * @param store
   * @return
   */
  public Store insert(Store store);
}
