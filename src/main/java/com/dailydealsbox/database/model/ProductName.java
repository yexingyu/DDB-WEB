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
@Table(name = "product_name")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductName extends BaseModel {

  @NotNull
  @Column(name = "value", nullable = false, length = 256)
  private String   value;

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
    if (StringUtils.isBlank(this.getValue())) { return false; }
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
   * @return the value
   */
  public String getValue() {
    return this.value;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(String value) {
    this.value = value;
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
