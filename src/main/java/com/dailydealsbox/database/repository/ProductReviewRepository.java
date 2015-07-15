/**
 *
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.ProductReview;

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
}
