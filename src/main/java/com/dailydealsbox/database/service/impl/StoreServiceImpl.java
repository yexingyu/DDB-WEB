/**
 *
 */
package com.dailydealsbox.database.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
   * @see com.dailydealsbox.database.service.StoreService#list(boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Store> list(boolean deleted, Pageable pageable) {
    return this.repo.findByDeleted(deleted, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#delete(int)
   */
  @Override
  public void delete(int id) {
    this.repo.delete(id);
  }

}
