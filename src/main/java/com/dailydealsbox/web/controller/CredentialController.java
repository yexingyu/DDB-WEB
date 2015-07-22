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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/credential")
@Api(value = "Credential", description = "Credential Api.")
public class CredentialController {

  @Resource
  MemberService memberService;

  @Resource
  AuthorizationService authorizationService;

  /**
   * checkCookie
   *
   * @param tokenString
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Check credential",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Check credential.")
  public GenericResponseData checkCookie(@ApiIgnore @CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = this.authorizationService.verify(tokenString);
    if (token == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, token);
    }
  }

  /**
   * login
   *
   * @param response
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Login", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Login.")
  public GenericResponseData login(
      @ApiParam(value = "member object", required = true) @RequestBody Member member, @ApiParam(value = "remember me",
        required = false,
        defaultValue = "false") @RequestParam(value = "rememberMe", required = false, defaultValue = "false") boolean rememberMe,
      HttpServletResponse response) {
    Member member_from_db = this.memberService.getByAccount(member.getAccount());
    if (member_from_db != null && StringUtils.equals(member_from_db.getPassword(), member.getPassword())) {
      int expiry = -1;
      if (rememberMe) {
        expiry = (int) (BaseAuthorization.EXPIRY / 1000);
      }
      Cookie cookie = this.authorizationService.buildCookie(AuthorizationToken.newInstance(member_from_db.getId(), member_from_db.getAccount(),
          member_from_db.getPassword(), this.authorizationService.buildExpiredStamp(), member_from_db.getRole()), expiry);
      if (cookie == null) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.FAIL, "FAIL_001");
      } else {
        response.addCookie(cookie);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, member_from_db);
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.FAIL, "FAIL_002");
    }
  }
}
