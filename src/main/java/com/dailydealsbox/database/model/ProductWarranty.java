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
@Table(name = "product_warranty")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductWarranty extends BaseModel {

  @NotNull
  @Column(name = "warranty_info", nullable = false, length = 256)
  private String  warranty_info;

  @NotNull
  @Column(name = "return_policy", nullable = false)
  private String  return_policy;

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
    if (StringUtils.isBlank(this.getWarrantyInfo())) { return false; }
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
   * @return the warranty_info
   */
  public String getWarrantyInfo() {
    return this.warranty_info;
  }

  /**
   * @param warranty_info
   *          the warranty_info to set
   */
  public void setWarrantyInfo(String warranty_info) {
    this.warranty_info = warranty_info;
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

}
