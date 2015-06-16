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

import com.dailydealsbox.database.model.Product.TAX_TITLE;
import com.dailydealsbox.database.model.Product.TAX_TYPE;
import com.dailydealsbox.database.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_tax")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductTax extends BaseModel {

  @NotNull
  @Column(name = "product_id", nullable = false)
  private int       productId;

  @NotNull
  @Column(name = "value", nullable = false)
  private double    value;

  @NotNull
  @Column(name = "title", nullable = false)
  @Enumerated(EnumType.STRING)
  private TAX_TITLE title;

  @NotNull
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private TAX_TYPE  type;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product   product;

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
  public TAX_TITLE getTitle() {
    return this.title;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(TAX_TITLE title) {
    this.title = title;
  }

  /**
   * @return the type
   */
  public TAX_TYPE getType() {
    return this.type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(TAX_TYPE type) {
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
