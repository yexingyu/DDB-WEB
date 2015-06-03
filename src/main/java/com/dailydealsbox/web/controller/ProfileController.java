/**
 * 
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Account;
import com.dailydealsbox.database.service.AccountsService;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseResponseData;
import com.dailydealsbox.web.base.BaseResponseData.STATUS;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  @Autowired
  AccountsService      accountService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * profile
   * 
   * @param tokenCookie
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public BaseResponseData profile(@CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = authorizationService.verify(tokenString);
    System.out.println(tokenString);
    if (token == null) {
      return BaseResponseData.newInstance(STATUS.NEED_LOGIN, "");
    } else {
      Account account = accountService.get(token.getAccountId());
      if (account == null) {
        return BaseResponseData.newInstance(STATUS.FAIL, "");
      } else {
        return BaseResponseData.newInstance(STATUS.SUCCESS, account);
      }
    }
  }
}
