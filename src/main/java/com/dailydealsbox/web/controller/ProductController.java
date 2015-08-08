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

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.ProductLike;
import com.dailydealsbox.database.model.ProductReview;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.database.service.ProductService;
import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/product")
@Api(value = "Product", description = "Product Management Operation")
public class ProductController {

  @Autowired
  private MemberService memberService;

  @Autowired
  private ProductService productService;

  /**
   * addLike
   *
   * @param productId
   * @param fingerprint
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "id/{productId}/like", method = { RequestMethod.POST })
  @ApiOperation(value = "Product like", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Add new like for product.")
  public GenericResponseData addLike(@ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiParam(value = "fingerprint", required = true) @CookieValue(value = "fingerprint", required = true) String fingerprint, HttpServletRequest request) throws Exception {
    int rst = this.productService.addLike(productId, fingerprint, request.getRemoteAddr());
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
   * listLikes
   *
   * @param productId
   * @param pageable
   * @param request
   * @return
   */
  @RequestMapping(value = "id/{productId}/like", method = { RequestMethod.GET })
  @ApiOperation(value = "List product likes", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List pageable likes for product.")
  public GenericResponseData listLike(@ApiParam(value = "product id", required = true) @PathVariable("productId") int productId, @ApiIgnore Pageable pageable, HttpServletRequest request) {
    Page<ProductLike> likes = this.productService.listLike(productId, pageable);
    if (likes == null || likes.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, likes);
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
  @RequestMapping(value = "id/{productId}/review", method = { RequestMethod.POST })
  @ApiOperation(value = "Add review", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Add a new review for product.")
  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  public GenericResponseData addReview(@ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiParam(value = "review", required = true) @RequestBody ProductReview review,
      @ApiParam(value = "fingerprint", required = true) @CookieValue(value = "fingerprint", required = true) String fingerprint, HttpServletRequest request) throws Exception {
    review.setProductId(productId);
    review.setIp(request.getRemoteAddr());
    review.setFingerprint(fingerprint);
    int rst = this.productService.addReview(review);
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
   * listReview
   *
   * @param productId
   * @param deleted
   * @param pageable
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "id/{productId}/review", method = { RequestMethod.GET })
  @ApiOperation(value = "list reviews", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List pageable reviews for product.")
  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  public GenericResponseData listReview(@ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
      @ApiIgnore Pageable pageable) throws Exception {

    Page<ProductReview> reviews = this.productService.listReview(productId, deleted, pageable);
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
  @ApiOperation(value = "list product", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List pageable products.")
  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  public GenericResponseData list(
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
      @ApiParam(value = "filter: is disabled", required = false, defaultValue = "false") @RequestParam(value = "disabled", required = false, defaultValue = "false") boolean disabled,
      @ApiIgnore Pageable pageable) throws Exception {
    Page<Product> products = this.productService.list(deleted, disabled, pageable);
    if (products == null || products.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, products);
    }
  }

  /**
   * listFollowed
   *
   * @param deleted
   * @param disabled
   * @param pageable
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "followed", method = RequestMethod.GET)
  @ApiOperation(value = "list followed product", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List pageable followed products.")
  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  @DDBAuthorization
  public GenericResponseData listFollowed(
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
      @ApiParam(value = "filter: is disabled", required = false, defaultValue = "false") @RequestParam(value = "disabled", required = false, defaultValue = "false") boolean disabled,
      @ApiIgnore Pageable pageable, HttpServletRequest request) throws Exception {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Member member = this.memberService.get(token.getMemberId());
    Page<Product> products = this.productService.listByStores(member.getStores(), deleted, disabled, pageable);
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
  @RequestMapping(value = "id/{productId}", method = RequestMethod.GET)
  @ApiOperation(value = "retrieve product", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Retrieve product information.")
  public GenericResponseData retrieve(@ApiParam(value = "product id", required = true) @PathVariable("productId") int productId) throws Exception {
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
  @RequestMapping(value = "id/{productId}", method = { RequestMethod.PUT })
  @ApiOperation(value = "update product", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Update product information.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData update(@ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiIgnore @CookieValue(value = "token", required = false) String tokenString, @ApiParam(value = "product object", required = true) @RequestBody Product product) {

    if (product.validate()) {
      Product productFromDb = this.productService.get(productId);
      if (productFromDb == null) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "002");
      } else {
        product.setId(productId);
        productFromDb = this.productService.update(product);
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, productFromDb);
      }
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
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
  @ApiOperation(value = "insert product", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Insert a new product.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData insert(@ApiIgnore @CookieValue(value = "token", required = false) String tokenString, @ApiParam(value = "product object", required = true) @RequestBody Product product) {
    if (product.validate()) {
      Product productFromDb = this.productService.insert(product);
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, productFromDb);
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
    }
  }
}
