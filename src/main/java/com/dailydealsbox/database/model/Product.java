/**
 *
 */
package com.dailydealsbox.database.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product")
@SQLDelete(sql = "update product set deleted = 1 where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product extends BaseEntityModel {

  @NotNull
  @Size(min = 10, max = 512)
  @Column(name = "url", nullable = false, length = 512)
  private String url;

  @NotNull
  @Column(name = "`key`", nullable = false, length = 64)
  private String key;

  @NotNull
  @Column(name = "disabled", nullable = false)
  private boolean disabled;

  @Column(name = "expired_at", nullable = true)
  private Date expiredAt;

  @Column(name = "activate_at", nullable = true)
  private Date activateAt;

  @Column(name = "add_by", nullable = true)
  private int addBy;

  @NotNull
  @Column(name = "count_likes", nullable = false, insertable = false, updatable = false)
  private int countLikes;

  @NotNull
  @Column(name = "count_reviews", nullable = false, insertable = false, updatable = false)
  private int countReviews;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "store_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Store store;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductText> texts;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductImage> images;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductPrice> prices;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductFee> fees;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductTax> taxes;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductLink> links;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductOption> options;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductTag> tags;

  /**
   * isActive
   *
   * @return
   */
  public boolean isActive() {
    if (this.isDisabled()) { return false; }
    if (this.isDeleted()) { return false; }
    long now = System.currentTimeMillis();
    if (this.getActivateAt() != null && now < this.getActivateAt().getTime()) { return false; }
    if (this.getExpiredAt() != null && now > this.getExpiredAt().getTime()) { return false; }
    return true;
  }

  /**
   * validate
   *
   * @return
   */
  public boolean validate() {
    if (StringUtils.isBlank(this.getKey())) { return false; }
    if (StringUtils.isBlank(this.getUrl())) { return false; }

    // validate product image
    Iterator<ProductImage> itImages = this.getImages().iterator();
    while (itImages.hasNext()) {
      ProductImage value = itImages.next();
      if (!value.validate()) {
        itImages.remove();
      }
    }

    // validate product price
    Iterator<ProductPrice> itPrices = this.getPrices().iterator();
    while (itPrices.hasNext()) {
      ProductPrice value = itPrices.next();
      if (!value.validate()) {
        itPrices.remove();
      }
    }

    return true;
  }

  /**
   * @return the taxes
   */
  public Set<ProductTax> getTaxes() {
    return this.taxes;
  }

  /**
   * @param taxes
   *          the taxes to set
   */
  public void setTaxes(Set<ProductTax> taxes) {
    this.taxes = taxes;
  }

  /**
   * @return the fees
   */
  public Set<ProductFee> getFees() {
    return this.fees;
  }

  /**
   * @param fees
   *          the fees to set
   */
  public void setFees(Set<ProductFee> fees) {
    this.fees = fees;
  }

  /**
   * @return the prices
   */
  public Set<ProductPrice> getPrices() {
    return this.prices;
  }

  /**
   * @param prices
   *          the prices to set
   */
  public void setPrices(Set<ProductPrice> prices) {
    this.prices = prices;
  }

  /**
   * @return the images
   */
  public Set<ProductImage> getImages() {
    return this.images;
  }

  /**
   * @param images
   *          the images to set
   */
  public void setImages(Set<ProductImage> images) {
    this.images = images;
  }

  /**
   * @return the texts
   */
  public Set<ProductText> getTexts() {
    return this.texts;
  }

  /**
   * @param texts
   *          the texts to set
   */
  public void setTexts(Set<ProductText> texts) {
    this.texts = texts;
  }

  /**
   * @return the links
   */
  public Set<ProductLink> getLinks() {
    return this.links;
  }

  /**
   * @param links
   *          the links to set
   */
  public void setLinks(Set<ProductLink> links) {
    this.links = links;
  }

  /**
   * @return the options
   */
  public Set<ProductOption> getOptions() {
    return this.options;
  }

  /**
   * @param options
   *          the options to set
   */
  public void setOptions(Set<ProductOption> options) {
    this.options = options;
  }

  /**
   * @return the tags
   */
  public Set<ProductTag> getTags() {
    return this.tags;
  }

  /**
   * @param tags
   *          the tags to set
   */
  public void setTags(Set<ProductTag> tags) {
    this.tags = tags;
  }

  /**
   * @return the store
   */
  public Store getStore() {
    return this.store;
  }

  /**
   * @param store
   *          the store to set
   */
  public void setStore(Store store) {
    this.store = store;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the key
   */
  public String getKey() {
    return this.key;
  }

  /**
   * @param key
   *          the key to set
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * @return the expiredAt
   */
  public Date getExpiredAt() {
    return this.expiredAt;
  }

  /**
   * @return the countLikes
   */
  public int getCountLikes() {
    return this.countLikes;
  }

  /**
   * @param countLikes
   *          the countLikes to set
   */
  public void setCountLikes(int countLikes) {
    this.countLikes = countLikes;
  }

  /**
   * @return the countReviews
   */
  public int getCountReviews() {
    return this.countReviews;
  }

  /**
   * @param countReviews
   *          the countReviews to set
   */
  public void setCountReviews(int countReviews) {
    this.countReviews = countReviews;
  }

  /**
   * @param expiredAt
   *          the expiredAt to set
   */
  public void setExpiredAt(Date expiredAt) {
    this.expiredAt = expiredAt;
  }

  /**
   * @return the disabled
   */
  public boolean isDisabled() {
    return this.disabled;
  }

  /**
   * @param disabled
   *          the disabled to set
   */
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  /**
   * @return the activateAt
   */
  public Date getActivateAt() {
    return this.activateAt;
  }

  /**
   * @param activateAt
   *          the activateAt to set
   */
  public void setActivateAt(Date activateAt) {
    this.activateAt = activateAt;
  }

  /**
   * @return the addBy
   */
  public int getAddBy() {
    return this.addBy;
  }

  /**
   * @param addBy
   *          the addBy to set
   */
  public void setAddBy(int addBy) {
    this.addBy = addBy;
  }

  /**
   * setProductForChildren
   */
  public void setProductForChildren() {
    for (ProductFee o : this.getFees()) {
      o.setProduct(this);
    }
    for (ProductImage o : this.getImages()) {
      o.setProduct(this);
    }
    for (ProductText o : this.getTexts()) {
      o.setProduct(this);
    }
    for (ProductPrice o : this.getPrices()) {
      o.setProduct(this);
    }
    for (ProductTax o : this.getTaxes()) {
      o.setProduct(this);
    }
    for (ProductLink o : this.getLinks()) {
      o.setProduct(this);
    }
    for (ProductOption o : this.getOptions()) {
      o.setProduct(this);
    }
    for (ProductTag o : this.getTags()) {
      o.setProduct(this);
    }
  }
}
