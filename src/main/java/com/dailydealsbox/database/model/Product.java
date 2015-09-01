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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import com.dailydealsbox.configuration.BaseEnum;
import com.dailydealsbox.configuration.BaseEnum.CURRENCY;
import com.dailydealsbox.configuration.GenericConfiguration;
import com.dailydealsbox.database.model.base.BaseEntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "product")
@SQLDelete(sql = "update product set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product extends BaseEntityModel {

  @NotNull
  @Size(min = 10, max = 512)
  @Column(name = "url", nullable = false, length = 512)
  private String url;

  @NotNull
  @Column(name = "`key`", nullable = false, length = 64)
  private String key;

  @NotNull
  @Column(name = "current_price", nullable = false)
  private double currentPrice;

  @NotNull
  @Column(name = "currency", nullable = false)
  @Enumerated(EnumType.STRING)
  private CURRENCY currency;

  @NotNull
  @Column(name = "disabled", nullable = false)
  private boolean disabled;

  @Column(name = "expired_at", nullable = true)
  private Date expiredAt;

  @Column(name = "activate_at", nullable = true)
  private Date activateAt;

  @Column(name = "add_by", nullable = true)
  private int addBy;

  @Column(name = "add_by_name", nullable = true)
  private String addByName;

  @NotNull
  @Column(name = "count_likes", nullable = false, insertable = false, updatable = false)
  private int countLikes;

  @NotNull
  @Column(name = "count_reviews", nullable = false, insertable = false, updatable = false)
  private int countReviews;

  @NotNull
  @Column(name = "reputation")
  private double reputation;

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

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
  @JoinTable(name = "relation_product_tag", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductTag> tags;

  /**
   * getDealPrice
   *
   * @return
   */
  public double getDealPrice() {
    return this.getCurrentPrice();
  }

  /**
   * getPayments
   *
   * @return
   */
  public int getPayments() {
    return GenericConfiguration.PAYMENTS;
  }

  /**
   * keepPrecision
   *
   * @param d
   * @return
   */
  private double precision(double d) {
    return ((double) Math.round(d * 100)) / 100;
  }

  /**
   * getMonthlyPayment
   *
   * @return
   */
  public double getMonthlyPayment() {
    double mpr = GenericConfiguration.ARP / GenericConfiguration.PAYMENTS;
    double mp = mpr * this.getPrincipal() / (1 - Math.pow((1 + mpr), -GenericConfiguration.PAYMENTS));
    return this.precision(mp);
  }

  /**
   * getPrincipal
   *
   * @return
   */
  public double getPrincipal() {
    return this.precision(this.getTotal() * (1 - GenericConfiguration.DOWNPAYMENT_RATE));
  }

  /**
   * getDownpayment
   *
   * @return
   */
  public double getDownpayment() {
    return this.precision(this.getTotal() * GenericConfiguration.DOWNPAYMENT_RATE);
  }

  /**
   * getTotal
   *
   * @return
   */
  public double getTotal() {
    if (this.getDealPrice() == 0) { return 0; }
    double total = this.getDealPrice();

    // total fee
    if (this.getFees() != null) {
      for (ProductFee fee : this.getFees()) {
        if (fee.getType() == BaseEnum.PRODUCT_FEE_TYPE.AMOUNT) {
          total += fee.getValue();
        } else if (fee.getType() == BaseEnum.PRODUCT_FEE_TYPE.PERCENTAGE) {
          total += this.getDealPrice() * (fee.getValue() / 100);
        }
      }
    }

    // exchange to CAD
    if (this.getCurrency() == BaseEnum.CURRENCY.USD) {
      total *= GenericConfiguration.EXCHANGE_RATE_USD;
    }

    return this.precision(total);
  }

  /**
   * computeReputation
   *
   * @return
   */
  public void computeReputation() {
    double reputation = 0;
    if (this.getLinks() != null) {
      for (ProductLink link : this.getLinks()) {
        double r = link.getRating() * link.getReviewNumber();
        reputation = r > reputation ? r : reputation;
      }
    }
    this.setReputation(reputation);
  }

  /**
   * @return the reputation
   */
  public double getReputation() {
    return this.reputation;
  }

  /**
   * @param reputation
   *          the reputation to set
   */
  public void setReputation(double reputation) {
    this.reputation = reputation;
  }

  /**
   * @return the currentPrice
   */
  public double getCurrentPrice() {
    return this.currentPrice;
  }

  /**
   * @param currentPrice
   *          the currentPrice to set
   */
  public void setCurrentPrice(double currentPrice) {
    this.currentPrice = currentPrice;
  }

  /**
   * @return the currency
   */
  public CURRENCY getCurrency() {
    return this.currency;
  }

  /**
   * @param currency
   *          the currency to set
   */
  public void setCurrency(CURRENCY currency) {
    this.currency = currency;
  }

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
   * @return the addByName
   */
  public String getAddByName() {
    return this.addByName;
  }

  /**
   * @param addByName
   *          the addByName to set
   */
  public void setAddByName(String addByName) {
    this.addByName = addByName;
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

}
