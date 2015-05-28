/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.dailydealsbox.database.model.Account;

/**
 * @author x_ye
 */
@Repository("timo_test_dao")
public class AccountsDaoImpl extends AbstractDao<Account> implements AccountsDao {

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TimoTestDao#save(com.dailydealsbox.database.model.TimoTest)
   */
  @Override
  public void save(Account test) {
    this.persist(test);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TimoTestDao#findAll()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Account> findAll() {
    Criteria criteria = getSession().createCriteria(Account.class);
    return criteria.list();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TestDao#get(int)
   */
  @Override
  public Account get(int id) {
    return this.get(Account.class, id);
  }
}
