/**
 * 
 */
package com.dailydealsbox.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.base.BaseEnum;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.web.base.GeneralResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/constant")
public class ConstantController {

  @RequestMapping(method = RequestMethod.GET)
  public GeneralResponseData retrieve() {
    return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, BaseEnum.enums());
  }

}