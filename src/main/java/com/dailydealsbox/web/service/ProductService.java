/**
 *
 */
package com.dailydealsbox.web.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailydealsbox.web.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.web.database.model.Member;
import com.dailydealsbox.web.database.model.Product;
import com.dailydealsbox.web.database.model.ProductLike;
import com.dailydealsbox.web.database.model.ProductReview;
import com.dailydealsbox.web.database.model.ProductTag;

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
   * @param storeIds
   * @param tags
   * @param countries
   * @param member
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   * @throws Exception
   */
  public Page<Product> list(Set<Integer> storeIds, Set<String> tags, Set<COUNTRY> countries, Member member, boolean deleted, boolean disabled, Pageable pageable) throws Exception;

  /**
   * listAll
   *
   * @param ids
   * @param storeIds
   * @param tags
   * @param countries
   * @param member
   * @param deleted
   * @param disabled
   * @return
   * @throws Exception
   */
  public Set<Product> listAll(Set<Integer> ids, Set<Integer> storeIds, Set<String> tags, Set<COUNTRY> countries, Member member, boolean deleted, boolean disabled) throws Exception;

  /**
   * addLike
   *
   * @param productId
   * @param fingerprint
   * @param ip
   * @param positive
   * @return
   */
  public int addLike(int productId, String fingerprint, String ip, boolean positive);

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

  /**
   * listAllTag
   *
   * @return
   */
  public Set<ProductTag> listAllTag();

  /**
   * fixProduct
   *
   * @throws Exception
   */
  public void fixProduct() throws Exception;

  /**
   * search
   *
   * @param keyword
   * @param pageable
   * @return
   */
  public Page<Product> search(String keyword, Pageable pageable) throws Exception;

  /**
   * updateProductBySpider
   */
  public void updateProductBySpider();
}
