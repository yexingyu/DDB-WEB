/**
 *
 */
package com.dailydealsbox.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.ProductLike;
import com.dailydealsbox.database.model.ProductReview;
import com.dailydealsbox.database.repository.ProductLikeRepository;
import com.dailydealsbox.database.repository.ProductRepository;
import com.dailydealsbox.database.repository.ProductReviewRepository;
import com.dailydealsbox.service.ProductService;

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

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#get(int)
   */
  @Override
  public Product get(int id) {
    return this.repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#update(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Product update(Product product) {
    product.setProductForChildren();
    return this.repo.save(product);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#insert(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Product insert(Product product) {
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
   * @see
   * com.dailydealsbox.service.ProductService#listAllOnFrontEnd(org.springframework.data.domain.
   * Pageable)
   */
  @Override
  public Page<Product> listAllOnFrontEnd(Pageable pageable) {
    return this.repo.findByDisabledAndDeletedOrderByCreatedAtDesc(false, false, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#listByStoreIdOnFrontEnd(int,
   * org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Product> listByStoreIdOnFrontEnd(int storeId, Pageable pageable) {
    return this.repo.findByStoreIdAndDisabledAndDeletedOrderByCreatedAtDesc(storeId, false, false, pageable);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductService#like(int, java.lang.String, java.lang.String)
   */
  @Override
  public int like(int productId, String fingerprint, String ip) {
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
   * @see com.dailydealsbox.service.ProductService#review(com.dailydealsbox.database.model.ProductReview)
   */
  @Override
  public int review(ProductReview review) {
    if (null != this.repoReview.findFirstByProductIdAndIpAndFingerprint(review.getProductId(), review.getIp(), review.getFingerprint())) { return -1; }
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
  public Page<ProductReview> listReview(int productId, int deleted, Pageable pageable) {
    return this.repoReview.findByProductIdAndDeletedOrderByCreatedAtDesc(productId, 0, pageable);
  }

}
