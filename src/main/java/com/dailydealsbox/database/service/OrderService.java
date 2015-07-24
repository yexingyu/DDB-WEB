/**
 *
 */
package com.dailydealsbox.database.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailydealsbox.database.model.Order;
import com.dailydealsbox.database.model.base.BaseEnum;

/**
 * @author x_ye
 */
public interface OrderService {
  /**
   * get
   *
   * @param id
   * @return
   */
  public Order get(int id);

  /**
   * list
   *
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Order> list(boolean deleted, Pageable pageable);

  /**
   * insert
   *
   * @param order
   * @return
   */
  public Order insert(Order order);

  /**
   * update
   *
   * @param order
   * @return
   */
  public Order update(Order order);

  /**
   * delete
   *
   * @param id
   */
  public void delete(int id);

  /**
   * modifiyStatus
   * 
   * @param id
   * @param status
   * @return
   */
  public Order modifiyStatus(int id, BaseEnum.ORDER_STATUS status);
}
