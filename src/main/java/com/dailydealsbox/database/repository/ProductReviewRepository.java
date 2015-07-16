/**
 *
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.ProductReview;
import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
public interface ProductReviewRepository extends CrudRepository<ProductReview, Integer> {

  /**
   * countByProductIdAndIp
   *
   * @param productId
   * @param ip
   * @return
   */
  public long countByProductIdAndIp(int productId, String ip);

  /**
   * findFirstByProductIdAndIpAndFingerprint
   *
   * @param productId
   * @param ip
   * @param fingerprint
   * @return
   */
  public ProductReview findFirstByProductIdAndIpAndFingerprint(int productId, String ip, String fingerprint);

  /**
   * findByProductIdAndStatusOrderByCreatedAtDesc
   *
   * @param productId
   * @param status
   * @param pageable
   * @return
   */
  public Page<ProductReview> findByProductIdAndStatusOrderByCreatedAtDesc(int productId, BaseEntityModel.STATUS status, Pageable pageable);
}
