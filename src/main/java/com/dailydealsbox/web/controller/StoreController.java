/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Store;
import com.dailydealsbox.service.AuthorizationService;
import com.dailydealsbox.service.StoreService;
import com.dailydealsbox.web.base.BaseResponseData.STATUS;
import com.dailydealsbox.web.base.GeneralResponseData;

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
  public GeneralResponseData all() {
    List<Store> stores = storeService.getAll();
    if (stores == null || stores.isEmpty()) {
      return GeneralResponseData.newInstance(STATUS.EMPTY_RESULT, "");
    } else {
      return GeneralResponseData.newInstance(STATUS.SUCCESS, stores);
    }
  }

  /**
   * retrieve
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public GeneralResponseData retrieve(@PathVariable("id") int id) {
    Store store = storeService.get(id);
    if (store == null) {
      return GeneralResponseData.newInstance(STATUS.EMPTY_RESULT, "");
    } else {
      return GeneralResponseData.newInstance(STATUS.SUCCESS, store);
    }
  }

}
