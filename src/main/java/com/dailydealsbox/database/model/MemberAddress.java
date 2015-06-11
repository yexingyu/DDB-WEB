/**
 * 
 */
package com.dailydealsbox.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dailydealsbox.database.model.base.BaseModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "member_addresses")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MemberAddress extends BaseModel {

  @NotNull
  @Column(name = "member_id", nullable = false)
  private int     memberId;

  @NotNull
  @Column(name = "address1", nullable = false, length = 255)
  private String  address1;

  @NotNull
  @Column(name = "address2", nullable = false, length = 255)
  private String  address2;

  @NotNull
  @Column(name = "city", nullable = false, length = 255)
  private String  city;

  @NotNull
  @Column(name = "region", nullable = false, length = 255)
  private String  region;

  @NotNull
  @Column(name = "country", nullable = false)
  @Enumerated(EnumType.STRING)
  private COUNTRY country;

  @NotNull
  @Column(name = "post_code", nullable = false, length = 50)
  private String  postCode;

  @NotNull
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private TYPE    type;

  /**
   * @return the type
   */
  public TYPE getType() {
    return this.type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(TYPE type) {
    this.type = type;
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
  public COUNTRY getCountry() {
    return this.country;
  }

  /**
   * @param country
   *          the country to set
   */
  public void setCountry(COUNTRY country) {
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

  /**
   * @author x_ye
   */
  public static enum COUNTRY {
    CA, US
  }

  /**
   * @author x_ye
   */
  public static enum TYPE {
    BILLING, LIVING
  }
}
