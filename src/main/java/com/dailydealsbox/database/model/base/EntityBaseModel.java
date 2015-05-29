/**
 * 
 */
package com.dailydealsbox.database.model.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @author x_ye
 */
@MappedSuperclass
public abstract class EntityBaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int    id;

  @NotNull
  @Column(name = "status", nullable = false)
  private int    status;

  @Column(name = "stamp_joined", nullable = false, updatable = false, insertable = false)
  private String stampJoined;

  @Column(name = "stamp_modified", nullable = false, updatable = false, insertable = false)
  private String stampModified;

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
   * @return the status
   */
  public int getStatus() {
    return this.status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(int status) {
    this.status = status;
  }

}
