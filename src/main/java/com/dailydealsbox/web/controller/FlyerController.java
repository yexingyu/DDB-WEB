/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author x_ye
 */
@Controller
@RequestMapping("/flyer")
public class FlyerController {

  @RequestMapping(method = RequestMethod.GET)
  public String index(
      @RequestParam(value = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);

    return "flyer/index";
  }

  @RequestMapping(value = "test", method = RequestMethod.GET)
  public String test(
      @RequestParam(value = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    System.out.println("name: " + name);
    return "flyer/test";
  }

  @RequestMapping(value = "json", method = RequestMethod.GET)
  @ResponseBody
  public List<String> testJson(Model model) {
    List<String> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      list.add("i: " + i);
    }

    return list;
  }
}
