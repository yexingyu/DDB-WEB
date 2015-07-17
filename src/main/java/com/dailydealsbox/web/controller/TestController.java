/**
 *
 */
package com.dailydealsbox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.repository.ProductRepository;
import com.dailydealsbox.database.repository.ProductReviewRepository;
import com.dailydealsbox.database.service.ProductService;
import com.dailydealsbox.web.base.GenericResponseData;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
  private ProductRepository       productRepo;
  @Autowired
  private ProductService          productService;
  @Autowired
  private ProductReviewRepository reviewRepo;

  @RequestMapping(method = RequestMethod.GET)
  public GenericResponseData test() throws Exception {
    //this.productService.delete(75);
    //List<Product> products = (List<Product>) this.productRepo.findAll();
    //Page<Product> products = this.productRepo.findByStatus(BaseEntityModel.STATUS.AVAILABLE, null);
    //Page<Product> products = this.productRepo.findByStatusAndEnableOrderByCreatedAtDesc(BaseEntityModel.STATUS.AVAILABLE, true, null);
    //Page<Product> products = this.productService.listAllOnFrontEnd(null);
    //Page<Product> products = null;

    //this.reviewRepo.delete(1);
    throw new Exception("tttt");
    //return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, "");
  }

}
