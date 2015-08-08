/**
 *
 */
package com.dailydealsbox.database.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
@Table(name = "product_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductTag extends BaseModel {

  @NotNull
  @Column(name = "value", nullable = false)
  private String value;

  @JsonIgnore
  @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Product> products;

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
  public Set<Product> getProducts() {
    return this.products;
  }

  /**
   * @param product
   *          the product to set
   */
  public void setProducts(Set<Product> products) {
    this.products = products;
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

}
