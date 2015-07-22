/**
 *
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.GenericResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/member")
@Api(value = "Member", description = "Member Management Operation.")
public class MembersController {

  @Autowired
  MemberService        service;
  @Autowired
  AuthorizationService authorizationService;

  /**
   * list
   *
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "List Members",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "List pageable members.")
  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"), @ApiImplicitParam(
        name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  public GenericResponseData list(
      @ApiIgnore @CookieValue(value = "token", required = false) String tokenString, @ApiParam(value = "filter: is deleted",
        required = false,
        defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
      @ApiIgnore Pageable pageable) {
    AuthorizationToken token = this.authorizationService.verify(tokenString);
    if (token == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == MEMBER_ROLE.ADMIN) {
      Page<Member> members = this.service.list(deleted, pageable);
      if (members == null || members.getNumberOfElements() == 0) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, members);
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "");
    }
  }

}
