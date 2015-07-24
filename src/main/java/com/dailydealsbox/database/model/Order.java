/**
 *
 */
package com.dailydealsbox.database.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import com.dailydealsbox.database.model.base.BaseEntityModel;
import com.dailydealsbox.database.model.base.BaseEnum.ORDER_STATUS;

/**
 * @author x_ye
 */
@Entity
@Table(name = "`order`")
@SQLDelete(sql = "update `order` set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order extends BaseEntityModel {

  @NotNull
  @Column(name = "product_id", nullable = false)
  private int productId;

  @NotNull
  @Column(name = "member_id", nullable = false)
  private int memberId;

  @NotNull
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ORDER_STATUS status;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = { CascadeType.ALL }, orphanRemoval = true)
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
   * @return the status
   */
  public ORDER_STATUS getStatus() {
    return this.status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(ORDER_STATUS status) {
    this.status = status;
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
