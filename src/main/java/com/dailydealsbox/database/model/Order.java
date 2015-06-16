/**
 * 
 */
package com.dailydealsbox.database.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order extends BaseEntityModel {

  @NotNull
  @Column(name = "product_id", nullable = false)
  private int               productId;

  @NotNull
  @Column(name = "member_id", nullable = false)
  private int               memberId;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "order",
    cascade = { javax.persistence.CascadeType.ALL },
    orphanRemoval = true)
  @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<OrderAddress> addresses;

  /**
   * validate
   * 
   * @return
   */
  public boolean validate() {
    return true;
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
   * @return the memberId
   */
  public int getMemberId() {
    return this.memberId;
  }

  /**
   * @param memberId
   *          the memberId to set
   */
  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  /**
   * @return the addresses
   */
  public Set<OrderAddress> getAddresses() {
    return this.addresses;
  }

  /**
   * @param addresses
   *          the addresses to set
   */
  public void setAddresses(Set<OrderAddress> addresses) {
    this.addresses = addresses;
  }

}
