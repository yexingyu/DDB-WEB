/**
 *
 */
package com.dailydealsbox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/order")
@Api(value = "Order", description = "Order Management Operation")
public class OrderController {

  @Autowired
  OrderService orderService;

  @Autowired
  AuthorizationService authorizationService;

  /**
   * listAll
   *
   * @param deleted
   * @param pageable
   * @return
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "list orders", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List pageable orders.")
  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData listAll(
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
      @ApiIgnore Pageable pageable) {
    Page<Order> orders = this.orderService.listAll(deleted, pageable);
    if (orders == null || orders.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, orders);
    }
  }

  /**
   * list
   * 
   * @param deleted
   * @param pageable
   * @param request
   * @return
   */
  @RequestMapping(value = "me", method = RequestMethod.GET)
  @ApiOperation(value = "list my orders", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "List pageable orders of specific member.")
  @ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "page number", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "size", value = "page size", required = false, defaultValue = "20", dataType = "int", paramType = "query"),
      @ApiImplicitParam(name = "sort", value = "sorting. (eg. &sort=createdAt,desc)", required = false, defaultValue = "", dataType = "String", paramType = "query") })
  @DDBAuthorization
  public GenericResponseData list(
      @ApiParam(value = "filter: is deleted", required = false, defaultValue = "false") @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
      @ApiIgnore Pageable pageable, HttpServletRequest request) {
    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    Page<Order> orders = this.orderService.listByMemberId(token.getMemberId(), deleted, pageable);
    if (orders == null || orders.getNumberOfElements() == 0) {
      return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
    } else {
      return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, orders);
    }
  }

  /**
   * retrieve
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "id/{id}", method = RequestMethod.GET)
  @ApiOperation(value = "retrieve order details", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Retrieve order details.")
  @DDBAuthorization
  public GenericResponseData retrieve(@ApiParam(value = "order id", required = true) @PathVariable("id") int id, HttpServletRequest request) {

    AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    if (token.getRole() == MEMBER_ROLE.ADMIN) {
      Order order = this.orderService.get(id);
      if (order == null) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.EMPTY_RESULT, "");
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, order);
      }
    } else {
      Order order = this.orderService.get(id);
      if (order == null || order.getMemberId() != token.getMemberId()) {
        return GenericResponseData.newInstance(RESPONSE_STATUS.NO_PERMISSION, "");
      } else {
        return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, order);
      }
    }
  }

  /**
   * insert
   *
   * @param tokenString
   * @param order
   * @return
   */
  @RequestMapping(method = { RequestMethod.POST })
  @ApiOperation(value = "insert order", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Insert a new order.")
  @DDBAuthorization
  public GenericResponseData insert(@ApiParam(value = "order object", required = true) @RequestBody Order order) {
    order.setStatus(BaseEnum.ORDER_STATUS.NEW);
    Order orderFromDb = this.orderService.insert(order);
    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, orderFromDb);
  }

  /**
   * switchStatus
   *
   * @param orderId
   * @param status
   * @param tokenString
   * @return
   */
  @RequestMapping(value = "id/{id}/switch_status", method = { RequestMethod.PUT })
  @ApiOperation(value = "switch status", response = GenericResponseData.class, responseContainer = "Map", produces = "application/json", notes = "Switch status of order.")
  @DDBAuthorization({ MEMBER_ROLE.ADMIN })
  public GenericResponseData switchStatus(@ApiParam(value = "order id", required = true) @PathVariable("id") int orderId,
      @ApiParam(value = "order object", required = true) @RequestBody String status) {
    BaseEnum.ORDER_STATUS orderStatus = BaseEnum.ORDER_STATUS.valueOf(status);
    this.orderService.modifiyStatus(orderId, orderStatus);
    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, orderStatus);
  }
}
