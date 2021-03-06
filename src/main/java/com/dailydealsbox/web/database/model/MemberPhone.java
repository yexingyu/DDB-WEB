/**
 *
 */
package com.dailydealsbox.web.database.model;

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

import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_PHONE_TYPE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "member_phone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPhone extends BaseModel {

  @NotNull
  @Column(name = "country_code", nullable = false, length = 10)
  private String countryCode;

  @NotNull
  @Column(name = "phone_number", nullable = false, length = 50)
  private String phoneNumber;

  @NotNull
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private MEMBER_PHONE_TYPE type;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  /**
   * validate
   *
   * @return
   */
  public boolean validate() {
    if (StringUtils.isBlank(this.getCountryCode())) {
      this.setCountryCode("+1");
    }
    if (StringUtils.isBlank(this.getPhoneNumber())) { return false; }
    return true;
  }

  /**
   * @return the member
   */
  public Member getMember() {
    return this.member;
  }

  /**
   * @param member
   *          the member to set
   */
  public void setMember(Member member) {
    this.member = member;
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
  public MEMBER_PHONE_TYPE getType() {
    return this.type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(MEMBER_PHONE_TYPE type) {
    this.type = type;
  }

}
