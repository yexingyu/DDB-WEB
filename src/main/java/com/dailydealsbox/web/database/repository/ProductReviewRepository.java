/**
 *
 */
package com.dailydealsbox.web.database.repository;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.web.database.model.ProductReview;

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
   * findFirstByProductIdAndIpAndFingerprintAndDeletedFalse
   *
   * @param productId
   * @param ip
   * @param fingerprint
   * @return
   */
  public ProductReview findFirstByProductIdAndIpAndFingerprintAndDeletedFalse(int productId, String ip, String fingerprint);

  /**
   * findByProductIdAndDeleted
   * 
   * @param productId
   * @param deleted
   * @param pageable
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<ProductReview> findByProductIdAndDeleted(int productId, boolean deleted, Pageable pageable);
}
