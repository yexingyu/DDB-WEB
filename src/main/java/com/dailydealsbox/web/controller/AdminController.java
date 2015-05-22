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
@RequestMapping(value = "/admin")
public class AdminController {

  @RequestMapping(method = RequestMethod.GET)
  public String index() {
    return "DailyDealsBox Admin Page.";
  }

}
