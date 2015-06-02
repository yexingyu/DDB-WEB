/**
 * 
 */
package com.dailydealsbox.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Account;
import com.dailydealsbox.database.service.AccountsService;
import com.dailydealsbox.web.base.BaseResponseData;
import com.dailydealsbox.web.base.BaseResponseData.STATUS;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

  @Autowired
  AccountsService service;

  /**
   * login
   * 
   * @param response
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public BaseResponseData login(@RequestBody Account account, HttpServletResponse response) {
    Account account_from_db = service.getByAccount(account.getAccount());
    if (account_from_db != null
        && StringUtils.equals(account_from_db.getPassword(), account.getPassword())) {
      System.out.println(account_from_db.getPassword() + " : " + account.getPassword());
      Cookie cookie = new Cookie("auth", "test");
      cookie.setMaxAge(3600 * 24 * 31);
      response.addCookie(cookie);
      return BaseResponseData.newInstance(STATUS.SUCCEED, "GO");
    } else {
      return BaseResponseData.newInstance(STATUS.SUCCEED, "FAIL");
    }
  }
}
