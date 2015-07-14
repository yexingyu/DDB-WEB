/**
 * 
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  Page<Product> findByStoreId(int storeId, Pageable pageable);

  /**
   * findByStatus
   * 
   * @param status
   * @param pageable
   * @return
   */
  Page<Product> findByStatus(BaseEntityModel.STATUS status, Pageable pageable);
}
