/**
 * 
 */
package com.dailydealsbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.model.Store;
import com.dailydealsbox.database.repository.StoreRepository;
import com.dailydealsbox.service.StoreService;

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

}