/**
 * 
 */
package com.dailydealsbox.database.service.impl;

import java.util.List;

import javax.annotation.Resource;

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
    return repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#getAll()
   */
  @Override
  public List<Store> getAll() {
    return (List<Store>) repo.findAll();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#update(com.dailydealsbox.database.model.Store)
   */
  @Override
  public Store update(Store store) {

    return repo.save(store);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#insert(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Store insert(Store store) {
    return this.update(store);
  }

}
