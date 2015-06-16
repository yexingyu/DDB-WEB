/**
 * 
 */
package com.dailydealsbox.database.model;

import java.util.Iterator;
import java.util.Set;

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
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.dailydealsbox.database.model.base.BaseEntityModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product extends BaseEntityModel {

  @NotNull
  @Size(min = 10, max = 100)
  @Column(name = "url", nullable = false, length = 512)
  private String                  url;

  @NotNull
  @Column(name = "key", nullable = false, length = 64)
  private String                  key;

  @NotNull
  @Column(name = "store_id", nullable = false, length = 100)
  private int                     storeId;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id", insertable = false, updatable = false)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Store                   store;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { javax.persistence.CascadeType.ALL },
    orphanRemoval = true)
  @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductName>        names;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { javax.persistence.CascadeType.ALL },
    orphanRemoval = true)
  @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductDescription> descriptions;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { javax.persistence.CascadeType.ALL },
    orphanRemoval = true)
  @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductImage>       images;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { javax.persistence.CascadeType.ALL },
    orphanRemoval = true)
  @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductPrice>       prices;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { javax.persistence.CascadeType.ALL },
    orphanRemoval = true)
  @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductFee>         fees;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "product",
    cascade = { javax.persistence.CascadeType.ALL },
    orphanRemoval = true)
  @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductTax>         taxes;

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
   * @return the storeId
   */
  public int getStoreId() {
    return this.storeId;
  }

  /**
   * @param storeId
   *          the storeId to set
   */
  public void setStoreId(int storeId) {
    this.storeId = storeId;
  }

}
