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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dailydealsbox.database.model.base.BaseEnum.LANGUAGE;
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
  @Column(name = "name", nullable = false, length = 256)
  private String   name;
  
  @NotNull
  @Column(name = "description", nullable = false, length = 256)
  private String   description;
  
  @NotNull
  @Column(name = "warranty", nullable = false, length = 1024)
  private String   warranty;
  
  @NotNull
  @Column(name = "return_policy", nullable = false, length = 1024)
  private String   return_policy;
  
  @NotNull
  @Column(name = "meta_title", nullable = false, length = 128)
  private String   meta_title;
  
  @NotNull
  @Column(name = "meta_keyword", nullable = false, length = 64)
  private String   meta_keyword;
  
  @NotNull
  @Column(name = "meta_desciption", nullable = false, length = 512)
  private String   meta_desciption;
  

  @NotNull
  @Column(name = "language", nullable = false)
  @Enumerated(EnumType.STRING)
  private LANGUAGE language;

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
    return this.return_policy;
  }

  /**
   * @param return_policy
   *          the return_policy to set
   */
  public void setReturnPolicy(String return_policy) {
    this.return_policy = return_policy;
  }
  
  
  /**
   * @return the meta_title
   */
  public String getMetaTitle() {
    return this.meta_title;
  }

  /**
   * @param meta_title
   *          the meta_title to set
   */
  public void setMetaTitle(String meta_title) {
    this.meta_title = meta_title;
  }
  
  /**
   * @return the meta_keyword
   */
  public String getMetaKeyword() {
    return this.meta_keyword;
  }

  /**
   * @param meta_keyword
   *          the meta_keyword to set
   */
  public void setMetaKeyword(String meta_keyword) {
    this.meta_keyword = meta_keyword;
  }
  
  /**
   * @return the meta_desciption
   */
  public String getMetaDesciption() {
    return this.meta_desciption;
  }

  /**
   * @param meta_desciption
   *          the meta_desciption to set
   */
  public void setMetaDesciption(String meta_desciption) {
    this.meta_desciption = meta_desciption;
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
