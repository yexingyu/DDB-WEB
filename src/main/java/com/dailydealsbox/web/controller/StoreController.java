/**
 *
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Store;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.StoreService;
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
@RequestMapping("/api/store")
@Api(value = "Store", description = "Store Management Operation")
public class StoreController {

  @Autowired
  StoreService storeService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * list
   *
   * @param deleted
   * @param pageable
   *
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "list stores",
      response = GenericResponseData.class,
      responseContainer = "Map",
      produces = "application/json",
      notes = "List pageable stores.")
  @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"), @ApiImplicitParam(
      name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query")})
  public GenericResponseData list (@ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted",
      required = false,
      defaultValue = "false") boolean deleted, @ApiIgnore Pageable pageable) {
    Page<Store> stores = this.storeService.list (deleted, pageable);
    if (stores == null || stores.getNumberOfElements () == 0) {
      return GenericResponseData.newInstance (RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, stores);
    }
  }

  /**
   * retrieve
   *
   * @param id
   *
   * @return
   */
  @RequestMapping(value = "id/{id}", method = RequestMethod.GET)
  @ApiOperation(value = "retrieve store details",
      response = GenericResponseData.class,
      responseContainer = "Map",
      produces = "application/json",
      notes = "Retrieve store details.")
  public GenericResponseData retrieve (@ApiParam(value = "store id", required = true) @PathVariable("id") int id) {
    Store store = this.storeService.get (id);
    if (store == null) {
      return GenericResponseData.newInstance (RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, store);
    }
  }

  /**
   * insert
   *
   * @param tokenString
   * @param store
   *
   * @return
   */
  @RequestMapping(method = {RequestMethod.POST})
  @ApiOperation(value = "insert store",
      response = GenericResponseData.class,
      responseContainer = "Map",
      produces = "application/json",
      notes = "Insert a new store.")
  public GenericResponseData insert (@ApiIgnore @CookieValue(value = "token", required = false) String tokenString,
                                     @ApiParam(value = "store object", required = true) @RequestBody Store store) {
    AuthorizationToken token = this.authorizationService.verify (tokenString);
    if (token == null) {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole () == MEMBER_ROLE.ADMIN) {
      if (store.validate ()) {
        Store storeFromDb = this.storeService.insert (store);
        return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, storeFromDb);
      } else {
        return GenericResponseData.newInstance (RESPONSE_STATUS.FAIL, "");
      }
    } else {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NO_PERMISSION, "");
    }
  }

  /**
   * update
   *
   * @param storeId
   * @param tokenString
   * @param store
   *
   * @return
   */
  @RequestMapping(value = "id/:id", method = {RequestMethod.PUT})
  @ApiOperation(value = "update store",
      response = GenericResponseData.class,
      responseContainer = "Map",
      produces = "application/json",
      notes = "Update store details.")
  public GenericResponseData update (@ApiParam(value = "store id", required = true) @PathVariable("id") int storeId,
                                     @ApiIgnore @CookieValue(value = "token", required = false) String tokenString,
                                     @ApiParam(value = "store object", required = true) @RequestBody Store store) {
    AuthorizationToken token = this.authorizationService.verify (tokenString);
    if (token == null) {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole () == MEMBER_ROLE.ADMIN) {
      if (store.validate ()) {
        Store storeFromDb = this.storeService.get (storeId);
        if (storeFromDb != null) {
          store.setId (storeId);
          storeFromDb = this.storeService.update (store);
          return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, storeFromDb);
        } else {
          return GenericResponseData.newInstance (RESPONSE_STATUS.FAIL, "");
        }
      } else {
        return GenericResponseData.newInstance (RESPONSE_STATUS.FAIL, "");
      }
    } else {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NO_PERMISSION, "");
    }
  }

}
