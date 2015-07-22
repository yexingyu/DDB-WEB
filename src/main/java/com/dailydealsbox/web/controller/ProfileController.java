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
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.GenericResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/profile")
@Api(value = "Profile", description = "Profile Management Operation")
public class ProfileController {

  @Autowired
  MemberService memberService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * profile
   *
   * @param tokenCookie
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Retrieve member profile",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Retrieve member profile.")
  public GenericResponseData profile(@ApiIgnore @CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = this.authorizationService.verify(tokenString);
    if (token == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else {
      Member member = this.memberService.get(token.getMemberId());
      if (member == null) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, member);
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
  @ApiOperation(value = "Edit profile", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Edit profile.")
  public GenericResponseData edit(@ApiIgnore @CookieValue(value = "token", required = false) String tokenString,
      @ApiParam(value = "member object", required = true) @RequestBody Member member) {
    AuthorizationToken token = this.authorizationService.verify(tokenString);
    if (token == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else {
      if (member.validate()) {
        Member memberFromDb = this.memberService.update(member);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, memberFromDb);
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      }
    }
  }

}
