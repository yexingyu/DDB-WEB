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
@Table(name = "member_phones")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MemberPhone extends BaseModel {

  @NotNull
  @Column(name = "member_id", nullable = false)
  private int    memberId;

  @NotNull
  @Column(name = "country_code", nullable = false, length = 10)
  private String countryCode;

  @NotNull
  @Column(name = "phone_number", nullable = false, length = 50)
  private String phoneNumber;

  @NotNull
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private TYPE   type;

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
   * @return the countryCode
   */
  public String getCountryCode() {
    return this.countryCode;
  }

  /**
   * @param countryCode
   *          the countryCode to set
   */
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * @param phoneNumber
   *          the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

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

  public static enum TYPE {
    MOBILE, HOME, WORK, FAX
  }
}
