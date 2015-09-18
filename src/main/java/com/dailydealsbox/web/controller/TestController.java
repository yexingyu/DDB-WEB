/**
 *
 */
package com.dailydealsbox.web.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailydealsbox.web.annotation.DDBAuthorization;
import com.dailydealsbox.web.base.GenericResponseData;
import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.web.configuration.BaseEnum.RESPONSE_STATUS;
import com.dailydealsbox.web.database.model.Member;
import com.dailydealsbox.web.database.repository.ProductRepository;
import com.dailydealsbox.web.database.repository.ProductTagRepository;
import com.dailydealsbox.web.database.repository.StoreRepository;
import com.dailydealsbox.web.service.MailService;
import com.dailydealsbox.web.service.MemberService;
import com.dailydealsbox.web.service.ProductService;
import com.dailydealsbox.web.service.StoreService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * @author x_ye
 */
@RestController
@RequestMapping("/api/test")
@ApiIgnore
public class TestController {

  @Autowired
  private MemberService        memberService;
  @Autowired
  private StoreService         storeService;
  @Autowired
  private MailService          mailService;
  @Autowired
  private ProductRepository    productRepo;
  @Autowired
  private ProductService       productService;
  @Autowired
  private ProductTagRepository repo;
  @Autowired
  private StoreRepository      repoStore;

  @RequestMapping(method = RequestMethod.GET)
  @DDBAuthorization(requireAuthorization = false)
  public GenericResponseData test(@RequestParam(value = "tags", required = false) Set<String> tags, Pageable pageable, HttpServletRequest request) throws Exception {

    //Page<Product> products = productRepo.findByTagAndDeletedAndDisabled(Collections.<String> singleton("watch"), false, false, pageable);

    //Member me = memberService.getByAccount("yexingyu@gmail.com");
    //    Store walmart = storeService.get(1);
    //    Store ebay = storeService.get(2);
    //    if (me.getStores() == null) {
    //      me.setStores(new HashSet<Store>());
    //    }
    //    me.getStores().add(ebay);
    //    memberService.update(me);

    //Page<Product> result = productRepo.findByStoreAndDeletedAndDisabled(me.getStores(), false, false, pageable);

    //AuthorizationToken token = (AuthorizationToken) request.getAttribute(BaseAuthorization.TOKEN);
    //Member member = this.memberService.get(token.getMemberId());

    //Set<Store> rst = this.repoStore.findByIds(ids);
    //Page<Product> rst = this.productRepo.searchByName("iphone", pageable);
    //Member me = this.memberService.getByAccount("yexingyu@gmail.com");
    //    Set<Integer> ids = new HashSet<Integer>();
    //    ids.add(1);
    //    ids.add(2);
    //
    //    Page<Store> rst = this.storeService.list(ids, null, null, false, pageable);

    Set<Member> rst = this.memberService.listByRole(MEMBER_ROLE.CONTRIBUTOR);
    return GenericResponseData.newInstance(RESPONSE_STATUS.SUCCESS, rst);
  }

}
