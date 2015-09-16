/**
 *
 */
package com.dailydealsbox.web.controller;

import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;
import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.web.configuration.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.web.database.model.LocalItem;
import com.dailydealsbox.web.database.model.Member;
import com.dailydealsbox.web.database.service.LocalItemService;
import com.dailydealsbox.web.database.service.MemberService;
import com.dailydealsbox.web.database.service.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/local_item")
@Api(value = "LocalItem", description = "LocalItem Management Operation")
public class LocalItemController {

  @Autowired
  private MemberService    memberService;
  @Autowired
  private LocalItemService localItemService;
  @Autowired
  private StoreService     storeService;

  //
  //  @RequestMapping(method = RequestMethod.GET)
  //  @ApiOperation(value = "list product", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List pageable products.")
  //  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
  //      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"),
  //      @ApiImplicitParam(name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  //  public GenericResponseData list(@ApiParam(value = "filter: store ids", required = false) @RequestParam(value = "store_ids", required = false) Set<Integer> storeIds,
  //      @ApiParam(value = "filter: tags", required = false) @RequestParam(value = "tags", required = false) Set<String> tags,
  //      @ApiParam(value = "filter: countries", required = false) @RequestParam(value = "countries", required = false) Set<COUNTRY> countries,
  //      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
  //      @ApiParam(value = "filter: is disabled", required = false, defaultValue = "false") @RequestParam(value = "disabled", required = false, defaultValue = "false") boolean disabled,
  //      @ApiIgnore Pageable pageable) throws Exception {
  //
  //    Page<Product> products = null;
  //    try {
  //      products = this.productService.list(storeIds, tags, countries, null, deleted, disabled, pageable);
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //    }
  //
  //    if (products == null || products.getNumberOfElements() == 0) {
  //      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
  //    } else {
  //      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, products);
  //    }
  //  }

  /**
   * retrieve
   *
   * @param itemId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "id/{itemId}", method = RequestMethod.GET)
  @ApiOperation(value = "retrieve local item", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Retrieve local item information.")
  public GenericResponseData retrieve(@ApiParam(value = "local item id", required = true) @PathVariable("itemId") int itemId) throws Exception {
    LocalItem item = this.localItemService.get(itemId);
    if (item == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, item);
    }
  }

  /**
   * update
   *
   * @param itemId
   * @param item
   * @return
   */
  @RequestMapping(value = "id/{itemId}", method = { RequestMethod.PUT })
  @ApiOperation(value = "update local item", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Update local item information.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN, MEMBER_ROLE.MEMBER })
  public GenericResponseData update(@ApiParam(value = "local item id", required = true) @PathVariable("itemId") int itemId,
      @ApiParam(value = "local item object", required = true) @RequestBody LocalItem item, HttpServletRequest request) throws Exception {
    if (item.validate()) {
      AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
      Member me = this.memberService.get(token.getMemberId());

      LocalItem itemFromDb = this.localItemService.get(itemId);
      if (itemFromDb == null) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "002");
      } else {
        try {
          if (me.getRole() == MEMBER_ROLE.ADMIN) {
            item.setId(itemId);
            itemFromDb = this.localItemService.update(item);
            return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, itemFromDb);
          } else if (itemFromDb.getAddBy() == me.getId()) {
            item.setId(itemId);
            item.setAddBy(me.getId());
            itemFromDb = this.localItemService.update(item);
            return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, itemFromDb);
          } else {
            return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "003");
          }
        } catch (Exception e) {
          e.printStackTrace();
          return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "004");
        }
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
    }
  }

  /**
   * insert
   *
   * @param item
   * @param request
   * @return
   */
  @RequestMapping(method = { RequestMethod.POST })
  @ApiOperation(value = "insert local item", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Insert a new local item.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN, MEMBER_ROLE.MEMBER })
  public GenericResponseData insert(@ApiParam(value = "local item object", required = true) @RequestBody LocalItem item, HttpServletRequest request) {
    if (item.validate()) {
      AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
      Member me = this.memberService.get(token.getMemberId());
      if (me.getRole() == MEMBER_ROLE.ADMIN) {
        Member contributor = me;
        Set<Member> contributors = this.memberService.listByRole(MEMBER_ROLE.CONTRIBUTOR);
        if (contributors != null) {
          Member[] arrContributors = contributors.toArray(new Member[contributors.size()]);
          int max = contributors.size() - 1;
          int min = 0;
          int randomNum = new Random().nextInt((max - min) + 1) + min;
          contributor = arrContributors[randomNum];
        }
        item.setAddBy(contributor.getId());
        item.setAddByName(StringUtils.substringBefore(contributor.getAccount(), "@"));
      } else {
        item.setAddBy(me.getId());
        item.setAddByName(me.getFirstName());
      }
      LocalItem itemFromDb = this.localItemService.insert(item);
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, itemFromDb);
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
    }
  }
}
