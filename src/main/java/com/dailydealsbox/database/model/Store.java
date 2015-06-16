/**
 * 
 */
package com.dailydealsbox.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Store extends BaseEntityModel {

  @NotNull
  @Size(min = 10, max = 100)
  @Column(name = "name", nullable = false, length = 45)
  private String name;

  @NotNull
  @Column(name = "logo", nullable = false, length = 255)
  private String logo;

  @NotNull
  @Column(name = "website", nullable = false, length = 255)
  private String website;

  @NotNull
  @Column(name = "deal_page", nullable = false, length = 512)
  private String dealPage;

  /**
   * validate
   * 
   * @return
   */
  public boolean validate() {
    if (StringUtils.isBlank(this.getName())) { return false; }
    if (StringUtils.isBlank(this.getLogo())) { return false; }
    if (StringUtils.isBlank(this.getWebsite())) { return false; }
    if (StringUtils.isBlank(this.getDealPage())) {
      this.setDealPage("");
    }

    return true;
  }

  /**
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the logo
   */
  public String getLogo() {
    return this.logo;
  }

  /**
   * @param logo
   *          the logo to set
   */
  public void setLogo(String logo) {
    this.logo = logo;
  }

  /**
   * @return the website
   */
  public String getWebsite() {
    return this.website;
  }

  /**
   * @param website
   *          the website to set
   */
  public void setWebsite(String website) {
    this.website = website;
  }

  /**
   * @return the dealPage
   */
  public String getDealPage() {
    return this.dealPage;
  }

  /**
   * @param dealPage
   *          the dealPage to set
   */
  public void setDealPage(String dealPage) {
    this.dealPage = dealPage;
  }

}
