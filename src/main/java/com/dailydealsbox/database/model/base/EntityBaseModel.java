/**
 * 
 */
package com.dailydealsbox.database.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author x_ye
 */
@MappedSuperclass
public abstract class EntityBaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int  id;

  @NotNull
  @Column(name = "status", nullable = false)
  private int  status;

  @Column(name = "stamp_joined", nullable = false, updatable = false)
  private Date stampJoined = new Date();

  @Column(name = "stamp_modified", nullable = false, updatable = false, insertable = false)
  private Date stampModified;

  /**
   * @return the stampJoined
   */
  public Date getStampJoined() {
    return this.stampJoined;
  }

  /**
   * @param stampJoined
   *          the stampJoined to set
   */
  public void setStampJoined(Date stampJoined) {
    this.stampJoined = stampJoined;
  }

  /**
   * @return the stampModified
   */
  public Date getStampModified() {
    return this.stampModified;
  }

  /**
   * @param stampModified
   *          the stampModified to set
   */
  public void setStampModified(Date stampModified) {
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

  /**
   * 
   */
  @Override
  public String toString() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "";
    }
  }

}
