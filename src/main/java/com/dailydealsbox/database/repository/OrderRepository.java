/**
 *
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dailydealsbox.database.model.Order;
import com.dailydealsbox.database.model.base.BaseEnum;

/**
 * @author x_ye
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {

  /**
   * findByDeleted
   *
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Order> findByDeleted(boolean deleted, Pageable pageable);

  /**
   * modifyStatus
   * 
   * @param orderId
   * @param status
   */
  @Modifying
  @Query("update Order o set o.status = :status where o.id = :orderId")
  public void modifyStatus(@Param("orderId") int orderId, @Param("status") BaseEnum.ORDER_STATUS status);
}
