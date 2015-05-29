/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import com.dailydealsbox.database.model.Account;

/**
 * @author x_ye
 */
public interface AccountsDao {

  /**
   * save
   * 
   * @param test
   */
  public void save(Account account);

  /**
   * findAll
   * 
   * @return
   */
  public List<Account> findAll();

  /**
   * get
   * 
   * @param id
   * @return
   */
  public Account get(int id);

  /**
   * delete
   * 
   * @param account
   */
  public void delete(Account account);
}
