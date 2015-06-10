/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.MemberEmail;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MemberEmailService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseResponseData;
import com.dailydealsbox.web.base.BaseResponseData.STATUS;
import com.dailydealsbox.web.base.ResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/members/emails")
public class MemberEmailController {

  @Autowired
  MemberEmailService   service;

  @Autowired
  AuthorizationService authService;

  /**
   * all
   * 
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  //@Transactional
  public ResponseData all(@CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = authService.verify(tokenString);
    if (token == null) {
      return ResponseData.newInstance(STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == Member.ROLE.ADMIN) {
      List<MemberEmail> emails = service.all();
      //System.out.println(members);
      if (emails == null || emails.size() == 0) {
        return ResponseData.newInstance(BaseResponseData.STATUS.EMPTY_RESULT, null);
      } else {
        return ResponseData.newInstance(BaseResponseData.STATUS.SUCCESS, emails);
      }
    } else {
      return ResponseData.newInstance(BaseResponseData.STATUS.NO_PERMISSION, null);
    }
  }

  /**
   * get
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public BaseResponseData get(@PathVariable("id") int id) {
    //return service.get(id);
    return null;
  }

}
