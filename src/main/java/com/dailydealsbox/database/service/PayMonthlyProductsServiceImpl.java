/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.dao.PayMonthlyProductsDao;
import com.dailydealsbox.database.model.PayMonthlyProduct;

/**
 * @author x_ye
 */
@Service("pay_monthly_pruducts_service")
@Transactional
public class PayMonthlyProductsServiceImpl implements PayMonthlyProductsService {

  @Autowired
  private PayMonthlyProductsDao dao;

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.database.service.PayMonthlyProductsService#save(com.dailydealsbox.database.model
   * .PayMonthlyProduct
   * )
   */
  @Override
  public void save(PayMonthlyProduct paymonthlyproduct) {
    dao.save(paymonthlyproduct);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.PayMonthlyProductsService#findAll()
   */
  @Override
  public List<PayMonthlyProduct> findAll() {
    return dao.findAll();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.TestService#get(int)
   */
  @Override
  public PayMonthlyProduct get(int id) {
    return dao.get(id);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.database.service.PayMonthlyProductsService#delete(com.dailydealsbox.database.
   * model.
   * PayMonthlyProduct)
   */
  @Override
  public void delete(PayMonthlyProduct paymonthlyproduct) {
    dao.delete(paymonthlyproduct);
  }

}
