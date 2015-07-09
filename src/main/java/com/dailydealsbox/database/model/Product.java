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

import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product extends BaseEntityModel {

  @NotNull
  @Size(min = 10, max = 512)
  @Column(name = "url", nullable = false, length = 512)
  private String                  url;

  @NotNull
  @Column(name = "`key`", nullable = false, length = 64)
  private String                  key;

  @NotNull
  @Column(name = "enable", nullable = false)
  private boolean                 enable;

  @Column(name = "expired_at", nullable = true)
  private Date                    expiredAt;

  @Column(name = "activate_at", nullable = true)
  private Date                    activateAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "store_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Store                   store;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductName>        names;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductDescription> descriptions;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductImage>       images;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductPrice>       prices;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductFee>         fees;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductTax>         taxes;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductLike>        likes;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductLink>        links;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductMeta>        metas;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductOption>      options;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductReview>      reviews;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductTag>         tags;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductValidation>  validations;

  /**
   * isActive
   * 
   * @return
   */
  public boolean isActive() {
    long now = System.currentTimeMillis();
    if (!this.isEnable()) return false;
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

    // validate product name
    Iterator<ProductName> itNames = this.getNames().iterator();
    while (itNames.hasNext()) {
      ProductName name = itNames.next();
      if (!name.validate()) {
        itNames.remove();
      }
    }

    // validate product image
    Iterator<ProductImage> itImages = this.getImages().iterator();
    while (itNames.hasNext()) {
      ProductImage value = itImages.next();
      if (!value.validate()) {
        itImages.remove();
      }
    }

    // validate product price
    Iterator<ProductPrice> itPrices = this.getPrices().iterator();
    while (itNames.hasNext()) {
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
   * @return the descriptions
   */
  public Set<ProductDescription> getDescriptions() {
    return this.descriptions;
  }

  /**
   * @param descriptions
   *          the descriptions to set
   */
  public void setDescriptions(Set<ProductDescription> descriptions) {
    this.descriptions = descriptions;
  }

  /**
   * @return the names
   */
  public Set<ProductName> getNames() {
    return this.names;
  }

  /**
   * @param names
   *          the names to set
   */
  public void setNames(Set<ProductName> names) {
    this.names = names;
  }

  /**
   * @return the likes
   */
  public Set<ProductLike> getLikes() {
    return this.likes;
  }

  /**
   * @param likes
   *          the likes to set
   */
  public void setLikes(Set<ProductLike> likes) {
    this.likes = likes;
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
   * @return the metas
   */
  public Set<ProductMeta> getMetas() {
    return this.metas;
  }

  /**
   * @param metas
   *          the metas to set
   */
  public void setMetas(Set<ProductMeta> metas) {
    this.metas = metas;
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
   * @return the reviews
   */
  public Set<ProductReview> getReviews() {
    return this.reviews;
  }

  /**
   * @param reviews
   *          the reviews to set
   */
  public void setReviews(Set<ProductReview> reviews) {
    this.reviews = reviews;
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
   * @return the validations
   */
  public Set<ProductValidation> getValidations() {
    return this.validations;
  }

  /**
   * @param validations
   *          the validations to set
   */
  public void setValidations(Set<ProductValidation> validations) {
    this.validations = validations;
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
   * @param expiredAt
   *          the expiredAt to set
   */
  public void setExpiredAt(Date expiredAt) {
    this.expiredAt = expiredAt;
  }

  /**
   * @return the enable
   */
  public boolean isEnable() {
    return this.enable;
  }

  /**
   * @param enable
   *          the enable to set
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
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
   * setProductForChildren
   */
  public void setProductForChildren() {
    for (ProductDescription o : this.getDescriptions()) {
      o.setProduct(this);
    }
    for (ProductFee o : this.getFees()) {
      o.setProduct(this);
    }
    for (ProductImage o : this.getImages()) {
      o.setProduct(this);
    }
    for (ProductName o : this.getNames()) {
      o.setProduct(this);
    }
    for (ProductPrice o : this.getPrices()) {
      o.setProduct(this);
    }
    for (ProductTax o : this.getTaxes()) {
      o.setProduct(this);
    }
    for (ProductLike o : this.getLikes()) {
      o.setProduct(this);
    }
    for (ProductLink o : this.getLinks()) {
      o.setProduct(this);
    }
    for (ProductMeta o : this.getMetas()) {
      o.setProduct(this);
    }
    for (ProductOption o : this.getOptions()) {
      o.setProduct(this);
    }
    for (ProductReview o : this.getReviews()) {
      o.setProduct(this);
    }
    for (ProductTag o : this.getTags()) {
      o.setProduct(this);
    }
    for (ProductValidation o : this.getValidations()) {
      o.setProduct(this);
    }
  }
}
