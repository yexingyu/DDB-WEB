/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author x_ye
 */
@RestController
@RequestMapping(value = "/publish")
public class PublishController {

  @RequestMapping(method = RequestMethod.GET)
  public String index() {
    return "Publish Index Page.";
  }

  @RequestMapping(value = "test", method = RequestMethod.GET)
  public Map<String, String> test() {
    Map<String, String> map = new HashMap<>();
    map.put("a", "aaa");
    map.put("b", "bbb");
    return map;
  }
}
