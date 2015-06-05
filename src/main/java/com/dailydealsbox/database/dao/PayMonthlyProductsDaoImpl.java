/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.dailydealsbox.database.dao.base.BaseEntityDao;
import com.dailydealsbox.database.model.PayMonthlyProduct;

/**
 * @author x_ye
 */
@Repository
public class PayMonthlyProductsDaoImpl extends BaseEntityDao<PayMonthlyProduct> implements
    PayMonthlyProductsDao {

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TimoTestDao#save(com.dailydealsbox.database.model.TimoTest)
   */
  @Override
  public void save(PayMonthlyProduct test) {
    this.persist(test);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TimoTestDao#findAll()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<PayMonthlyProduct> findAll() {
    Criteria criteria = getSession().createCriteria(PayMonthlyProduct.class);
    return criteria.list();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TestDao#get(int)
   */
  @Override
  public PayMonthlyProduct get(int id) {
    return this.get(PayMonthlyProduct.class, id);
  }

}
