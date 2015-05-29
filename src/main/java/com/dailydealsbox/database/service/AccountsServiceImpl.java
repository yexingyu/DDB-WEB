/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.dao.AccountsDao;
import com.dailydealsbox.database.model.Account;

/**
 * @author x_ye
 */
@Service("timo_test_service")
@Transactional
public class AccountsServiceImpl implements AccountsService {

  @Autowired
  private AccountsDao dao;

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.database.service.AccountsService#save(com.dailydealsbox.database.model.Account
   * )
   */
  @Override
  public void save(Account account) {
    dao.save(account);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.AccountsService#findAll()
   */
  @Override
  public List<Account> findAll() {
    return dao.findAll();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.TestService#get(int)
   */
  @Override
  public Account get(int id) {
    return dao.get(id);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.database.service.AccountsService#delete(com.dailydealsbox.database.model.
   * Account)
   */
  @Override
  public void delete(Account account) {
    dao.delete(account);
  }

}
