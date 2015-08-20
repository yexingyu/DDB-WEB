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
   * @param ids
   * @param countries
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Store> list(Set<Integer> ids, Set<COUNTRY> countries, boolean deleted, Pageable pageable);

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
   * increaseCountLikes
   *
   * @param storeId
   */
  public void increaseCountLikes(int storeId);

  /**
   * increaseCountFollowings
   *
   * @param storeId
   */
  public void increaseCountFollowings(int storeId);

  /**
   * decreaseCountFollowings
   * 
   * @param storeId
   */
  public void decreaseCountFollowings(int storeId);

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
