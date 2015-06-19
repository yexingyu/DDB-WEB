/**
 * 
 */
package com.dailydealsbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.ProductDescription;
import com.dailydealsbox.database.model.ProductFee;
import com.dailydealsbox.database.model.ProductImage;
import com.dailydealsbox.database.model.ProductName;
import com.dailydealsbox.database.model.ProductPrice;
import com.dailydealsbox.database.model.ProductTax;
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

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#update(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Product update(Product product) {
    return repo.save(product);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#insert(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Product insert(Product product) {
    for (ProductDescription o : product.getDescriptions()) {
      o.setProduct(product);
    }
    for (ProductFee o : product.getFees()) {
      o.setProduct(product);
    }
    for (ProductImage o : product.getImages()) {
      o.setProduct(product);
    }
    for (ProductName o : product.getNames()) {
      o.setProduct(product);
    }
    for (ProductPrice o : product.getPrices()) {
      o.setProduct(product);
    }
    for (ProductTax o : product.getTaxes()) {
      o.setProduct(product);
    }

    return repo.save(product);
  }

}
