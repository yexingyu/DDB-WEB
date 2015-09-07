/**
 *
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;
import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.web.configuration.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.web.database.model.Product;
import com.dailydealsbox.web.database.service.SpiderService;

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
   * spider
   *
   * @param url
   * @return
   * @throws Exception
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Retrieve product infomation", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Retrieve product infomation.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData spider(@ApiParam(value = "url", required = true) @RequestParam(value = "url", required = true) String url) throws Exception {
    try {
      Product product = this.spiderService.getProduct(url);
      if (product == null) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, product);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, e.getMessage());
    }
  }
}
