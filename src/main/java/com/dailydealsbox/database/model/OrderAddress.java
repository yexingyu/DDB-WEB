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

import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ADDRESS_COUNTRY;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ADDRESS_TYPE;
import com.dailydealsbox.database.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author x_ye
 */
@Entity
@Table(name = "order_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderAddress extends BaseModel {

  @NotNull
  @Column(name = "order_id", nullable = false)
  private int                    orderId;

  @NotNull
  @Column(name = "address1", nullable = false, length = 255)
  private String                 address1;

  @NotNull
  @Column(name = "address2", nullable = false, length = 255)
  private String                 address2;

  @NotNull
  @Column(name = "city", nullable = false, length = 255)
  private String                 city;

  @NotNull
  @Column(name = "region", nullable = false, length = 255)
  private String                 region;

  @NotNull
  @Column(name = "country", nullable = false)
  @Enumerated(EnumType.STRING)
  private MEMBER_ADDRESS_COUNTRY country;

  @NotNull
  @Column(name = "post_code", nullable = false, length = 50)
  private String                 postCode;

  @NotNull
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private MEMBER_ADDRESS_TYPE    type;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", insertable = false, updatable = false)
  private Order                  order;

  public boolean validate() {
    if (StringUtils.isBlank(this.getAddress1())) {
      this.setAddress1("");
    }
    if (StringUtils.isBlank(this.getAddress2())) {
      this.setAddress2("");
    }
    if (StringUtils.isBlank(this.getCity())) { return false; }
    if (StringUtils.isBlank(this.getPostCode())) { return false; }
    if (StringUtils.isBlank(this.getRegion())) { return false; }
    if (this.getOrderId() <= 0) { return false; }
    return true;
  }

  /**
   * @return the type
   */
  public MEMBER_ADDRESS_TYPE getType() {
    return this.type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(MEMBER_ADDRESS_TYPE type) {
    this.type = type;
  }

  /**
   * @return the orderId
   */
  public int getOrderId() {
    return this.orderId;
  }

  /**
   * @param orderId
   *          the orderId to set
   */
  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  /**
   * @return the order
   */
  public Order getOrder() {
    return this.order;
  }

  /**
   * @param order
   *          the order to set
   */
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * @return the address1
   */
  public String getAddress1() {
    return this.address1;
  }

  /**
   * @param address1
   *          the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  /**
   * @return the address2
   */
  public String getAddress2() {
    return this.address2;
  }

  /**
   * @param address2
   *          the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return this.city;
  }

  /**
   * @param city
   *          the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the region
   */
  public String getRegion() {
    return this.region;
  }

  /**
   * @param region
   *          the region to set
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * @return the country
   */
  public MEMBER_ADDRESS_COUNTRY getCountry() {
    return this.country;
  }

  /**
   * @param country
   *          the country to set
   */
  public void setCountry(MEMBER_ADDRESS_COUNTRY country) {
    this.country = country;
  }

  /**
   * @return the postCode
   */
  public String getPostCode() {
    return this.postCode;
  }

  /**
   * @param postCode
   *          the postCode to set
   */
  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

}
