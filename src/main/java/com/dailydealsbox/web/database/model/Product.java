/**
 *
 */
package com.dailydealsbox.web.database.model;

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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import com.dailydealsbox.web.configuration.BaseEnum;
import com.dailydealsbox.web.configuration.BaseEnum.CURRENCY;
import com.dailydealsbox.web.configuration.GenericConfiguration;
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
  @Column(name = "url")
  private String url;

  @NotNull
  @Column(name = "`key`")
  private String key;

  @NotNull
  @Column(name = "current_price")
  private double currentPrice;

  @NotNull
  @Column(name = "currency")
  @Enumerated(EnumType.STRING)
  private CURRENCY currency;

  @NotNull
  @Column(name = "disabled")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private boolean disabled;

  @NotNull
  @Column(name = "count_likes", insertable = false, updatable = false)
  private int countLikes;

  @NotNull
  @Column(name = "count_unlikes", insertable = false, updatable = false)
  private int countUnlikes;

  @NotNull
  @Column(name = "count_reviews", insertable = false, updatable = false)
  private int countReviews;

  @NotNull
  @Column(name = "reputation")
  private double reputation;

  @NotNull
  @Column(name = "soldout")
  @Type(type = "org.hibernate.type.NumericBooleanType")
  private boolean soldout;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "store_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Store store;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "poster_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Poster poster;

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
   * @param reputation the reputation to set
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
   * @param currentPrice the currentPrice to set
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
   * @param currency the currency to set
   */
  public void setCurrency(CURRENCY currency) {
    this.currency = currency;
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
   * @param taxes the taxes to set
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
   * @param fees the fees to set
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
   * @param prices the prices to set
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
   * @param images the images to set
   */
  public void setImages(Set<ProductImage> images) {
    this.images = images;
  }

  /**
   * @return the soldout
   */
  public boolean isSoldout() {
    return this.soldout;
  }

  /**
   * @param soldout the soldout to set
   */
  public void setSoldout(boolean soldout) {
    this.soldout = soldout;
  }

  /**
   * @return the texts
   */
  public Set<ProductText> getTexts() {
    return this.texts;
  }

  /**
   * @param texts the texts to set
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
   * @param links the links to set
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
   * @param options the options to set
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
   * @param tags the tags to set
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
   * @param store the store to set
   */
  public void setStore(Store store) {
    this.store = store;
  }

  /**
   * @return the poster
   */
  public Poster getPoster() {
    return this.poster;
  }

  /**
   * @param store the store to set
   */
  public void setPoster(Poster poster) {
    this.poster = poster;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * @param url the url to set
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
   * @param key the key to set
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * @return the countLikes
   */
  public int getCountLikes() {
    return this.countLikes;
  }

  /**
   * @param countLikes the countLikes to set
   */
  public void setCountLikes(int countLikes) {
    this.countLikes = countLikes;
  }

  /**
   * @return the countUnlikes
   */
  public int getCountUnlikes() {
    return this.countUnlikes;
  }

  /**
   * @param countUnlikes the countUnlikes to set
   */
  public void setCountUnlikes(int countUnlikes) {
    this.countUnlikes = countUnlikes;
  }

  /**
   * @return the countReviews
   */
  public int getCountReviews() {
    return this.countReviews;
  }

  /**
   * @param countReviews the countReviews to set
   */
  public void setCountReviews(int countReviews) {
    this.countReviews = countReviews;
  }

  /**
   * @return the disabled
   */
  public boolean isDisabled() {
    return this.disabled;
  }

  /**
   * @param disabled the disabled to set
   */
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

}
