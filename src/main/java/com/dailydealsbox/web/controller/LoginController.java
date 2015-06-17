/**
 * 
 */
package com.dailydealsbox.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/api/login")
public class LoginController {

  @Resource
  MemberService        memberService;

  @Resource
  AuthorizationService authorizationService;

  /**
   * checkCookie
   * 
   * @param tokenString
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public GeneralResponseData checkCookie(
      @CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = authorizationService.verify(tokenString);
    if (token == null) {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, token);
    }
  }

  /**
   * login
   * 
   * @param response
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public GeneralResponseData login(@RequestBody Member member, HttpServletResponse response) {
    Member member_from_db = memberService.getByAccount(member.getAccount());
    if (member_from_db != null
        && StringUtils.equals(member_from_db.getPassword(), member.getPassword())) {
      Cookie cookie = authorizationService.buildCookie(AuthorizationToken.newInstance(
          member_from_db.getId(), member_from_db.getAccount(), member_from_db.getPassword(),
          authorizationService.buildExpiredStamp(), member_from_db.getRole()));
      if (cookie == null) {
        return GeneralResponseData.newInstance(RESPONSE_STATUS.FAIL, "FAIL_001");
      } else {
        response.addCookie(cookie);
        return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, member_from_db);
      }
    } else {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.FAIL, "FAIL_002");
    }
  }

}
