/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Account;
import com.dailydealsbox.database.service.AccountsService;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

  @Autowired
  AccountsService service;

  /**
   * findAll
   * 
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public List<Account> findAll() {
    return service.findAll();
  }

  /**
   * get
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public Account get(@PathVariable("id") int id) {
    return service.get(id);
  }

  /**
   * addAccount
   * 
   * @param test
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public Account addAccount(@RequestBody Account account) {
    service.save(account);
    return service.get(account.getId());
  }

}
