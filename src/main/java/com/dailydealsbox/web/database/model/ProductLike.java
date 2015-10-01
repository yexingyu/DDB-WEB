/**
 *
 */
package com.dailydealsbox.web.database.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.dailydealsbox.web.database.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_like")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ProductLike extends BaseModel {

  @NotNull
  @Column(name = "fingerprint")
  private String fingerprint;

  @NotNull
  @Column(name = "ip")
  private String ip;

  @NotNull
  @Column(name = "product_id")
  private int productId;

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "created_at", updatable = false, insertable = false)
  private Date createdAt = new Date();

  @NotNull
  @Column(name = "positive")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private boolean positive;

  /**
   * validate
   *
   * @return
   */
  public boolean validate() {
    return true;
  }

  /**
   * @return the fingerprint
   */
  public String getFingerprint() {
    return this.fingerprint;
  }

  /**
   * @param fingerprint the fingerprint to set
   */
  public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
  }

  /**
   * @return the productId
   */
  public int getProductId() {
    return this.productId;
  }

  /**
   * @param productId the productId to set
   */
  public void setProductId(int productId) {
    this.productId = productId;
  }

  /**
   * @return the createdAt
   */
  public Date getCreatedAt() {
    return this.createdAt;
  }

  /**
   * @param createdAt the createdAt to set
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * @return the ip
   */
  public String getIp() {
    return this.ip;
  }

  /**
   * @param ip the ip to set
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * @return the positive
   */
  public boolean isPositive() {
    return this.positive;
  }

  /**
   * @param positive the positive to set
   */
  public void setPositive(boolean positive) {
    this.positive = positive;
  }

}
