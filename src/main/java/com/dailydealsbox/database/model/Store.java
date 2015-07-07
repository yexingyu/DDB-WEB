/**
 * 
 */
package com.dailydealsbox.database.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dailydealsbox.database.model.base.BaseEntityModel;
import com.dailydealsbox.database.model.base.BaseEnum.STORE_TYPE;

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
  private String     name;

  @NotNull
  @Column(name = "logo", nullable = false, length = 255)
  private String     logo;

  @NotNull
  @Column(name = "website", nullable = false, length = 255)
  private String     website;

  @NotNull
  @Column(name = "deal_page", nullable = false, length = 512)
  private String     dealPage;

  @NotNull
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private STORE_TYPE type;

  //  @JsonIgnore
  //  @OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
  //  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  //  private Set<Product> products;

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
   * @return the type
   */
  public STORE_TYPE getType() {
    return this.type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(STORE_TYPE type) {
    this.type = type;
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
