/**
 * 
 */
package com.dailydealsbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.base.BaseEntityModel;
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
   * @see com.dailydealsbox.service.ProductService#get(int)
   */
  @Override
  public Product get(int id) {
    return repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#getAll(org.springframework.data.domain.Pageable)
   */
  @Override
  public List<Product> getAll(Pageable pageable) {
    return repo.findByStatus(BaseEntityModel.STATUS.AVAILABLE, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#update(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Product update(Product product) {
    product.setProductForChildren();
    return repo.save(product);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#insert(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Product insert(Product product) {
    return this.update(product);
  }

  @Override
  public List<Product> findByStoreId(int storeId, Pageable pageable) {
    return repo.findByStoreId(storeId, pageable);
  }
}
