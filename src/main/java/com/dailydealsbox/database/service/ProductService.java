/**
 *
 */
package com.dailydealsbox.database.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.ProductLike;
import com.dailydealsbox.database.model.ProductReview;

/**
 * @author x_ye
 */
public interface ProductService {

  /**
   * get
   *
   * @param id
   * @return
   */
  public Product get(int id);

  /**
   * update
   *
   * @param product
   * @return
   */
  public Product update(Product product);

  /**
   * insert
   *
   * @param product
   * @return
   */
  public Product insert(Product product);

  /**
   * delete
   *
   * @param id
   */
  public void delete(int id);

  /**
   * deleteReview
   *
   * @param reviewId
   */
  public void deleteReview(int reviewId);

  /**
   * list
   *
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  public Page<Product> list(boolean deleted, boolean disabled, Pageable pageable);

  /**
   * list
   *
   * @param storeId
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  public Page<Product> list(int storeId, boolean deleted, boolean disabled, Pageable pageable);

  /**
   * list
   * 
   * @param tags
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  public Page<Product> list(Set<String> tags, boolean deleted, boolean disabled, Pageable pageable);

  /**
   * addLike
   *
   * @param productId
   * @param fingerprint
   * @param ip
   * @return
   */
  public int addLike(int productId, String fingerprint, String ip);

  /**
   * addReview
   *
   * @param review
   * @return
   */
  public int addReview(ProductReview review);

  /**
   * listReview
   *
   * @param productId
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<ProductReview> listReview(int productId, boolean deleted, Pageable pageable);

  /**
   * listLike
   *
   * @param productId
   * @param pageable
   * @return
   */
  public Page<ProductLike> listLike(int productId, Pageable pageable);

}
