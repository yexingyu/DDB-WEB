/**
 *
 */
package com.dailydealsbox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dailydealsbox.database.model.base.BaseEnum;
import com.dailydealsbox.web.base.GeneralResponseData;

/**
 * @author x_ye
 */
@ControllerAdvice
@ResponseBody
class GlobalExceptionHandler {

  /**
   * defaultErrorHandler
   *
   * @param req
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public GeneralResponseData defaultErrorHandler(HttpServletRequest req, Exception e) {
    return GeneralResponseData.newInstance(BaseEnum.RESPONSE_STATUS.ERROR, e.getMessage());
  }
}
