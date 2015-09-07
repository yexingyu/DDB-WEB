/**
 *
 */
package com.dailydealsbox.web.database.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailydealsbox.web.configuration.BaseEnum;
import com.dailydealsbox.web.database.model.Order;

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
   * listAll
   *
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Order> listAll(boolean deleted, Pageable pageable);

  /**
   * listByMemberId
   * 
   * @param memberId
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Order> listByMemberId(int memberId, boolean deleted, Pageable pageable);

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
