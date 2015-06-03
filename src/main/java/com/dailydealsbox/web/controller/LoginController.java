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
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseResponseData;
import com.dailydealsbox.web.base.BaseResponseData.STATUS;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

  @Autowired
  AccountsService      accountService;

  @Autowired
  AuthorizationService authService;

  /**
   * login
   * 
   * @param response
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public BaseResponseData login(@RequestBody Account account, HttpServletResponse response) {
    Account account_from_db = accountService.getByAccount(account.getAccount());
    if (account_from_db != null
        && StringUtils.equals(account_from_db.getPassword(), account.getPassword())) {
      System.out.println(account_from_db.getPassword() + " : " + account.getPassword());

      Cookie cookie = authService
          .buildCookie(AuthorizationToken.newInstance(account_from_db.getId(),
              account_from_db.getAccount(), authService.buildExpiredStamp(), 0));
      if (cookie == null) {
        return BaseResponseData.newInstance(STATUS.FAIL, "FAIL_001");
      } else {
        response.addCookie(cookie);
        return BaseResponseData.newInstance(STATUS.SUCCESS, "SUCCESS");
      }
    } else {
      return BaseResponseData.newInstance(STATUS.FAIL, "FAIL_002");
    }
  }
}
