/**
 *
 */
package com.dailydealsbox.database.model.base;

import java.util.Date;

import javax.persistence.Column;
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
  @Column(name = "deleted", nullable = false, insertable = false)
  private boolean deleted;

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private Date createdAt = new Date();

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "modified_at", nullable = false, updatable = false, insertable = false)
  private Date modifiedAt = new Date();

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
   * @return the deleted
   */
  public boolean isDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted
   *          the deleted to set
   */
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

}
