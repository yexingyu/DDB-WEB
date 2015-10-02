/**
 *
 */
package com.dailydealsbox.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.web.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.web.database.model.Member;
import com.dailydealsbox.web.database.model.Product;
import com.dailydealsbox.web.database.model.ProductFee;
import com.dailydealsbox.web.database.model.ProductImage;
import com.dailydealsbox.web.database.model.ProductLike;
import com.dailydealsbox.web.database.model.ProductLink;
import com.dailydealsbox.web.database.model.ProductOption;
import com.dailydealsbox.web.database.model.ProductPrice;
import com.dailydealsbox.web.database.model.ProductReview;
import com.dailydealsbox.web.database.model.ProductTag;
import com.dailydealsbox.web.database.model.ProductTax;
import com.dailydealsbox.web.database.model.ProductText;
import com.dailydealsbox.web.database.model.Store;
import com.dailydealsbox.web.database.repository.ProductLikeRepository;
import com.dailydealsbox.web.database.repository.ProductRepository;
import com.dailydealsbox.web.database.repository.ProductReviewRepository;
import com.dailydealsbox.web.database.repository.ProductTagRepository;
import com.dailydealsbox.web.service.ProductService;
import com.dailydealsbox.web.service.StoreService;

/**
 * @author x_ye
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

  @Resource
  private ProductRepository       repo;
  @Resource
  private ProductLikeRepository   repoLike;
  @Resource
  private ProductReviewRepository repoReview;
  @Resource
  private ProductTagRepository    repoTag;
  @Resource
  private StoreService            storeService;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#get(int)
   */
  @Override
  public Product get(int id) {
    return this.repo.findOne(id);
  }

  /**
   * fix
   *
   * @param product
   * @return
   */
  private Product fix(Product product) {
    // tag
    this.fixTags(product);

    // fee
    for (ProductFee o : product.getFees()) {
      o.setProduct(product);
    }
    // image
    for (ProductImage o : product.getImages()) {
      o.setProduct(product);
    }
    // text
    for (ProductText o : product.getTexts()) {
      o.setProduct(product);
    }
    // price
    for (ProductPrice o : product.getPrices()) {
      o.setProduct(product);
    }
    // tax
    for (ProductTax o : product.getTaxes()) {
      o.setProduct(product);
    }
    // link
    for (ProductLink o : product.getLinks()) {
      o.setProduct(product);
    }
    // option
    for (ProductOption o : product.getOptions()) {
      o.setProduct(product);
    }

    return product;
  }

  /**
   * fixTags
   *
   * @param product
   * @return
   */
  private Product fixTags(Product product) {
    Set<ProductTag> tags = product.getTags();
    product.setTags(new HashSet<ProductTag>());
    for (ProductTag tag : tags) {
      ProductTag tagDB = this.repoTag.findFirstByValue(tag.getValue());
      if (tagDB != null) {
        product.getTags().add(tagDB);
      } else {
        product.getTags().add(tag);
      }
    }
    return product;
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#update(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Product update(Product product) {
    this.fix(product);
    return this.repo.save(product);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#insert(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Product insert(Product product) {
    this.fix(product);
    return this.update(product);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#delete(int)
   */
  @Override
  public void delete(int id) {
    this.repo.delete(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.web.service.ProductService#addLike(int, java.lang.String, java.lang.String, boolean)
   */
  @Override
  public int addLike(int productId, String fingerprint, String ip, boolean positive) {
    if (null != this.repoLike.findFirstByProductIdAndIpAndFingerprint(productId, ip, fingerprint)) { return -1; }
    if (this.repoLike.countByProductIdAndIp(productId, ip) > 10) { return -2; }

    try {
      // retrieve product
      Product product = this.repo.findOne(productId);
      if (product == null) { return -3; }

      // insert product_like
      ProductLike like = new ProductLike();
      like.setIp(ip);
      like.setFingerprint(fingerprint);
      like.setProductId(productId);
      like.setPositive(positive);
      this.repoLike.save(like);

      // update product.count_likes
      if (positive) {
        this.repo.increaseCountLikes(productId);
      } else {
        this.repo.increaseCountUnlikes(productId);
      }

      // update store.count_likes
      this.storeService.increaseCountLikes(product.getStore().getId(), positive);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return 0;

  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#addReview(com.dailydealsbox.database.model.ProductReview)
   */
  @Override
  public int addReview(ProductReview review) {
    //if (null != this.repoReview.findFirstByProductIdAndIpAndFingerprintAndDeletedFalse(review.getProductId(), review.getIp(), review.getFingerprint())) { return -1; }
    //if (this.repoReview.countByProductIdAndIp(review.getProductId(), review.getIp()) > 10) { return -2; }

    // retrieve product item
    Product product = this.repo.findOne(review.getProductId());
    if (product == null) { return -3; }

    // insert product like
    this.repoReview.save(review);

    // update product.count_reviews
    this.repo.increaseCountReviews(review.getProductId());

    return 0;
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#deleteReview(int)
   */
  @Override
  public void deleteReview(int reviewId) {
    this.repoReview.delete(reviewId);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#listReview(int, int, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<ProductReview> listReview(int productId, boolean deleted, Pageable pageable) {
    return this.repoReview.findByProductIdAndDeleted(productId, deleted, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#listLike(int, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<ProductLike> listLike(int productId, Pageable pageable) {
    return this.repoLike.findByProductId(productId, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#listAllTag()
   */
  @Override
  public Set<ProductTag> listAllTag() {
    return this.repoTag.findAll();
  }

  @Autowired
  private EntityManager em;

  /**
   * fixIds
   *
   * @param ids
   * @return
   */
  private Set<Integer> fixIds(Set<Integer> ids) {
    if (ids == null || ids.isEmpty()) {
      ids = null;
    } else {
      Iterator<Integer> itId = ids.iterator();
      while (itId.hasNext()) {
        int id = itId.next();
        if (id <= 0) {
          itId.remove();
        }
      }
    }
    return ids;
  }

  /**
   * combineStoreIdsAndMemberFollowedStores
   *
   * @param storeIds
   * @param member
   * @return
   */
  private Set<Integer> combineStoreIdsAndMemberFollowedStores(Set<Integer> storeIds, Member member) {
    if (member != null && member.getStores() != null && !member.getStores().isEmpty()) {
      Set<Integer> storeIdsfollowed = new HashSet<>();
      for (Store store : member.getStores()) {
        storeIdsfollowed.add(store.getId());
      }
      if (storeIds == null) {
        storeIds = storeIdsfollowed;
      } else {
        Iterator<Integer> it = storeIds.iterator();
        while (it.hasNext()) {
          int storeId = it.next();
          if (!storeIdsfollowed.contains(storeId)) {
            it.remove();
          }
        }
      }
    }
    if (storeIds == null || storeIds.isEmpty()) {
      storeIds = null;
    }
    return storeIds;
  }

  /**
   * fixTags
   *
   * @param tags
   * @return
   */
  private Set<String> fixTags(Set<String> tags) {
    if (tags != null) {
      Set<String> fixedTags = new HashSet<>();
      for (String tag : tags) {
        if (!StringUtils.isBlank(tag)) {
          fixedTags.add(StringUtils.lowerCase(StringUtils.trim(tag)));
        }
      }
      if (!fixedTags.isEmpty()) {
        tags = fixedTags;
      } else {
        tags = null;
      }
    }
    return tags;
  }

  /**
   * buildQuery
   *
   * @param ids
   * @param storeIds
   * @param tags
   * @param countries
   * @param member
   * @param deleted
   * @param disabled
   * @return
   * @throws Exception
   */
  private TypedQuery<Product> buildQuery(Set<Integer> ids, Set<Integer> storeIds, Set<String> tags, Set<COUNTRY> countries, Member member, boolean deleted, boolean disabled) throws Exception {
    // fixing ids
    ids = this.fixIds(ids);
    storeIds = this.fixIds(storeIds);
    storeIds = this.combineStoreIdsAndMemberFollowedStores(storeIds, member);

    // lowercase tags
    tags = this.fixTags(tags);

    //  build criteriaQuery
    CriteriaBuilder builder = this.em.getCriteriaBuilder();
    CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
    Root<Product> productRoot = criteriaQuery.from(Product.class);
    EntityType<Product> productModel = productRoot.getModel();
    List<Predicate> predicates = new ArrayList<Predicate>();

    // where id in (ids)
    if (ids != null && !ids.isEmpty()) {
      predicates.add(productRoot.get("id").in(ids));
    }

    // where store.id in (storeIds)
    Join<Product, Store> storeJoin = null;
    if (storeIds != null) {
      if (storeJoin == null) {
        storeJoin = productRoot.join(productModel.getSingularAttribute("store", Store.class));
      }
      predicates.add(storeJoin.get("id").in(storeIds));
    }

    // where country in (countries)
    if (countries != null) {
      if (storeJoin == null) {
        storeJoin = productRoot.join(productModel.getSingularAttribute("store", Store.class));
      }
      predicates.add(storeJoin.get("country").in(countries));
    }

    // where tag.value in (tags)
    if (tags != null) {
      Join<Product, ProductTag> tagJoin = productRoot.join(productModel.getDeclaredSet("tags", ProductTag.class));
      predicates.add(tagJoin.get("value").in(tags));
    }

    predicates.add(builder.equal(productRoot.get("disabled"), disabled));
    predicates.add(builder.equal(productRoot.get("deleted"), deleted));
    criteriaQuery.where(predicates.toArray(new Predicate[] {}));

    return this.em.createQuery(criteriaQuery);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#listAll(java.util.Set, java.util.Set, java.util.Set, java.util.Set,
   * com.dailydealsbox.database.model.Member, boolean, boolean)
   */
  @Override
  public Set<Product> listAll(Set<Integer> ids, Set<Integer> storeIds, Set<String> tags, Set<COUNTRY> countries, Member member, boolean deleted, boolean disabled) throws Exception {
    TypedQuery<Product> query = this.buildQuery(ids, storeIds, tags, countries, member, deleted, disabled);
    query.setHint("org.hibernate.cacheable", true);
    return new HashSet<Product>(query.getResultList());
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#list(java.util.Set, java.util.Set, java.util.Set, java.util.Set,
   * com.dailydealsbox.database.model.Member, boolean, boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Product> list(Set<Integer> storeIds, Set<String> tags, Set<COUNTRY> countries, Member member, boolean deleted, boolean disabled, Pageable pageable) throws Exception {
    // fixing storeIds
    storeIds = this.fixIds(storeIds);
    storeIds = this.combineStoreIdsAndMemberFollowedStores(storeIds, member);
    Set<Store> stores = this.storeService.listAll(storeIds, countries, null, false);

    // lowercase tags
    tags = this.fixTags(tags);

    Page<Product> products = null;
    if (stores != null && tags != null) {
      products = this.repo.findByStoresAndTagsAndDeletedAndDisabled(stores, tags, deleted, disabled, pageable);
    } else if (stores != null) {
      products = this.repo.findByStoresAndDeletedAndDisabled(stores, deleted, disabled, pageable);
    } else if (tags != null) {
      products = this.repo.findByTagsAndDeletedAndDisabled(tags, deleted, disabled, pageable);
    } else {
      products = this.repo.findByDisabledAndDeleted(deleted, disabled, pageable);
    }

    return products;
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#fixProduct()
   */
  @Override
  public void fixProduct() throws Exception {
    Calendar c = Calendar.getInstance();
    c.clear(Calendar.MILLISECOND);
    c.clear(Calendar.SECOND);
    c.clear(Calendar.MINUTE);
    c.add(Calendar.HOUR_OF_DAY, -2);
    Date stamp = c.getTime();
    Set<Product> products = this.repo.findAllByModifiedAtGreaterThanAndDeletedFalseAndDisabledFalse(stamp);
    for (Product product : products) {
      product.computeReputation();
      this.repo.save(product);
    }
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.web.service.ProductService#search(java.lang.String, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Product> search(String keyword, Pageable pageable) throws Exception {
    return repo.search(keyword, pageable);
  }

}
