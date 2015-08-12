/**
 *
 */
package com.dailydealsbox.database.service;

import java.util.Set;

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
   * listAll
   * 
   * @param deleted
   * @return
   */
  public Set<Store> listAll(boolean deleted);

  /**
   * listDefaultFollowed
   *
   * @return
   */
  public Set<Store> listDefaultFollowed();

  /**
   * listByIds
   *
   * @param ids
   * @return
   */
  public Set<Store> listByIds(Set<Integer> ids);

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

  /**
   * delete
   *
   * @param id
   */
  public void delete(int id);
}
