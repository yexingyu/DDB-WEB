/**
 *
 */
package com.dailydealsbox.web.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.configuration.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.configuration.BaseEnum.STORE_TYPE;
import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.Store;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.database.service.StoreService;
import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;
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
@RequestMapping("/api/store")
@Api(value = "Store", description = "Store Management Operation")
public class StoreController {

  @Autowired
  StoreService         storeService;
  @Autowired
  MemberService        memberService;
  @Autowired
  AuthorizationService authorizationService;

  /**
   * list
   *
   * @param storeIds
   * @param countries
   * @param deleted
   * @param pageable
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "list stores", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List pageable stores.")
  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  public GenericResponseData list(@ApiParam(value = "filter: store ids", required = false) @RequestParam(value = "store_ids", required = false) Set<Integer> storeIds,
      @ApiParam(value = "filter: countries", required = false) @RequestParam(value = "countries", required = false) Set<COUNTRY> countries,
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
      @ApiIgnore Pageable pageable) {
    Page<Store> stores = this.storeService.list(storeIds, countries, deleted, pageable);
    if (stores == null || stores.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, stores);
    }
  }

  /**
   * listAll
   *
   * @param storeIds
   * @param countries
   * @param deleted
   * @return
   */
  @RequestMapping(value = "all", method = RequestMethod.GET)
  @ApiOperation(value = "list all stores", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List all stores.")
  public GenericResponseData listAll(@ApiParam(value = "filter: store ids", required = false) @RequestParam(value = "store_ids", required = false) Set<Integer> storeIds,
      @ApiParam(value = "filter: countries", required = false) @RequestParam(value = "countries", required = false) Set<COUNTRY> countries,
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted) {
    Set<Store> stores = this.storeService.listAll(storeIds, countries, deleted);
    if (stores == null || stores.isEmpty()) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, stores);
    }
  }

  /**
   * retrieve
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "id/{storeId}", method = RequestMethod.GET)
  @ApiOperation(value = "retrieve store details", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Retrieve store details.")
  public GenericResponseData retrieve(@ApiParam(value = "store id", required = true) @PathVariable("storeId") int id) {
    Store store = this.storeService.get(id);
    if (store == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, store);
    }
  }

  /**
   * insert
   *
   * @param tokenString
   * @param store
   * @return
   */
  @RequestMapping(method = { RequestMethod.POST })
  @ApiOperation(value = "insert store", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Insert a new store.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN, MEMBER_ROLE.MEMBER })
  public GenericResponseData insert(@ApiParam(value = "store object", required = true) @RequestBody Store store, HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member me = this.memberService.get(token.getMemberId());
    if (store.validate()) {
      if (store.getType() == null) { return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "002"); }
      if (store.getType() == STORE_TYPE.ONLINE && me.getRole() != MEMBER_ROLE.ADMIN) { return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "003"); }

      // set verified column
      if (store.getType() == STORE_TYPE.ONLINE || me.getRole() == MEMBER_ROLE.ADMIN) {
        store.setVerified(true);
      } else {
        store.setVerified(false);
      }

      // set add by memberId
      store.setAddBy(me.getId());

      // inserting
      Store storeFromDb = this.storeService.insert(store);
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, storeFromDb);
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
    }

  }

  /**
   * update
   *
   * @param storeId
   * @param tokenString
   * @param store
   * @return
   */
  @RequestMapping(value = "id/{storeId}", method = { RequestMethod.PUT })
  @ApiOperation(value = "update store", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Update store details.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN, MEMBER_ROLE.MEMBER })
  public GenericResponseData update(@ApiParam(value = "store id", required = true) @PathVariable("storeId") int storeId, @ApiParam(value = "store object", required = true) @RequestBody Store store,
      HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member me = this.memberService.get(token.getMemberId());
    if (store.validate()) {
      Store storeFromDb = this.storeService.get(storeId);
      if (storeFromDb != null) {
        store.setId(storeId);
        store.setAddBy(storeFromDb.getAddBy());

        // permission
        if (store.getType() == null) { return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "003"); }
        if (store.getType() == STORE_TYPE.ONLINE && me.getRole() != MEMBER_ROLE.ADMIN) { return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "004"); }
        if (store.getType() == STORE_TYPE.LOCAL && me.getRole() != MEMBER_ROLE.ADMIN
            && me.getId() != store.getAddBy()) { return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "005"); }

        // set verified=true, if store_type == online
        if (store.getType() == STORE_TYPE.ONLINE) {
          store.setVerified(true);
        } else if (store.getType() == STORE_TYPE.LOCAL && me.getRole() != MEMBER_ROLE.ADMIN) {
          store.setVerified(false);
        }

        // saving
        storeFromDb = this.storeService.update(store);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, storeFromDb);
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "002");
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
    }
  }

  /**
   * follow
   *
   * @param storeId
   * @param request
   * @return
   */
  @RequestMapping(value = "id/{storeId}/follow", method = { RequestMethod.POST })
  @ApiOperation(value = "follow store", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Follow store details.")
  @DDBAuthorization
  public GenericResponseData follow(@ApiParam(value = "store id", required = true) @PathVariable("storeId") int storeId, HttpServletRequest request) {
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
      Store store = this.storeService.get(storeId);
      if (store == null) { return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001"); }

      // update following
      me.getStores().add(store);
      me = this.memberService.update(me);

      // increase count_followings
      this.storeService.increaseCountFollowings(storeId);
    }

    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, me.getStores());
  }

  /**
   * unfollow
   *
   * @param storeId
   * @param request
   * @return
   */
  @RequestMapping(value = "id/{storeId}/follow", method = { RequestMethod.DELETE })
  @ApiOperation(value = "unfollow store", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Unfollow store details.")
  @DDBAuthorization
  public GenericResponseData unfollow(@ApiParam(value = "store id", required = true) @PathVariable("storeId") int storeId, HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member me = this.memberService.get(token.getMemberId());
    if (me.getStores() == null || me.getStores().isEmpty()) { return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001"); }

    boolean following = false;
    Iterator<Store> it = me.getStores().iterator();
    while (it.hasNext()) {
      Store store = it.next();
      if (store.getId() == storeId) {
        it.remove();
        following = true;
      }
    }

    // if following
    if (following) {
      // update following
      me = this.memberService.update(me);

      // decrease count_followings
      this.storeService.decreaseCountFollowings(storeId);
    }

    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, me.getStores());
  }
}
