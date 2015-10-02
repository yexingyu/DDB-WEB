/**
 *
 */
package com.dailydealsbox.web.database.model;

import java.security.MessageDigest;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.primitives.Longs;

/**
 * @author x_ye
 */
@Entity
@Table(name = "member_email")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberEmail extends BaseModel {

  @NotNull
  @Column(name = "email")
  private String email;

  @NotNull
  @Column(name = "`primary`")
  private boolean primary;

  @NotNull
  @Column(name = "verified")
  private boolean verified;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @NotNull
  @Column(name = "hash_code")
  private String hashCode;

  @Column(name = "verified_at")
  private Date verifiedAt = null;

  @Column(name = "verified_ip")
  private String verifiedIp = null;

  /**
   * validate
   *
   * @return
   */
  public boolean validate() {
    if (StringUtils.isBlank(this.getEmail())) { return false; }
    return true;
  }

  /**
   * generateHashCode
   */
  public void generateHashCode() {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    md.update(this.getEmail().getBytes());
    md.update(Longs.toByteArray(System.currentTimeMillis()));
    this.setHashCode(new String(Hex.encodeHex(md.digest())));
  }

  /**
   * @return the primary
   */
  public boolean isPrimary() {
    return this.primary;
  }

  /**
   * @param primary the primary to set
   */
  public void setPrimary(boolean primary) {
    this.primary = primary;
  }

  /**
   * @return the hashCode
   */
  public String getHashCode() {
    return this.hashCode;
  }

  /**
   * @param hashCode the hashCode to set
   */
  public void setHashCode(String hashCode) {
    this.hashCode = hashCode;
  }

  /**
   * @return the verifiedAt
   */
  public Date getVerifiedAt() {
    return this.verifiedAt;
  }

  /**
   * @param verifiedAt the verifiedAt to set
   */
  public void setVerifiedAt(Date verifiedAt) {
    this.verifiedAt = verifiedAt;
  }

  /**
   * @return the verifiedIp
   */
  public String getVerifiedIp() {
    return this.verifiedIp;
  }

  /**
   * @param verifiedIp the verifiedIp to set
   */
  public void setVerifiedIp(String verifiedIp) {
    this.verifiedIp = verifiedIp;
  }

  /**
   * @return the verified
   */
  public boolean isVerified() {
    return this.verified;
  }

  /**
   * @param verified the verified to set
   */
  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  /**
   * @return the member
   */
  public Member getMember() {
    return this.member;
  }

  /**
   * @param member the member to set
   */
  public void setMember(Member member) {
    this.member = member;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

}
