/**
 *
 */
package com.dailydealsbox.database.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.ProductFee;
import com.dailydealsbox.database.model.ProductImage;
import com.dailydealsbox.database.model.ProductLike;
import com.dailydealsbox.database.model.ProductLink;
import com.dailydealsbox.database.model.ProductOption;
import com.dailydealsbox.database.model.ProductPrice;
import com.dailydealsbox.database.model.ProductReview;
import com.dailydealsbox.database.model.ProductTag;
import com.dailydealsbox.database.model.ProductTax;
import com.dailydealsbox.database.model.ProductText;
import com.dailydealsbox.database.model.Store;
import com.dailydealsbox.database.repository.ProductLikeRepository;
import com.dailydealsbox.database.repository.ProductRepository;
import com.dailydealsbox.database.repository.ProductReviewRepository;
import com.dailydealsbox.database.repository.ProductTagRepository;
import com.dailydealsbox.database.service.ProductService;

/**
 * @author x_ye
 */
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

  @Resource
  private ProductRepository repo;

  @Resource
  private ProductLikeRepository repoLike;

  @Resource
  private ProductReviewRepository repoReview;

  @Resource
  private ProductTagRepository repoTag;

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
   * @see com.dailydealsbox.service.ProductService#addLike(int, java.lang.String, java.lang.String)
   */
  @Override
  public int addLike(int productId, String fingerprint, String ip) {
    if (null != this.repoLike.findFirstByProductIdAndIpAndFingerprint(productId, ip, fingerprint)) { return -1; }
    if (this.repoLike.countByProductIdAndIp(productId, ip) > 10) { return -2; }

    // insert product_like
    ProductLike like = new ProductLike();
    like.setIp(ip);
    like.setFingerprint(fingerprint);
    like.setProductId(productId);
    this.repoLike.save(like);

    // update product.count_likes
    this.repo.increaseCountLikes(productId);

    return 0;

  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#addReview(com.dailydealsbox.database.model.ProductReview)
   */
  @Override
  public int addReview(ProductReview review) {
    if (null != this.repoReview.findFirstByProductIdAndIpAndFingerprintAndDeletedFalse(review.getProductId(), review.getIp(), review.getFingerprint())) { return -1; }
    if (this.repoReview.countByProductIdAndIp(review.getProductId(), review.getIp()) > 10) { return -2; }

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
   * @see com.dailydealsbox.database.service.ProductService#list(boolean, boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Product> list(boolean deleted, boolean disabled, Pageable pageable) {
    return this.repo.findByDisabledAndDeleted(deleted, disabled, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#list(int, boolean, boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Product> list(int storeId, boolean deleted, boolean disabled, Pageable pageable) {
    return this.repo.findByStoreIdAndDisabledAndDeleted(storeId, deleted, disabled, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#listLike(int, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<ProductLike> listLike(int productId, Pageable pageable) {
    return this.repoLike.findByProductId(productId, pageable);
  }

  @Override
  public Page<Product> listByTags(Set<String> tags, boolean deleted, boolean disabled, Pageable pageable) {
    return this.repo.findByTagsAndDeletedAndDisabled(tags, deleted, disabled, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#listByStores(java.util.Set, boolean, boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Product> listByStores(Set<Store> stores, boolean deleted, boolean disabled, Pageable pageable) {
    return this.repo.findByStoresAndDeletedAndDisabled(stores, deleted, disabled, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#listAllTag()
   */
  @Override
  public Set<ProductTag> listAllTag() {
    return this.repoTag.findAll();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductService#list(java.util.Set, java.util.Set, boolean, boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Product> list(Set<Store> stores, Set<String> tags, boolean deleted, boolean disabled, Pageable pageable) {
    return this.repo.findByStoresAndTagsAndDeletedAndDisabled(stores, tags, deleted, disabled, pageable);
  }

}
