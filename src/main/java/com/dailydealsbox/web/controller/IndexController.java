/**
 * 
 */
package com.dailydealsbox.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author x_ye
 */
@RestController
public class IndexController {

  @RequestMapping(method = RequestMethod.GET)
  public String index() {
    return "DailyDealsBox Welcome Page.";
  }

}
