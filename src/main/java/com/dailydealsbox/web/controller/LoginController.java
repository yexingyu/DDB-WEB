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
@RequestMapping("/api/login")
public class LoginController {

  @Autowired
  MemberService        memberService;

  @Autowired
  AuthorizationService authService;

  /**
   * login
   * 
   * @param response
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseData login(@RequestBody Member member, HttpServletResponse response) {
    Member member_from_db = memberService.getByAccount(member.getAccount());
    if (member_from_db != null
        && StringUtils.equals(member_from_db.getPassword(), member.getPassword())) {
      System.out.println(member_from_db.getPassword() + " : " + member.getPassword());

      Cookie cookie = authService.buildCookie(AuthorizationToken.newInstance(
          member_from_db.getId(), member_from_db.getAccount(), authService.buildExpiredStamp(),
          member_from_db.getRole()));
      if (cookie == null) {
        return ResponseData.newInstance(STATUS.FAIL, "FAIL_001");
      } else {
        response.addCookie(cookie);
        return ResponseData.newInstance(STATUS.SUCCESS, member_from_db);
      }
    } else {
      return ResponseData.newInstance(STATUS.FAIL, "FAIL_002");
    }
  }
}
