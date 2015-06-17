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
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.service.AuthorizationService;
import com.dailydealsbox.service.MemberService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseResponseData;
import com.dailydealsbox.web.base.GeneralResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/member")
public class MembersController {

  @Autowired
  MemberService        service;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * all
   * 
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public GeneralResponseData all(@CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = authorizationService.verify(tokenString);
    if (token == null) {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == MEMBER_ROLE.ADMIN) {
      List<Member> members = service.getAll();
      //System.out.println(members);
      if (members == null || members.size() == 0) {
        return GeneralResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
      } else {
        return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, members);
      }
    } else {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "");
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
  public BaseResponseData add(@RequestBody Member member) {
    //service.save(account);
    //return service.get(account.getId());
    return null;
  }

}
