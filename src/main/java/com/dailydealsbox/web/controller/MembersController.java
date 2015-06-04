/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseResponseData;
import com.dailydealsbox.web.base.BaseResponseData.STATUS;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/members")
public class MembersController {

  @Autowired
  MemberService        service;

  @Autowired
  AuthorizationService authService;

  /**
   * all
   * 
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public BaseResponseData all(@CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = authService.verify(tokenString);
    if (token == null) {
      return BaseResponseData.newInstance(STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == Member.ROLE.ADMIN) {
      List<Member> members = service.all();
      if (members == null || members.size() == 0) {
        return BaseResponseData.newInstance(BaseResponseData.STATUS.EMPTY_RESULT, null);
      } else {
        return BaseResponseData.newInstance(BaseResponseData.STATUS.SUCCESS, members);
      }
    } else {
      return BaseResponseData.newInstance(BaseResponseData.STATUS.NO_PERMISSION, null);
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

  /**
   * add
   * 
   * @param test
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public BaseResponseData add(@RequestBody Member account) {
    //service.save(account);
    //return service.get(account.getId());
    return null;
  }

}
