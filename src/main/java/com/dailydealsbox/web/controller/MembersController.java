/**
 *
 */
package com.dailydealsbox.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;
import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.web.configuration.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.web.database.model.Member;
import com.dailydealsbox.web.service.AuthorizationService;
import com.dailydealsbox.web.service.MemberService;

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
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page",
        value = "page number",
        required = false,
        defaultValue = "0",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "size",
        value = "page size",
        required = false,
        defaultValue = "20",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "sort",
        value = "sorting. (eg. &sort=createdAt,desc)",
        required = false,
        defaultValue = "",
        dataType = "String",
        paramType = "query") })
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData list(@ApiParam(value = "filter: is deleted",
    required = false,
    defaultValue = "false") @RequestParam(value = "deleted",
    required = false,
    defaultValue = "false") boolean deleted, @ApiIgnore Pageable pageable) {
    Page<Member> members = this.service.list(deleted, pageable);
    if (members == null || members.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, members);
    }
  }

  /**
   * retrieve
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "id/{memberId}", method = RequestMethod.GET)
  @ApiOperation(value = "retrieve member details",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Retrieve store details.")
  public GenericResponseData retrieve(
      @ApiParam(value = "member id", required = true) @PathVariable("memberId") int id) {
    Member member = this.service.get(id);
    if (member == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, member);
    }
  }

}
