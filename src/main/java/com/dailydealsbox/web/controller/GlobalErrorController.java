/**
 *
 */
package com.dailydealsbox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.base.BaseEnum;
import com.dailydealsbox.web.base.GenericResponseData;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author x_ye
 */
@ControllerAdvice
@RestController
@ApiIgnore
public class GlobalErrorController implements ErrorController {
  private static final String PATH = "/error";

  /**
   * error
   *
   * @return
   */
  @RequestMapping(value = PATH)
  public GenericResponseData error() {
    return GenericResponseData.newInstance(BaseEnum.RESPONSE_STATUS.ERROR, "404");
  }

  /**
   * defaultErrorHandler
   *
   * @param request
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public GenericResponseData defaultErrorHandler(HttpServletRequest request, Exception e) {
    return GenericResponseData.newInstance(BaseEnum.RESPONSE_STATUS.ERROR, e.getMessage());
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
