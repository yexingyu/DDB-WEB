/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.PayMonthlyProduct;
import com.dailydealsbox.database.service.PayMonthlyProductsService;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/paymonthlyproducts")
public class PayMonthlyProductsController {

  @Autowired
  PayMonthlyProductsService service;

  /**
   * findAll
   * 
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  public List<PayMonthlyProduct> findAll() {
    return service.findAll();
  }

  /**
   * get
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public PayMonthlyProduct get(@PathVariable("id") int id) {
    return service.get(id);
  }

  /**
   * addPayMonthlyProduct
   * 
   * @param test
   * @return
   */
  @RequestMapping(method = RequestMethod.POST)
  public PayMonthlyProduct addPayMonthlyProduct(@RequestBody PayMonthlyProduct test) {
    service.save(test);
    return service.get(test.getId());
  }
}

