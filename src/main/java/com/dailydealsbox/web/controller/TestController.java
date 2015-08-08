/**
 *
 */
package com.dailydealsbox.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.database.repository.ProductRepository;
import com.dailydealsbox.database.repository.ProductReviewRepository;
import com.dailydealsbox.database.service.MailService;
import com.dailydealsbox.database.service.MemberService;
import com.dailydealsbox.database.service.ProductService;
import com.dailydealsbox.database.service.StoreService;
import com.dailydealsbox.web.base.GenericResponseData;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/test")
@ApiIgnore
public class TestController {

  @Autowired
  private MemberService           memberService;
  @Autowired
  private StoreService            storeService;
  @Autowired
  private MailService             mailService;
  @Autowired
  private ProductRepository       productRepo;
  @Autowired
  private ProductService          productService;
  @Autowired
  private ProductReviewRepository reviewRepo;

  @RequestMapping(method = RequestMethod.GET)
  public GenericResponseData test(Pageable pageable, HttpServletRequest request) throws Exception {
    //this.productService.delete(75);
    //List<Product> products = (List<Product>) this.productRepo.findAll();
    //Page<Product> products = this.productRepo.findByStatus(BaseEntityModel.STATUS.AVAILABLE, null);
    //Page<Product> products = this.productRepo.findByStatusAndEnableOrderByCreatedAtDesc(BaseEntityModel.STATUS.AVAILABLE, true, null);
    //Page<Product> products = this.productService.listAllOnFrontEnd(null);
    //Page<Product> products = null;

    //this.reviewRepo.delete(1);

    //System.out.println(BaseAuthorization.TOKEN);
    //System.out.println(request.getAttribute(BaseAuthorization.TOKEN));
    //throw new Exception("tttt");

    //this.mailService.send("yexingyu@gmail.com", "no-reply email from ddb-web", "body: from ddb-web");

    //System.out.println("fbCookie=" + fbCookie);
    //    try {
    //      System.out.println("Application Namespace=" + this.facebook.getApplicationNamespace());
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    }

    //    Facebook facebook = new FacebookTemplate(accessToken);
    //
    //    boolean isAuth = facebook.isAuthorized();
    //    System.out.println("isAuth=" + isAuth);
    //
    //    User me = facebook.userOperations().getUserProfile();
    //    System.out.println(me);

    //Page<Product> products = productRepo.findByTagAndDeletedAndDisabled(Collections.<String> singleton("watch"), false, false, pageable);

    Member me = memberService.getByAccount("yexingyu@gmail.com");
    //    Store walmart = storeService.get(1);
    //    Store ebay = storeService.get(2);
    //    if (me.getStores() == null) {
    //      me.setStores(new HashSet<Store>());
    //    }
    //    me.getStores().add(ebay);
    //    memberService.update(me);

    Page<Product> result = productRepo.findByStoreAndDeletedAndDisabled(me.getStores(), false, false, pageable);

    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, result);
  }

}
