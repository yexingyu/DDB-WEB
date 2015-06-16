/**
 * 
 */
package com.dailydealsbox.database.model;

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

import com.dailydealsbox.database.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductImage extends BaseModel {

  @NotNull
  @Column(name = "product_id", nullable = false)
  private int     productId;

  @NotNull
  @Column(name = "url", nullable = false, length = 512)
  private String  url;

  @NotNull
  @Column(name = "alt", nullable = false, length = 256)
  private String  alt;

  @NotNull
  @Column(name = "width", nullable = false)
  private int     width;

  @NotNull
  @Column(name = "height", nullable = false)
  private int     height;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product product;

  /**
   * validate
   * 
   * @return
   */
  public boolean validate() {
    if (StringUtils.isBlank(this.getUrl())) { return false; }
    if (StringUtils.isBlank(this.getAlt())) {
      this.setAlt("");
    }
    if (this.getWidth() <= 0) {
      this.setWidth(50);
    }
    if (this.getHeight() <= 0) {
      this.setHeight(50);
    }
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
   * @return the alt
   */
  public String getAlt() {
    return this.alt;
  }

  /**
   * @param alt
   *          the alt to set
   */
  public void setAlt(String alt) {
    this.alt = alt;
  }

  /**
   * @return the width
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * @param width
   *          the width to set
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * @param height
   *          the height to set
   */
  public void setHeight(int height) {
    this.height = height;
  }

}
