/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dailydealsbox.database.dao.base.EntityBaseDao;
import com.dailydealsbox.database.model.Account;

/**
 * @author x_ye
 */
@Repository("accounts_dao")
public class AccountsDaoImpl extends EntityBaseDao<Account> implements AccountsDao {

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

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.AccountsDao#getByAccount(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Account getByAccount(String account) {
    List<Account> as = getSession().createCriteria(Account.class)
        .add(Restrictions.eq("account", account)).list();
    if (as.size() >= 1) {
      return as.get(0);
    } else {
      return null;
    }
  }
}
