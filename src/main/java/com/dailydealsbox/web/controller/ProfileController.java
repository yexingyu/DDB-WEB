/**
 * 
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseResponseData.STATUS;
import com.dailydealsbox.web.base.ResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  @Autowired
  MemberService        memberService;

  @Autowired
  AuthorizationService authService;

  /**
   * profile
   * 
   * @param tokenCookie
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public ResponseData profile(@CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = authService.verify(tokenString);
    if (token == null) {
      return ResponseData.newInstance(STATUS.NEED_LOGIN, "");
    } else {
      Member member = memberService.get(token.getMemberId());
      //System.out.println(member);
      if (member == null) {
        return ResponseData.newInstance(STATUS.FAIL, "");
      } else {
        return ResponseData.newInstance(STATUS.SUCCESS, member);
      }
    }
  }
}
