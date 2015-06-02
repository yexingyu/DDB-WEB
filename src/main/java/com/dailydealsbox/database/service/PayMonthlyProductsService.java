/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import com.dailydealsbox.database.model.PayMonthlyProduct;

/**
 * @author y_dai
 */
public interface PayMonthlyProductsService {

  /**
   * save
   * 
   * @param test
   */
  public void save(PayMonthlyProduct paymonthlyproduct);

  /**
   * get
   * 
   * @param id
   * @return
   */
  public PayMonthlyProduct get(int id);

  /**
   * findAll
   * 
   * @return
   */
  public List<PayMonthlyProduct> findAll();

  /**
   * delete
   * 
   * @param paymonthlyproduct
   */
  public void delete(PayMonthlyProduct paymonthlyproduct);
}
