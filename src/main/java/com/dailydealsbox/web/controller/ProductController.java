/**
 * 
 */
package com.dailydealsbox.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.service.AuthorizationService;
import com.dailydealsbox.service.ProductService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.GeneralResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  ProductService       productService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * list
   * 
   * @param pageable
   * @return
   * @throws Exception
   */
  @RequestMapping(method = RequestMethod.GET)
  public GeneralResponseData list(Pageable pageable) throws Exception {
    List<Product> products = productService.getAll(pageable);
    if (products == null || products.isEmpty()) {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, products);
    }
  }

  /**
   * retrieve
   * 
   * @param id
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public GeneralResponseData retrieve(@PathVariable("id") int id) throws Exception {
    Product product = productService.get(id);
    if (product == null) {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      System.out.println("product=" + product);
      return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, product);
    }
  }

  /**
   * update
   * 
   * @param tokenString
   * @param product
   * @return
   */
  @RequestMapping(method = { RequestMethod.PUT })
  public GeneralResponseData update(@CookieValue(value = "token", required = false) String tokenString, @RequestBody Product product) {
    AuthorizationToken token = authorizationService.verify(tokenString);
    if (token == null) {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == MEMBER_ROLE.ADMIN) {
      if (product.validate()) {
        Product productFromDb = productService.update(product);
        return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, productFromDb);
      } else {
        return GeneralResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      }
    } else {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "");
    }
  }

  /**
   * insert
   * 
   * @param tokenString
   * @param product
   * @return
   */
  @RequestMapping(method = { RequestMethod.POST })
  public GeneralResponseData insert(@CookieValue(value = "token", required = false) String tokenString, @RequestBody Product product) {
    AuthorizationToken token = authorizationService.verify(tokenString);
    if (token == null) {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == MEMBER_ROLE.ADMIN) {
      System.out.println(product);
      if (product.validate()) {
        Product productFromDb = productService.insert(product);
        return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, productFromDb);
      } else {
        return GeneralResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      }
    } else {
      return GeneralResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "");
    }
  }
}
