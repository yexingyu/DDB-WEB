/**
 * 
 */
package com.dailydealsbox.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author x_ye
 */
@Entity
@Table(name = "accounts")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int    id;

  @NotNull
  @Size(min = 3, max = 45)
  @Column(name = "account", nullable = false, length = 45)
  private String account;

  @Column(name = "stamp_joined", nullable = false, updatable = false, insertable = false)
  private String stampJoined;

  @Column(name = "stamp_modified", nullable = false, updatable = false, insertable = false)
  private String stampModified;

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(int id) {
    this.id = id;
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
