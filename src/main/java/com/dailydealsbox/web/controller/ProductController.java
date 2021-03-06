/**
 *
 */
package com.dailydealsbox.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashSet;
import java.util.Set;

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

import springfox.documentation.annotations.ApiIgnore;

import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.AuthorizationToken;
import com.dailydealsbox.web.base.BaseAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;
import com.dailydealsbox.web.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.web.configuration.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.web.database.model.Member;
import com.dailydealsbox.web.database.model.Poster;
import com.dailydealsbox.web.database.model.Product;
import com.dailydealsbox.web.database.model.ProductLike;
import com.dailydealsbox.web.database.model.ProductReview;
import com.dailydealsbox.web.database.model.ProductTag;
import com.dailydealsbox.web.database.model.Store;
import com.dailydealsbox.web.service.MemberService;
import com.dailydealsbox.web.service.ProductService;
import com.dailydealsbox.web.service.StoreService;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/product")
@Api(value = "Product", description = "Product Management Operation")
public class ProductController {

  @Autowired
  private MemberService  memberService;
  @Autowired
  private ProductService productService;
  @Autowired
  private StoreService   storeService;

  /**
   * like
   *
   * @param productId
   * @param fingerprint
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "id/{productId}/like", method = { RequestMethod.POST })
  @ApiOperation(value = "Product like",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Add new like for product.")
  public GenericResponseData like(
      @ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiParam(value = "fingerprint", required = true) @CookieValue(value = "fingerprint",
        required = true) String fingerprint, HttpServletRequest request) throws Exception {
    return this.addLike(productId, fingerprint, true, request);
  }

  /**
   * unlike
   *
   * @param productId
   * @param fingerprint
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "id/{productId}/unlike", method = { RequestMethod.POST })
  @ApiOperation(value = "Product like",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Add new unlike for product.")
  public GenericResponseData unlike(
      @ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiParam(value = "fingerprint", required = true) @CookieValue(value = "fingerprint",
        required = true) String fingerprint, HttpServletRequest request) throws Exception {
    return this.addLike(productId, fingerprint, false, request);
  }

  /**
   * addLike
   *
   * @param productId
   * @param fingerprint
   * @param positive
   * @param request
   * @return
   */
  private GenericResponseData addLike(int productId, String fingerprint, boolean positive,
      HttpServletRequest request) {
    int rst = this.productService
        .addLike(productId, fingerprint, request.getRemoteAddr(), positive);
    if (rst == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Success");
    } else if (rst == -1) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Already liked");
    } else if (rst == -2) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS,
          "Too many likes from the same ip");
    } else if (rst == -3) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "No this item");
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
  @ApiOperation(value = "List product likes",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "List pageable likes for product.")
  public GenericResponseData listLike(
      @ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiIgnore Pageable pageable, HttpServletRequest request) {
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
  @ApiOperation(value = "Add review",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Add a new review for product.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page",
        value = "page number",
        required = false,
        defaultValue = "0",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "size",
        value = "page size",
        required = false,
        defaultValue = "20",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "sort",
        value = "sorting. (eg. &sort=createdAt,desc)",
        required = false,
        defaultValue = "",
        dataType = "String",
        paramType = "query") })
  @DDBAuthorization(requireAuthorization = false)
  public GenericResponseData addReview(
      @ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiParam(value = "review", required = true) @RequestBody ProductReview review,
      @ApiParam(value = "fingerprint", required = true) @CookieValue(value = "fingerprint",
        required = true) String fingerprint, HttpServletRequest request) throws Exception {

    // retrieve token from cookie
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Poster poster = null;
    if (token != null) {
      Member me = this.memberService.get(token.getMemberId());
      poster = new Poster(me);
    }

    review.setProductId(productId);
    review.setIp(request.getRemoteAddr());
    review.setFingerprint(fingerprint);
    review.setPoster(poster);
    int rst = this.productService.addReview(review);
    if (rst == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Success");
    } else if (rst == -1) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "Already reviewed");
    } else if (rst == -2) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS,
          "Too many reviews from the same ip");
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
  @ApiOperation(value = "list reviews",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "List pageable reviews for product.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page",
        value = "page number",
        required = false,
        defaultValue = "0",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "size",
        value = "page size",
        required = false,
        defaultValue = "20",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "sort",
        value = "sorting. (eg. &sort=createdAt,desc)",
        required = false,
        defaultValue = "",
        dataType = "String",
        paramType = "query") })
  public GenericResponseData listReview(
      @ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted",
        required = false,
        defaultValue = "false") boolean deleted, @ApiIgnore Pageable pageable) throws Exception {

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
   * @param storeIds
   * @param tags
   * @param countries
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   * @throws Exception
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "list product",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "List pageable products.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page",
        value = "page number",
        required = false,
        defaultValue = "0",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "size",
        value = "page size",
        required = false,
        defaultValue = "20",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "sort",
        value = "sorting. (eg. &sort=createdAt,desc)",
        required = false,
        defaultValue = "",
        dataType = "String",
        paramType = "query") })
  public GenericResponseData list(
      @ApiParam(value = "filter: store ids", required = false) @RequestParam(value = "store_ids",
        required = false) Set<Integer> storeIds,
      @ApiParam(value = "filter: tags", required = false) @RequestParam(value = "tags",
        required = false) Set<String> tags,
      @ApiParam(value = "filter: countries", required = false) @RequestParam(value = "countries",
        required = false) Set<COUNTRY> countries, @ApiParam(value = "filter: is deleted",
        required = false,
        defaultValue = "false") @RequestParam(value = "deleted",
        required = false,
        defaultValue = "false") boolean deleted, @ApiParam(value = "filter: is disabled",
        required = false,
        defaultValue = "false") @RequestParam(value = "disabled",
        required = false,
        defaultValue = "false") boolean disabled, @ApiIgnore Pageable pageable) throws Exception {

    Page<Product> products = null;
    try {
      products = this.productService.list(storeIds, tags, countries, null, deleted, disabled,
          pageable);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (products == null || products.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, products);
    }
  }

  /**
   * search
   *
   * @param keyword
   * @param pageable
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "search", method = RequestMethod.GET)
  @ApiOperation(value = "search product",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Search pageable products.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page",
        value = "page number",
        required = false,
        defaultValue = "0",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "size",
        value = "page size",
        required = false,
        defaultValue = "20",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "sort",
        value = "sorting. (eg. &sort=createdAt,desc)",
        required = false,
        defaultValue = "",
        dataType = "String",
        paramType = "query") })
  public GenericResponseData search(
      @ApiParam(value = "search keyword", required = true) @RequestParam(value = "keyword",
        required = false) String keyword, @ApiIgnore Pageable pageable) throws Exception {
    Page<Product> products = this.productService.search(keyword, pageable);
    if (products == null || products.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, products);
    }
  }

  /**
   * listAll
   *
   * @param ids
   * @param storeIds
   * @param tags
   * @param countries
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "all", method = RequestMethod.GET)
  @ApiOperation(value = "list all product",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "List all products.")
  public GenericResponseData listAll(
      @ApiParam(value = "filter: product ids", required = false) @RequestParam(value = "ids",
        required = false) Set<Integer> ids,
      @ApiParam(value = "filter: store ids", required = false) @RequestParam(value = "store_ids",
        required = false) Set<Integer> storeIds,
      @ApiParam(value = "filter: tags", required = false) @RequestParam(value = "tags",
        required = false) Set<String> tags,
      @ApiParam(value = "filter: countries", required = false) @RequestParam(value = "countries",
        required = false) Set<COUNTRY> countries, @ApiParam(value = "filter: is deleted",
        required = false,
        defaultValue = "false") @RequestParam(value = "deleted",
        required = false,
        defaultValue = "false") boolean deleted, @ApiParam(value = "filter: is disabled",
        required = false,
        defaultValue = "false") @RequestParam(value = "disabled",
        required = false,
        defaultValue = "false") boolean disabled, @ApiIgnore Pageable pageable) throws Exception {

    try {
      System.out.println(storeIds);
      Set<Product> products = this.productService.listAll(ids, storeIds, tags, countries, null,
          deleted, disabled);

      if (products == null || products.isEmpty()) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, products);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * listFollowed
   *
   * @param storeIds
   * @param tags
   * @param countries
   * @param deleted
   * @param disabled
   * @param pageable
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "followed", method = RequestMethod.GET)
  @ApiOperation(value = "list followed product",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "List pageable followed products.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page",
        value = "page number",
        required = false,
        defaultValue = "0",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "size",
        value = "page size",
        required = false,
        defaultValue = "20",
        dataType = "int",
        paramType = "query"),
      @ApiImplicitParam(name = "sort",
        value = "sorting. (eg. &sort=createdAt,desc)",
        required = false,
        defaultValue = "",
        dataType = "String",
        paramType = "query") })
  @DDBAuthorization
  public GenericResponseData listFollowed(
      @ApiParam(value = "filter: store ids", required = false) @RequestParam(value = "store_ids",
        required = false) Set<Integer> storeIds,
      @ApiParam(value = "filter: tags", required = false) @RequestParam(value = "tags",
        required = false) Set<String> tags,
      @ApiParam(value = "filter: countries", required = false) @RequestParam(value = "countries",
        required = false) Set<COUNTRY> countries, @ApiParam(value = "filter: is deleted",
        required = false,
        defaultValue = "false") @RequestParam(value = "deleted",
        required = false,
        defaultValue = "false") boolean deleted, @ApiParam(value = "filter: is disabled",
        required = false,
        defaultValue = "false") @RequestParam(value = "disabled",
        required = false,
        defaultValue = "false") boolean disabled, @ApiIgnore Pageable pageable,
      HttpServletRequest request) throws Exception {
    // retrieve token from cookie
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);

    // retrieve/fix member
    Member member = this.memberService.get(token.getMemberId());
    if (member.getStores() == null || member.getStores().size() == 0) {
      member.setStores(new HashSet<Store>(this.storeService.listDefaultFollowed()));
      this.memberService.update(member);
    }

    // list product by stores
    Page<Product> products = null;
    try {
      products = this.productService.list(storeIds, tags, countries, member, deleted, disabled,
          pageable);
    } catch (Exception e) {
      e.printStackTrace();
    }

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
  @ApiOperation(value = "retrieve product",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Retrieve product information.")
  public GenericResponseData retrieve(
      @ApiParam(value = "product id", required = true) @PathVariable("productId") int productId)
      throws Exception {
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
   * @param productId
   * @param product
   * @return
   */
  @RequestMapping(value = "id/{productId}", method = { RequestMethod.PUT })
  @ApiOperation(value = "update product",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Update product information.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData update(
      @ApiParam(value = "product id", required = true) @PathVariable("productId") int productId,
      @ApiParam(value = "product object", required = true) @RequestBody Product product) {

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
   * @param product
   * @return
   */
  @RequestMapping(method = { RequestMethod.POST })
  @ApiOperation(value = "insert product",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Insert a new product.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData insert(
      @ApiParam(value = "product object", required = true) @RequestBody Product product,
      HttpServletRequest request) {
    if (product.validate()) {
      AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
      Member me = this.memberService.get(token.getMemberId());
      product.setPoster(new Poster(me));

      Product productFromDb = this.productService.insert(product);
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, productFromDb);
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.ERROR, "001");
    }
  }

  /**
   * listAllTags
   *
   * @return
   */
  @RequestMapping(value = "tags", method = { RequestMethod.GET })
  @ApiOperation(value = "retrieve all tags",
    response = GenericResponseData.class,
    responseContainer = "Map",
    produces = "application/json",
    notes = "Retrieve all tags.")
  public GenericResponseData listAllTags() {
    Set<ProductTag> tags = this.productService.listAllTag();
    if (tags == null || tags.isEmpty()) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, tags);
    }
  }
}
