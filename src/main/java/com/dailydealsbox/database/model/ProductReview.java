/**
 *
 */
package com.dailydealsbox.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductReview extends BaseEntityModel {

  @NotNull
  @Column(name = "product_id", nullable = false)
  private int productId;

  @NotNull
  @Column(name = "ip", nullable = false)
  private String ip;

  @NotNull
  @Column(name = "fingerprint", nullable = false)
  private String fingerprint;

  @NotNull
  @Column(name = "rating", nullable = false)
  private int rating;

  @NotNull
  @Column(name = "content", nullable = false)
  private String content;

  /**
   * @return the productId
   */
  public int getProductId() {
    return this.productId;
  }

  /**
   * @param productId
   *          the productId to set
   */
  public void setProductId(int productId) {
    this.productId = productId;
  }

  /**
   * @return the ip
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * @param ip
   *          the ip to set
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * @return the fingerprint
   */
  public String getFingerprint() {
    return this.fingerprint;
  }

  /**
   * @param fingerprint
   *          the fingerprint to set
   */
  public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
  }

  /**
   * @return the rating
   */
  public int getRating() {
    return this.rating;
  }

  /**
   * @param rating
   *          the rating to set
   */
  public void setRating(int rating) {
    this.rating = rating;
  }

  /**
   * @return the content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * @param content
   *          the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }

}
