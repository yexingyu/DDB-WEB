/**
 *
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.repository.ProductRepository;
import com.dailydealsbox.service.ProductService;
import com.dailydealsbox.web.base.GeneralResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
  private ProductRepository productRepo;
  @Autowired
  private ProductService    productService;

  @RequestMapping(method = RequestMethod.GET)
  public GeneralResponseData test() {
    //this.productService.delete(75);
    //List<Product> products = (List<Product>) this.productRepo.findAll();
    //Page<Product> products = this.productRepo.findByStatus(BaseEntityModel.STATUS.AVAILABLE, null);
    //Page<Product> products = this.productRepo.findByStatusAndEnableOrderByCreatedAtDesc(BaseEntityModel.STATUS.AVAILABLE, true, null);
    //Page<Product> products = this.productService.listAllOnFrontEnd(null);
    Page<Product> products = null;
    return GeneralResponseData.newInstance(RESPONSE_STATUS.SUCCESS, products);
  }

}
