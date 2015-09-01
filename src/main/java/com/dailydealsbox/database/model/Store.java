/**
 *
 */
package com.dailydealsbox.database.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import com.dailydealsbox.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.configuration.BaseEnum.STORE_CATEGORY;
import com.dailydealsbox.configuration.BaseEnum.STORE_TYPE;
import com.dailydealsbox.database.model.base.BaseEntityModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "store")
@SQLDelete(sql = "update store set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Store extends BaseEntityModel {

  @NotNull
  @Size(min = 4, max = 100)
  @Column(name = "name")
  private String name;

  @NotNull
  @Column(name = "category")
  @Enumerated(EnumType.STRING)
  private STORE_CATEGORY category;

  @NotNull
  @Column(name = "description")
  private String description;

  @NotNull
  @Column(name = "count_followings")
  private int countFollowings;

  @NotNull
  @Column(name = "count_likes")
  private int countLikes;

  @NotNull
  @Column(name = "website")
  private String website;

  @NotNull
  @Column(name = "deal_page")
  private String dealPage;

  @NotNull
  @Column(name = "country")
  @Enumerated(EnumType.STRING)
  private COUNTRY country;

  @NotNull
  @Column(name = "province")
  private String province;

  @NotNull
  @Column(name = "logo")
  private String logo;

  @Column(name = "favicon")
  private String favicon;

  @NotNull
  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private STORE_TYPE type;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "store", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<StoreLocation> locations;

  @NotNull
  @Column(name = "default_followed")
  private boolean defaultFollowed;

  @JsonIgnore
  @ManyToMany(mappedBy = "stores", fetch = FetchType.LAZY)
  private Set<Member> members;

  @NotNull
  @Column(name = "verified")
  private boolean verified;

  @Column(name = "add_by")
  private int addBy;

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
   * @return the locations
   */
  public Set<StoreLocation> getLocations() {
    return this.locations;
  }

  /**
   * @param locations
   *          the locations to set
   */
  public void setLocations(Set<StoreLocation> locations) {
    this.locations = locations;
  }

  /**
   * @return the verified
   */
  public boolean isVerified() {
    return this.verified;
  }

  /**
   * @param verified
   *          the verified to set
   */
  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  /**
   * @return the defaultFollowed
   */
  public boolean isDefaultFollowed() {
    return this.defaultFollowed;
  }

  /**
   * @param defaultFollowed
   *          the defaultFollowed to set
   */
  public void setDefaultFollowed(boolean defaultFollowed) {
    this.defaultFollowed = defaultFollowed;
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
   * @param store
   *          category
   *          the store category to set
   */
  public void setCategory(STORE_CATEGORY category) {
    this.category = category;
  }

  /**
   * @return the store category
   */
  public STORE_CATEGORY getCategory() {
    return this.category;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the countFollowings
   */
  public int getCountFollowings() {
    return this.countFollowings;
  }

  /**
   * @param countFollowings
   *          the countFollowings to set
   */
  public void setCountFollowings(int countFollowings) {
    this.countFollowings = countFollowings;
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
   * @return the favicon
   */
  public String getFavicon() {
    return this.favicon;
  }

  /**
   * @param favicon
   *          the favicon to set
   */
  public void setFavicon(String favicon) {
    this.favicon = favicon;
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

  /**
   * @return the country
   */
  public COUNTRY getCountry() {
    return this.country;
  }

  /**
   * @param country
   *          the country to set
   */
  public void setCountry(COUNTRY country) {
    this.country = country;
  }

  /**
   * @return the members
   */
  public Set<Member> getMembers() {
    return this.members;
  }

  /**
   * @param members
   *          the members to set
   */
  public void setMembers(Set<Member> members) {
    this.members = members;
  }

  /**
   * @return the province
   */
  public String getProvince() {
    return this.province;
  }

  /**
   * @param province
   *          the province to set
   */
  public void setProvince(String province) {
    this.province = province;
  }
}
