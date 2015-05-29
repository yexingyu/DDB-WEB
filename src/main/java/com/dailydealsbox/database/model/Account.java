/**
 * 
 */
package com.dailydealsbox.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dailydealsbox.database.model.base.EntityBaseModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "accounts")
public class Account extends EntityBaseModel {

  @NotNull
  @Size(min = 3, max = 45)
  @Column(name = "account", nullable = false, length = 45)
  private String account;

  @NotNull
  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @Column(name = "stamp_joined", nullable = false, updatable = false, insertable = false)
  private String stampJoined;

  @Column(name = "stamp_modified", nullable = false, updatable = false, insertable = false)
  private String stampModified;

  /**
   * @return the password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the account
   */
  public String getAccount() {
    return this.account;
  }

  /**
   * @param account
   *          the account to set
   */
  public void setAccount(String account) {
    this.account = account;
  }

  /**
   * @return the stampJoined
   */
  public String getStampJoined() {
    return this.stampJoined;
  }

  /**
   * @param stampJoined
   *          the stampJoined to set
   */
  public void setStampJoined(String stampJoined) {
    this.stampJoined = stampJoined;
  }

  /**
   * @return the stampModified
   */
  public String getStampModified() {
    return this.stampModified;
  }

  /**
   * @param stampModified
   *          the stampModified to set
   */
  public void setStampModified(String stampModified) {
    this.stampModified = stampModified;
  }

}
