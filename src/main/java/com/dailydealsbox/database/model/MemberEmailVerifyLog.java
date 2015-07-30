/**
 *
 */
package com.dailydealsbox.database.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "member")
@SQLDelete(sql = "update member_email_verify_log set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MemberEmailVerifyLog extends BaseEntityModel {

  //  @NotNull
  //  @Column(name = "email_id", nullable = false)
  //  private int emailId;

  @NotNull
  @Column(name = "hash_code", nullable = false)
  private String hashCode;

  @Column(name = "ip")
  private String ip = null;

  @Column(name = "verified_at")
  private Date verifiedAt = null;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "email_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private MemberEmail email;

  /**
   * @return the email
   */
  public MemberEmail getEmail() {
    return this.email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(MemberEmail email) {
    this.email = email;
  }

  /**
   * @return the hashCode
   */
  public String getHashCode() {
    return this.hashCode;
  }

  /**
   * @param hashCode
   *          the hashCode to set
   */
  public void setHashCode(String hashCode) {
    this.hashCode = hashCode;
  }

  /**
   * @return the ip
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * @param ip
   *          the ip to set
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * @return the verifiedAt
   */
  public Date getVerifiedAt() {
    return this.verifiedAt;
  }

  /**
   * @param verifiedAt
   *          the verifiedAt to set
   */
  public void setVerifiedAt(Date verifiedAt) {
    this.verifiedAt = verifiedAt;
  }

}
