/**
 *
 */
package com.dailydealsbox.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;
import com.dailydealsbox.web.configuration.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.web.database.model.Member;
import com.dailydealsbox.web.database.model.ProductTag;
import com.dailydealsbox.web.database.model.Store;
import com.dailydealsbox.web.service.AuthorizationService;
import com.dailydealsbox.web.service.MemberService;
import com.dailydealsbox.web.service.ProductTagService;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/tag")
@Api(value = "Tag", description = "Tag Management Operation")
public class TagController {

  @Autowired
  ProductTagService    tagService;
  @Autowired
  MemberService        memberService;
  @Autowired
  AuthorizationService authorizationService;

  /**
   * follow
   *
   * @param storeId
   * @param request
   * @return
   */
  @RequestMapping(value = "id/{storeId}/follow", method = { RequestMethod.POST })
  @ApiOperation(value = "follow store",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Follow store details.")
  @DDBAuthorization
  public GenericResponseData follow(
      @ApiParam(value = "store id", required = true) @PathVariable("storeId") int storeId,
      HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member me = this.memberService.get(token.getMemberId());
    if (me.getStores() == null) {
      me.setStores(new HashSet<Store>());
    }

    // store is following
    boolean following = false;
    for (Store s : me.getStores()) {
      if (s.getId() == storeId) {
        following = true;
        break;
      }
    }

    // if not following
    if (!following) {
      ProductTag tag = this.tagService.get(storeId);
      if (tag == null) { return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001"); }

      // update following
      me.getTags().add(tag);
      me = this.memberService.update(me);

    }

    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, me.getStores());
  }

}
