/**
 *
 */
package com.dailydealsbox.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashSet;
import java.util.Iterator;

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
  @RequestMapping(value = "id/{tagId}/followTag", method = { RequestMethod.POST })
  @ApiOperation(value = "follow tag",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Follow tag details.")
  @DDBAuthorization
  public GenericResponseData follow(
      @ApiParam(value = "tag id", required = true) @PathVariable("tagId") int tagId,
      HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member me = this.memberService.get(token.getMemberId());
    if (me.getTags() == null) {
      me.setTags(new HashSet<ProductTag>());
    }

    // tag is following
    boolean following = false;
    for (ProductTag t : me.getTags()) {
      if (t.getId() == tagId) {
        following = true;
        break;
      }
    }

    // if not following
    if (!following) {
      ProductTag tag = this.tagService.get(tagId);
      if (tag == null) { return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001"); }

      // update following
      me.getTags().add(tag);
      me = this.memberService.update(me);

    }

    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, me.getTags());
  }

  /**
   * unfollow
   *
   * @param tagId
   * @param request
   * @return
   */
  @RequestMapping(value = "id/{tagId}/follow", method = { RequestMethod.DELETE })
  @ApiOperation(value = "unfollow tag",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Unfollow tag details.")
  @DDBAuthorization
  public GenericResponseData unfollow(
      @ApiParam(value = "tag id", required = true) @PathVariable("tagId") int tagId,
      HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member me = this.memberService.get(token.getMemberId());
    if (me.getTags() == null || me.getTags().isEmpty()) { return GenericResponseData.newInstance(
        RESPONSE_STATUS.ERROR, "001"); }

    boolean following = false;
    Iterator<ProductTag> it = me.getTags().iterator();
    while (it.hasNext()) {
      ProductTag tag = it.next();
      if (tag.getId() == tagId) {
        it.remove();
        following = true;
      }
    }

    // if following
    if (following) {
      // update following
      me = this.memberService.update(me);

      // decrease count_followings
      //this.tagService.decreaseCountFollowings(tagId);
    }

    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, me.getTags());
  }

}
