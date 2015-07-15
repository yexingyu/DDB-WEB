/**
 *
 */
package com.dailydealsbox.web.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.base.BaseEnum;
import com.dailydealsbox.web.base.GeneralResponseData;

/**
 * @author x_ye
 */
@RestController
public class GlobalErrorController implements ErrorController {
  private static final String PATH = "/error";

  @RequestMapping(value = PATH)
  public GeneralResponseData error() {
    return GeneralResponseData.newInstance(BaseEnum.RESPONSE_STATUS.ERROR, "404");
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
