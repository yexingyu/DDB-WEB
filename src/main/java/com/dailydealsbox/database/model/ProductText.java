/**
 * 
 */
package com.dailydealsbox.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dailydealsbox.configuration.BaseEnum.LANGUAGE;
import com.dailydealsbox.database.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_text")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductText extends BaseModel {
  @NotNull
  @Column(name = "language", nullable = false)
  @Enumerated(EnumType.STRING)
  private LANGUAGE language;

  @NotNull
  @Column(name = "name", nullable = false, length = 256)
  private String   name;

  @NotNull
  @Column(name = "description", nullable = false, length = 256)
  private String   description;

  @Column(name = "warranty", length = 1024)
  private String   warranty;

  @Column(name = "return_policy", length = 1024)
  private String   returnPolicy;

  @Column(name = "shipping_info", length = 254)
  private String   shippingInfo;

  @Column(name = "coupon", length = 45)
  private String   coupon;

  @Column(name = "meta_title", length = 128)
  private String   metaTitle;

  @Column(name = "meta_keyword", length = 64)
  private String   metaKeyword;

  @Column(name = "meta_description", length = 512)
  private String   metaDescription;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product  product;

  /**
   * validate
   * 
   * @return
   */
  public boolean validate() {

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
   * @return the description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the warranty
   */
  public String getWarranty() {
    return this.warranty;
  }

  /**
   * @param warranty
   *          the warranty to set
   */
  public void setWarranty(String warranty) {
    this.warranty = warranty;
  }

  /**
   * @return the return_policy
   */
  public String getReturnPolicy() {
    return this.returnPolicy;
  }

  /**
   * @param return_policy
   *          the return_policy to set
   */
  public void setReturnPolicy(String returnPolicy) {
    this.returnPolicy = returnPolicy;
  }

  /**
   * @return the shipping_info
   */
  public String getShippingInfo() {
    return this.shippingInfo;
  }

  /**
   * @param shipping_info
   *          the shipping_info to set
   */
  public void setShippingInfo(String shippingInfo) {
    this.shippingInfo = shippingInfo;
  }

  /**
   * @return the warranty
   */
  public String getCoupon() {
    return this.coupon;
  }

  /**
   * @param warranty
   *          the warranty to set
   */
  public void setCoupon(String coupon) {
    this.coupon = coupon;
  }

  /**
   * @return the meta_title
   */
  public String getMetaTitle() {
    return this.metaTitle;
  }

  /**
   * @param meta_title
   *          the meta_title to set
   */
  public void setMetaTitle(String metaTitle) {
    this.metaTitle = metaTitle;
  }

  /**
   * @return the meta_keyword
   */
  public String getMetaKeyword() {
    return this.metaKeyword;
  }

  /**
   * @param meta_keyword
   *          the meta_keyword to set
   */
  public void setMetaKeyword(String metaKeyword) {
    this.metaKeyword = metaKeyword;
  }

  /**
   * @return the meta_desciption
   */
  public String getMetaDesciption() {
    return this.metaDescription;
  }

  /**
   * @param meta_desciption
   *          the meta_desciption to set
   */
  public void setMetaDescription(String metaDescription) {
    this.metaDescription = metaDescription;
  }

  /**
   * @return the language
   */
  public LANGUAGE getLanguage() {
    return this.language;
  }

  /**
   * @param language
   *          the language to set
   */
  public void setLanguage(LANGUAGE language) {
    this.language = language;
  }

}
