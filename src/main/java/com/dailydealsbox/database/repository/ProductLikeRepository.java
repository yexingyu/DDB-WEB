/**
 *
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.ProductLike;

/**
 * @author x_ye
 */
public interface ProductLikeRepository extends CrudRepository<ProductLike, Integer> {

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
  public ProductLike findFirstByProductIdAndIpAndFingerprint(int productId, String ip, String fingerprint);
}
