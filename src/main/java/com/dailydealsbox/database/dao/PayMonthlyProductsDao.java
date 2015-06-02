/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import com.dailydealsbox.database.model.PayMonthlyProduct;

/**
 * @author x_ye
 */
public interface PayMonthlyProductsDao {

  /**
   * save
   * 
   * @param test
   */
  public void save(PayMonthlyProduct paymonthlyproduct);

  /**
   * findAll
   * 
   * @return
   */
  public List<PayMonthlyProduct> findAll();

  /**
   * get
   * 
   * @param id
   * @return
   */
  public PayMonthlyProduct get(int id);

  /**
   * delete
   * 
   * @param paymonthlyproduct
   */
  public void delete(PayMonthlyProduct paymonthlyproduct);
}

