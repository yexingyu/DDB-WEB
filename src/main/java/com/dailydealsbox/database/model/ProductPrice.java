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

import com.dailydealsbox.database.model.Product.CURRENCY;
import com.dailydealsbox.database.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductPrice extends BaseModel {

  @NotNull
  @Column(name = "product_id", nullable = false)
  private int      productId;

  @NotNull
  @Column(name = "value", nullable = false)
  private double   value;

  @NotNull
  @Column(name = "currency", nullable = false)
  @Enumerated(EnumType.STRING)
  private CURRENCY currency;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", insertable = false, updatable = false)
  private Product  product;

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
   * @return the currency
   */
  public CURRENCY getCurrency() {
    return this.currency;
  }

  /**
   * @param currency
   *          the currency to set
   */
  public void setCurrency(CURRENCY currency) {
    this.currency = currency;
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
