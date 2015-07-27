/**
 *
 */
package com.dailydealsbox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.web.annotation.DDBAuthorization;
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
  @ApiOperation(value = "Retrieve member profile", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Retrieve member profile.")
  @DDBAuthorization
  public GenericResponseData profile(@ApiIgnore @CookieValue(value = "token", required = false) String tokenString, HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member member = this.memberService.get(token.getMemberId());
    if (member == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, member);
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
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData edit(@ApiIgnore @CookieValue(value = "token", required = false) String tokenString, @ApiParam(value = "member object", required = true) @RequestBody Member member,
      HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    if (token.getMemberId() == member.getId()) {
      if (member.validate()) {
        Member memberFromDb = this.memberService.update(member);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, memberFromDb);
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "002");
    }
  }

}
