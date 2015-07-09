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
@Table(name = "product_validation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductValidation extends BaseModel {

  @NotNull
  @Column(name = "start_time", nullable = false, length = 256)
  private String  start_time;

  @NotNull
  @Column(name = "end_time", nullable = false)
  private String  end_time;

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
    if (StringUtils.isBlank(this.getStartTime())) { return false; }
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
   * @return the start_time
   */
  public String getStartTime() {
    return this.start_time;
  }

  /**
   * @param start_time
   *          the start_time to set
   */
  public void setStartTime(String start_time) {
    this.start_time = start_time;
  }

  /**
   * @return the end_time
   */
  public String getEndTime() {
    return this.end_time;
  }

  /**
   * @param end_time
   *          the end_time to set
   */
  public void setEndTime(String end_time) {
    this.end_time = end_time;
  }

}
