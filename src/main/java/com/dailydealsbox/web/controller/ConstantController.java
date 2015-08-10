/**
 *
 */
package com.dailydealsbox.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.configuration.BaseEnum;
import com.dailydealsbox.configuration.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.web.base.GenericResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/constant")
@Api(value = "Constant", description = "Retrieve system constants.")
public class ConstantController {

  /**
   * retrieve
   *
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Retrieve system constants", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Retrieve system constants.")
  public GenericResponseData retrieve() {
    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, BaseEnum.enums());
  }

}
