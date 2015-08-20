/**
 *
 */
package com.dailydealsbox.database.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.database.model.Store;
import com.dailydealsbox.database.repository.StoreRepository;
import com.dailydealsbox.database.service.StoreService;

/**
 * @author x_ye
 */
@Service
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

  @Resource
  private StoreRepository repo;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#get(int)
   */
  @Override
  public Store get(int id) {
    return this.repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#update(com.dailydealsbox.database.model.Store)
   */
  @Override
  public Store update(Store store) {
    return this.repo.save(store);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#insert(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Store insert(Store store) {
    return this.repo.save(store);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#delete(int)
   */
  @Override
  public void delete(int id) {
    this.repo.delete(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#listDefaultFollowed()
   */
  @Override
  public Set<Store> listDefaultFollowed() {
    return this.repo.findAllByDefaultFollowedTrueAndDeletedFalseOrderByIdAsc();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#listAll(java.util.Set, java.util.Set, boolean)
   */
  @Override
  public Set<Store> listAll(Set<Integer> ids, Set<COUNTRY> countries, boolean deleted) {
    Set<Store> stores = null;
    if (ids != null && countries != null) {
      stores = this.repo.findAllByIdsAndCountriesAndDeleted(ids, countries, deleted);
    } else if (ids != null) {
      stores = this.repo.findAllByIdsAndDeleted(ids, deleted);
    } else if (countries != null) {
      stores = this.repo.findAllByCountriesAndDeleted(countries, deleted);
    } else {
      stores = this.repo.findAllByDeleted(deleted);
    }
    return stores;
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#list(java.util.Set, java.util.Set, boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Store> list(Set<Integer> ids, Set<COUNTRY> countries, boolean deleted, Pageable pageable) {
    Page<Store> stores = null;
    if (ids != null && countries != null) {
      stores = this.repo.findByIdsAndCountriesAndDeleted(ids, countries, deleted, pageable);
    } else if (ids != null) {
      stores = this.repo.findByIdsAndDeleted(ids, deleted, pageable);
    } else if (countries != null) {
      stores = this.repo.findByCountriesAndDeleted(countries, deleted, pageable);
    } else {
      stores = this.repo.findByDeleted(deleted, pageable);
    }
    return stores;
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#increaseCountLikes(int)
   */
  @Override
  public void increaseCountLikes(int storeId) {
    this.repo.increaseCountLikes(storeId);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#increaseCountReviews(int)
   */
  @Override
  public void increaseCountReviews(int storeId) {
    this.repo.increaseCountReviews(storeId);
  }
}
