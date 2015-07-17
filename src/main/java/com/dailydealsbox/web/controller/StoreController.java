/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Store;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.StoreService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.GenericResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/store")
public class StoreController {

  @Autowired
  StoreService         storeService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * all
   * 
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public GenericResponseData all() {
    List<Store> stores = storeService.getAll();
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
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public GenericResponseData retrieve(@PathVariable("id") int id) {
    Store store = storeService.get(id);
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
   * @param product
   * @return
   */
  @RequestMapping(method = { RequestMethod.POST })
  public GenericResponseData insert(
      @CookieValue(value = "token", required = false) String tokenString, @RequestBody Store store) {
    AuthorizationToken token = authorizationService.verify(tokenString);
    if (token == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == MEMBER_ROLE.ADMIN) {
      System.out.println(store);
      if (store.validate()) {
        Store storeFromDb = storeService.insert(store);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, storeFromDb);
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "");
    }
  }
}
