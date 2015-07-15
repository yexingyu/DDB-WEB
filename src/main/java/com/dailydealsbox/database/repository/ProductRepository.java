/**
 *
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {

  //  @Query(value = "select p from Product p where store_id = :storeId and status = 0 order by id desc",
  //    countProjection = ":offset",
  //    countQuery = ":cnt")
  //  List<Product> findByStoreId(@Param("storeId") int storeId, @Param("offset") int offset, @Param("cnt") int cnt);

  /**
   * findByStoreId
   *
   * @param storeId
   * @param pageable
   * @return
   */
  public Page<Product> findByStoreId(int storeId, Pageable pageable);

  /**
   * findByStoreIdAndStatusAndEnableOrderByCreateAtDesc
   *
   * @param storeId
   * @param status
   * @param enable
   * @param pageable
   * @return
   */
  public Page<Product> findByStoreIdAndStatusAndEnableOrderByCreatedAtDesc(int storeId, BaseEntityModel.STATUS status, boolean enable, Pageable pageable);

  /**
   * findByStatus
   *
   * @param status
   * @param pageable
   * @return
   */
  public Page<Product> findByStatus(BaseEntityModel.STATUS status, Pageable pageable);

  /**
   * findByStatusAndEnableOrderByCreateAtDesc
   *
   * @param status
   * @param enable
   * @param pageable
   * @return
   */
  public Page<Product> findByStatusAndEnableOrderByCreatedAtDesc(BaseEntityModel.STATUS status, boolean enable, Pageable pageable);

  /**
   * increaseCountLikes
   *
   * @param productId
   */
  @Modifying
  @Query("update Product p set p.countLikes = p.countLikes + 1 where p.id = ?1")
  public void increaseCountLikes(int productId);

  /**
   * increaseCountReviews
   * 
   * @param productId
   */
  @Modifying
  @Query("update Product p set p.countReviews = p.countReviews + 1 where p.id = ?1")
  public void increaseCountReviews(int productId);
}
