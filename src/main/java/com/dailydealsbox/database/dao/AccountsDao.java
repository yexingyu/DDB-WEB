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
   * delete
   * 
   * @param id
   */
  public void delete(int id);

  /**
   * save
   * 
   * @param test
   */
  public void save(Account test);

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
}
