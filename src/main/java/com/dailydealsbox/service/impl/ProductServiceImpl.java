/**
 * 
 */
package com.dailydealsbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.repository.ProductRepository;
import com.dailydealsbox.service.ProductService;

/**
 * @author x_ye
 */
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

  @Resource
  private ProductRepository repo;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#get(int)
   */
  @Override
  public Product get(int id) {
    return repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#getAll()
   */
  @Override
  public List<Product> getAll() {
    return (List<Product>) repo.findAll();
  }

}
