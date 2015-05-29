/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import com.dailydealsbox.database.model.Account;

/**
 * @author x_ye
 */
public interface AccountsService {

  /**
   * save
   * 
   * @param test
   */
  public void save(Account account);

  /**
   * get
   * 
   * @param id
   * @return
   */
  public Account get(int id);

  /**
   * findAll
   * 
   * @return
   */
  public List<Account> findAll();

  /**
   * delete
   * 
   * @param account
   */
  public void delete(Account account);
}
