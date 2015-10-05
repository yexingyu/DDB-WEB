/**
 *
 */
package com.dailydealsbox.web.database.model;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product_review")
@SQLDelete(sql = "update product_review set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductReview extends BaseEntityModel {

  @NotNull
  @Column(name = "product_id", nullable = false)
  private int productId;

  @NotNull
  @Column(name = "ip", nullable = false)
  private String ip;

  @NotNull
  @Column(name = "fingerprint", nullable = false)
  private String fingerprint;

  @NotNull
  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "poster_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Poster poster;

  /**
   * @return the poster
   */
  public Poster getPoster() {
    return this.poster;
  }

  /**
   * @param poster the poster to set
   */
  public void setPoster(Poster poster) {
    this.poster = poster;
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
   * @return the content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * @param content the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }

}
