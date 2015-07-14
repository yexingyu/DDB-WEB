/**
 * 
 */
package com.dailydealsbox.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
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

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#delete(int)
   */
  @Override
  public void delete(int id) {
    Product product = repo.findOne(id);
    if (product != null) {
      product.setStatus(BaseEntityModel.STATUS.INACTIVE);
      repo.save(product);
    }
  }

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.service.ProductService#listAllOnFrontEnd(org.springframework.data.domain.
   * Pageable)
   */
  @Override
  public Page<Product> listAllOnFrontEnd(Pageable pageable) {
    return repo.findByStatusAndEnableOrderByCreatedAtDesc(BaseEntityModel.STATUS.AVAILABLE, true, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#listByStoreIdOnFrontEnd(int,
   * org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Product> listByStoreIdOnFrontEnd(int storeId, Pageable pageable) {
    return repo.findByStoreIdAndStatusAndEnableOrderByCreatedAtDesc(storeId, BaseEntityModel.STATUS.AVAILABLE, true, pageable);
  }
}
