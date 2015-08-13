/**
 *
 */
package com.dailydealsbox.database.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailydealsbox.configuration.BaseEnum.COUNTRY;
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
   * listAll
   *
   * @param ids
   * @param deleted
   * @return
   */
  public Set<Store> listAll(Set<Integer> ids, boolean deleted);

  /**
   * listAll
   *
   * @param deleted
   * @param countries
   * @return
   */
  public Set<Store> listAll(boolean deleted, Set<COUNTRY> countries);

  /**
   * listAll
   * 
   * @param ids
   * @param countries
   * @param deleted
   * @return
   */
  public Set<Store> listAll(Set<Integer> ids, Set<COUNTRY> countries, boolean deleted);

  /**
   * listDefaultFollowed
   *
   * @return
   */
  public Set<Store> listDefaultFollowed();

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
