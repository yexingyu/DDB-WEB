/**
 *
 */
package com.dailydealsbox.web.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_link")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductLink extends BaseModel {

  @NotNull
  @Column(name = "name", nullable = false, length = 256)
  private String name;

  @NotNull
  @Column(name = "url", nullable = false)
  private String url;

  @NotNull
  @Column(name = "rating", nullable = true)
  private double rating;

  @NotNull
  @Column(name = "review_number", nullable = false)
  private int reviewNumber;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  /**
   * validate
   *
   * @return
   */
  public boolean validate() {
    if (StringUtils.isBlank(this.getName())) { return false; }
    return true;
  }

  /**
   * @return the product
   */
  public Product getProduct() {
    return this.product;
  }

  /**
   * @param product
   *          the product to set
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the rating
   */
  public double getRating() {
    return this.rating;
  }

  /**
   * @param rating
   *          the rating to set
   */
  public void setRating(double rating) {
    this.rating = rating;
  }

  /**
   * @return the reviewNumber
   */
  public int getReviewNumber() {
    return this.reviewNumber;
  }

  /**
   * @param reviewNumber
   *          the review_number to set
   */
  public void setReviewNumber(int reviewNumber) {
    this.reviewNumber = reviewNumber;
  }

}
