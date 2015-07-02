/**
 * 
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.service.AuthorizationService;
import com.dailydealsbox.service.MemberService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.GeneralResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  @Autowired
  MemberService        memberService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * profile
   * 
   * @param tokenCookie
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public GeneralResponseData profile(@CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = authorizationService.verify(tokenString);
    if (token == null) {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else {
      Member member = memberService.get(token.getMemberId());
      if (member == null) {
        return GeneralResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      } else {
        return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, member);
      }
    }
  }

  /**
   * edit
   * 
   * @param tokenString
   * @return
   */
  @RequestMapping(method = RequestMethod.PUT)
  public GeneralResponseData edit(@CookieValue(value = "token", required = false) String tokenString, @RequestBody Member member) {
    AuthorizationToken token = authorizationService.verify(tokenString);
    if (token == null) {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else {
      if (member.validate()) {
        Member memberFromDb = memberService.update(member);
        return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, memberFromDb);
      } else {
        return GeneralResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      }
    }
  }

}
