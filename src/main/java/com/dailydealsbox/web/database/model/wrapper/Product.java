/**
 *
 */
package com.dailydealsbox.web.database.model.wrapper;

import java.util.Date;
import java.util.Set;

import com.dailydealsbox.web.configuration.BaseEnum.CURRENCY;
import com.dailydealsbox.web.database.model.Poster;
import com.dailydealsbox.web.database.model.ProductFee;
import com.dailydealsbox.web.database.model.ProductImage;
import com.dailydealsbox.web.database.model.ProductLink;
import com.dailydealsbox.web.database.model.ProductOption;
import com.dailydealsbox.web.database.model.ProductPrice;
import com.dailydealsbox.web.database.model.ProductTag;
import com.dailydealsbox.web.database.model.ProductTax;
import com.dailydealsbox.web.database.model.ProductText;
import com.dailydealsbox.web.database.model.Store;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

  private String             url;
  private String             key;
  private double             currentPrice;
  private CURRENCY           currency;
  private boolean            disabled;
  private Date               expiredAt;
  private Date               activateAt;
  private int                addBy;
  private String             addByName;
  private int                countLikes;
  private int                countReviews;
  private double             reputation;
  private Store              store;
  private Poster             poster;
  private Set<ProductText>   texts;
  private Set<ProductImage>  images;
  private Set<ProductPrice>  prices;
  private Set<ProductFee>    fees;
  private Set<ProductTax>    taxes;
  private Set<ProductLink>   links;
  private Set<ProductOption> options;
  private Set<ProductTag>    tags;

  /**
   * keepPrecision
   *
   * @param d
   * @return
   */
  //  private double precision(double d) {
  //    return ((double) Math.round(d * 100)) / 100;
  //  }

  /**
   * computeReputation
   *
   * @return
   */
  //  public void computeReputation() {
  //    double reputation = 0;
  //    if (this.getLinks() != null) {
  //      for (ProductLink link : this.getLinks()) {
  //        double r = link.getRating() * link.getReviewNumber();
  //        reputation = r > reputation ? r : reputation;
  //      }
  //    }
  //    this.setReputation(reputation);
  //  }

  /**
   * isActive
   *
   * @return
   */
  //  public boolean isActive() {
  //    if (this.isDisabled()) { return false; }
  //    if (this.isDeleted()) { return false; }
  //    long now = System.currentTimeMillis();
  //    if (this.getActivateAt() != null && now < this.getActivateAt().getTime()) { return false; }
  //    if (this.getExpiredAt() != null && now > this.getExpiredAt().getTime()) { return false; }
  //    return true;
  //  }

  /**
   * validate
   *
   * @return
   */
  //  public boolean validate() {
  //    if (StringUtils.isBlank(this.getKey())) { return false; }
  //    if (StringUtils.isBlank(this.getUrl())) { return false; }
  //
  //    return true;
  //  }

}
