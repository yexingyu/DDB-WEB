/**
 *
 */
package com.dailydealsbox.web.controller;

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

import com.dailydealsbox.database.model.Order;
import com.dailydealsbox.database.model.base.BaseEnum;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.service.AuthorizationService;
import com.dailydealsbox.database.service.OrderService;
import com.dailydealsbox.web.base.AuthorizationToken;
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
@RequestMapping("/api/order")
@Api(value = "Order", description = "Order Management Operation")
public class OrderController {

  @Autowired
  OrderService orderService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * list
   *
   * @param deleted
   * @param pageable
   *
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "list orders",
      response = GenericResponseData.class,
      responseContainer = "Map",
      produces = "application/json",
      notes = "List pageable orders.")
  @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"), @ApiImplicitParam(
      name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query")})
  public GenericResponseData list (
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted",
          required = false,
          defaultValue = "false") boolean deleted,
      @ApiIgnore @CookieValue(value = "token", required = false) String tokenString, @ApiIgnore Pageable pageable) {

    AuthorizationToken token = this.authorizationService.verify (tokenString);
    if (token == null) {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole () == MEMBER_ROLE.ADMIN) {
      Page<Order> orders = this.orderService.list (deleted, pageable);
      if (orders == null || orders.getNumberOfElements () == 0) {
        return GenericResponseData.newInstance (RESPONSE_STATUS.EMPTY_RESULT, "");
      } else {
        return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, orders);
      }
    } else {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NO_PERMISSION, "");
    }
  }

  /**
   * retrieve
   *
   * @param id
   *
   * @return
   */
  @RequestMapping(value = "id/{id}", method = RequestMethod.GET)
  @ApiOperation(value = "retrieve order details",
      response = GenericResponseData.class,
      responseContainer = "Map",
      produces = "application/json",
      notes = "Retrieve order details.")
  public GenericResponseData retrieve (@ApiParam(value = "order id", required = true) @PathVariable("id") int id,
                                       @ApiIgnore @CookieValue(value = "token", required = false) String tokenString) {

    AuthorizationToken token = this.authorizationService.verify (tokenString);
    if (token == null) {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NEED_LOGIN, "");
    } else if (token.getRole () == MEMBER_ROLE.ADMIN) {
      Order order = this.orderService.get (id);
      if (order == null) {
        return GenericResponseData.newInstance (RESPONSE_STATUS.EMPTY_RESULT, "");
      } else {
        return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, order);
      }
    } else {
      Order order = this.orderService.get (id);
      if (order == null || order.getMemberId () != token.getMemberId ()) {
        return GenericResponseData.newInstance (RESPONSE_STATUS.NO_PERMISSION, "");
      } else {
        return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, order);
      }
    }
  }

  /**
   * insert
   *
   * @param tokenString
   * @param order
   *
   * @return
   */
  @RequestMapping(method = {RequestMethod.POST})
  @ApiOperation(value = "insert order",
      response = GenericResponseData.class,
      responseContainer = "Map",
      produces = "application/json",
      notes = "Insert a new order.")
  public GenericResponseData insert (@ApiIgnore @CookieValue(value = "token", required = false) String tokenString,
                                     @ApiParam(value = "order object", required = true) @RequestBody Order order) {
    AuthorizationToken token = this.authorizationService.verify (tokenString);
    if (token == null) {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NEED_LOGIN, "");
    } else {
      order.setStatus (BaseEnum.ORDER_STATUS.NEW);
      Order orderFromDb = this.orderService.insert (order);
      return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, orderFromDb);
    }
  }

  /**
   * confirm
   *
   * @param orderId
   * @param tokenString
   *
   * @return
   */
  @RequestMapping(value = "id/{id}/confirm", method = {RequestMethod.PUT})
  @ApiOperation(value = "confirm order",
      response = GenericResponseData.class,
      responseContainer = "Map",
      produces = "application/json",
      notes = "Customer confirm order.")
  public GenericResponseData confirm (@ApiParam(value = "order id", required = true) @PathVariable("id") int orderId,
                                      @ApiIgnore @CookieValue(value = "token", required = false) String tokenString) {
    AuthorizationToken token = this.authorizationService.verify (tokenString);
    if (token == null) {
      return GenericResponseData.newInstance (RESPONSE_STATUS.NEED_LOGIN, "");
    } else {
      Order order = this.orderService.get (orderId);
      if (order == null || order.getMemberId () != token.getMemberId ()) {
        return GenericResponseData.newInstance (RESPONSE_STATUS.NO_PERMISSION, "");
      }
      order = this.orderService.modifiyStatus (orderId, BaseEnum.ORDER_STATUS.CONFIRMED);
      return GenericResponseData.newInstance (RESPONSE_STATUS.SUCCESS, order);
    }
  }
}
