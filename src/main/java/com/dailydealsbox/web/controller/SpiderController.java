/**
 *
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.SpiderService;
import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/spider")
@Api(value = "Html Spider", description = "Parse html page.")
public class SpiderController {
  @Autowired
  private SpiderService spiderService;

  /**
   * bestbuy
   * 
   * @param url
   * @return
   */
  @RequestMapping(value = "bestbuy", method = RequestMethod.GET)
  @ApiOperation(value = "Retrieve product infomation",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Retrieve product infomation from bestbuy.ca.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData bestbuy(@ApiParam(value = "url", required = true) @RequestParam(value = "url", required = true) String url) {
    Product product = this.spiderService.getProductFromBestbuy(url);
    if (product == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, product);
    }
  }
}