/**
 *
 */
package com.dailydealsbox.web.database.model;

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

import com.dailydealsbox.web.configuration.BaseEnum.PRODUCT_FEE_TITLE;
import com.dailydealsbox.web.configuration.BaseEnum.PRODUCT_FEE_TYPE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_fee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductFee extends BaseModel {

  @NotNull
  @Column(name = "value", nullable = false)
  private double value;

  @NotNull
  @Column(name = "title", nullable = false)
  @Enumerated(EnumType.STRING)
  private PRODUCT_FEE_TITLE title;

  @NotNull
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private PRODUCT_FEE_TYPE type;

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
    if (this.getValue() <= 0) { return false; }
    return true;
  }

  /**
   * @return the title
   */
  public PRODUCT_FEE_TITLE getTitle() {
    return this.title;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(PRODUCT_FEE_TITLE title) {
    this.title = title;
  }

  /**
   * @return the type
   */
  public PRODUCT_FEE_TYPE getType() {
    return this.type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(PRODUCT_FEE_TYPE type) {
    this.type = type;
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
  public double getValue() {
    return this.value;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(double value) {
    this.value = value;
  }

}
