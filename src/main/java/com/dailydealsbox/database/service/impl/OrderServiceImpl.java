/**
 *
 */
package com.dailydealsbox.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dailydealsbox.database.model.Order;
import com.dailydealsbox.database.model.OrderAddress;
import com.dailydealsbox.database.model.base.BaseEnum.ORDER_STATUS;
import com.dailydealsbox.database.repository.OrderRepository;
import com.dailydealsbox.database.service.OrderService;

/**
 * @author x_ye
 */
@Service
public class OrderServiceImpl implements OrderService {
  @Autowired
  private OrderRepository repo;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.OrderService#get(int)
   */
  @Override
  public Order get(int id) {
    return this.repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.OrderService#list(boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Order> list(boolean deleted, Pageable pageable) {
    return this.repo.findByDeleted(deleted, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.OrderService#insert(com.dailydealsbox.database.model.Order)
   */
  @Override
  public Order insert(Order order) {
    this.fixOrder(order);
    return this.repo.save(order);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.OrderService#update(com.dailydealsbox.database.model.Order)
   */
  @Override
  public Order update(Order order) {
    this.fixOrder(order);
    return this.repo.save(order);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.OrderService#delete(int)
   */
  @Override
  public void delete(int id) {
    this.repo.delete(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.OrderService#modifiyStatus(int, com.dailydealsbox.database.model.base.BaseEnum.ORDER_STATUS)
   */
  @Override
  public Order modifiyStatus(int id, ORDER_STATUS status) {
    Order order = this.get(id);
    if (order == null) { return null; }
    order.setStatus(status);
    this.update(order);
    return order;
  }

  /**
   * fixOrder
   *
   * @param order
   * @return
   */
  private Order fixOrder(Order order) {
    for (OrderAddress o : order.getAddresses()) {
      o.setOrder(order);
    }
    return order;
  }
}