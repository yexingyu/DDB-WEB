/**
 * 
 */
package com.dailydealsbox.database.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * @author x_ye
 */
@MappedSuperclass
public abstract class BaseEntityModel extends BaseModel {

  @NotNull
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private STATUS status;

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private Date   createdAt = new Date();

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "modified_at", nullable = false, updatable = false, insertable = false)
  private Date   modifiedAt;

  /**
   * @return the createdAt
   */
  public Date getCreatedAt() {
    return this.createdAt;
  }

  /**
   * @param createdAt
   *          the createdAt to set
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * @return the modifiedAt
   */
  public Date getModifiedAt() {
    return this.modifiedAt;
  }

  /**
   * @param modifiedAt
   *          the modifiedAt to set
   */
  public void setModifiedAt(Date modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * @return the status
   */
  public STATUS getStatus() {
    return this.status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(STATUS status) {
    this.status = status;
  }

  /**
   * @author x_ye
   */
  public static enum STATUS {
    AVAILABLE, UNAVAILABLE, DELETED
  }

}
