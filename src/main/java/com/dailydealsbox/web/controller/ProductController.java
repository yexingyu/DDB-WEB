/**
 *
 */
package com.dailydealsbox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.ProductReview;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.ProductService;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.GenericResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  ProductService productService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * like
   *
   * @param productId
   * @param fingerprint
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "{productId}/like", method = { RequestMethod.GET, RequestMethod.POST })
  public GenericResponseData like(@PathVariable("productId") int productId, @CookieValue(value = "fingerprint", required = true) String fingerprint,
      HttpServletRequest request) throws Exception {
    //System.out.println(request.getRemoteAddr());
    int rst = this.productService.like(productId, fingerprint, request.getRemoteAddr());
    if (rst == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Success");
    } else if (rst == -1) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Already liked");
    } else if (rst == -2) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Too many likes from the same ip");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "");
    }
  }

  /**
   * review
   *
   * @param productId
   * @param review
   * @param fingerprint
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "{productId}/review", method = { RequestMethod.POST })
  public GenericResponseData review(@PathVariable("productId") int productId, @RequestBody ProductReview review,
      @CookieValue(value = "fingerprint", required = true) String fingerprint, HttpServletRequest request) throws Exception {
    review.setProductId(productId);
    review.setIp(request.getRemoteAddr());
    review.setFingerprint(fingerprint);
    int rst = this.productService.review(review);
    if (rst == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Success");
    } else if (rst == -1) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Already reviewed");
    } else if (rst == -2) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Too many reviews from the same ip");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "");
    }
  }

  /**
   * reviews
   *
   * @param productId
   * @param fingerprint
   * @param pageable
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "{productId}/review", method = { RequestMethod.GET })
  public GenericResponseData reviews(@PathVariable("productId") int productId, @CookieValue(value = "fingerprint", required = true) String fingerprint,
      Pageable pageable, HttpServletRequest request) throws Exception {
    Page<ProductReview> reviews = this.productService.listReview(productId, 0, pageable);
    if (reviews == null || reviews.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, reviews);
    }
  }

  /**
   * list
   *
   * @param pageable
   * @return
   * @throws Exception
   */
  @RequestMapping(method = RequestMethod.GET)
  public GenericResponseData list(@RequestParam(value = "store_id", required = false, defaultValue = "0") int storeId,
      @RequestParam(value = "add_by", required = false, defaultValue = "0") int addBy,
      @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
      @RequestParam(value = "disabled", required = false, defaultValue = "false") boolean disabled,
      @RequestParam(value = "order_by", required = false, defaultValue = "createdAt") String orderBy,
      @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, Pageable pageable) throws Exception {

    Page<Product> products = this.productService.listAllOnFrontEnd(pageable);
    if (products == null || products.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, products);
    }
  }

  /**
   * retrieve
   *
   * @param productId
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "{productId}", method = RequestMethod.GET)
  public GenericResponseData retrieve(@PathVariable("productId") int productId) throws Exception {
    Product product = this.productService.get(productId);
    if (product == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, product);
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
  public GenericResponseData update(@CookieValue(value = "token", required = false) String tokenString, @RequestBody Product product) {
    AuthorizationToken token = this.authorizationService.verify(tokenString);
    if (token == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == MEMBER_ROLE.ADMIN) {
      if (product.validate()) {
        Product productFromDb = this.productService.update(product);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, productFromDb);
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "");
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
  public GenericResponseData insert(@CookieValue(value = "token", required = false) String tokenString, @RequestBody Product product) {
    AuthorizationToken token = this.authorizationService.verify(tokenString);
    if (token == null) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole() == MEMBER_ROLE.ADMIN) {
      System.out.println(product);
      if (product.validate()) {
        Product productFromDb = this.productService.insert(product);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, productFromDb);
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.FAIL, "");
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "");
    }
  }
}
