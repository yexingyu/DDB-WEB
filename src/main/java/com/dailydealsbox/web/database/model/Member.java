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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_LOGIN_TYPE;
import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "member")
@SQLDelete(sql = "update member set deleted = true where id = ?")
@Where(clause = "deleted = 0")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member extends BaseEntityModel {

  @NotNull
  @Size(min = 10, max = 100)
  @Column(name = "account", nullable = false, length = 100)
  private String             account;

  @NotNull
  @Column(name = "password", nullable = false, length = 32)
  private String             password;

  @NotNull
  @Column(name = "first_name", nullable = false, length = 100)
  private String             firstName  = "";

  @NotNull
  @Column(name = "middle_name", nullable = false, length = 100)
  private String             middleName = "";

  @NotNull
  @Column(name = "last_name", nullable = false, length = 100)
  private String             lastName   = "";

  @NotNull
  @Column(name = "nick_name", nullable = false, length = 100)
  private String             nickName   = "";

  @Column(name = "signature", length = 160)
  private String             signature  = "";

  @Column(name = "avatar", length = 45)
  private String             avatar     = "";

  @Column(name = "level", length = 45)
  private String             level      = "";

  @Column(name = "point")
  private int                point;

  @Column(name = "point_redeemed")
  private int                pointRedeemed;

  @NotNull
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private MEMBER_ROLE        role       = MEMBER_ROLE.MEMBER;

  @NotNull
  @Column(name = "login_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private MEMBER_LOGIN_TYPE  loginType;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "member",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberPhone>   phones;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "member",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberEmail>   emails;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "member",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberAddress> addresses;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
  @JoinTable(name = "relation_member_store",
    joinColumns = { @JoinColumn(name = "member_id") },
    inverseJoinColumns = { @JoinColumn(name = "store_id") })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<Store>         stores;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
  @JoinTable(name = "relation_member_tag",
    joinColumns = { @JoinColumn(name = "member_id") },
    inverseJoinColumns = { @JoinColumn(name = "tag_id") })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProductTag>    tags;

  /**
   * validate
   *
   * @return
   */
  public boolean validate() {
    if (StringUtils.isBlank(this.getAccount())) { return false; }
    if (StringUtils.isBlank(this.getFirstName())) { return false; }
    if (StringUtils.isBlank(this.getLastName())) { return false; }
    if (StringUtils.isBlank(this.getMiddleName())) {
      this.setMiddleName("");
    }

    // validate addresses
    Iterator<MemberAddress> itAddresses = this.getAddresses().iterator();
    while (itAddresses.hasNext()) {
      MemberAddress address = itAddresses.next();
      if (!address.validate()) {
        itAddresses.remove();
      }
    }

    // validate phones
    Iterator<MemberPhone> itPhones = this.getPhones().iterator();
    while (itPhones.hasNext()) {
      MemberPhone phone = itPhones.next();
      if (!phone.validate()) {
        itPhones.remove();
      }
    }

    // validate emails
    Iterator<MemberEmail> itEmails = this.getEmails().iterator();
    while (itEmails.hasNext()) {
      MemberEmail email = itEmails.next();
      if (!email.validate()) {
        itEmails.remove();
      }
    }

    return true;
  }

  /**
   * @return the stores
   */
  public Set<Store> getStores() {
    return this.stores;
  }

  /**
   * @param stores
   *          the stores to set
   */
  public void setStores(Set<Store> stores) {
    this.stores = stores;
  }

  /**
   * @return the ProductTags
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
   * @return the addresses
   */
  public Set<MemberAddress> getAddresses() {
    return this.addresses;
  }

  /**
   * @param addresses
   *          the addresses to set
   */
  public void setAddresses(Set<MemberAddress> addresses) {
    this.addresses = addresses;
  }

  /**
   * @return the loginType
   */
  public MEMBER_LOGIN_TYPE getLoginType() {
    return this.loginType;
  }

  /**
   * @param loginType
   *          the loginType to set
   */
  public void setLoginType(MEMBER_LOGIN_TYPE loginType) {
    this.loginType = loginType;
  }

  /**
   * @return the emails
   */
  public Set<MemberEmail> getEmails() {
    return this.emails;
  }

  /**
   * @param emails
   *          the emails to set
   */
  public void setEmails(Set<MemberEmail> emails) {
    this.emails = emails;
  }

  /**
   * @return the phones
   */
  public Set<MemberPhone> getPhones() {
    return this.phones;
  }

  /**
   * @param phones
   *          the phones to set
   */
  public void setPhones(Set<MemberPhone> phones) {
    this.phones = phones;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * @param firstName
   *          the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the middleName
   */
  public String getMiddleName() {
    return this.middleName;
  }

  /**
   * @param middleName
   *          the middleName to set
   */
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  // forum attributes
  /**
   * @return the nickName
   */
  public String getNickName() {
    return this.nickName;
  }

  /**
   * @param nickName
   *          the nickName to set
   */
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * @return the signature
   */
  public String getSignature() {
    return this.signature;
  }

  /**
   * @param signature
   *          the signature to set
   */
  public void setSignature(String signature) {
    this.signature = signature;
  }

  /**
   * @return the avatar
   */
  public String getAvatar() {
    return this.avatar;
  }

  /**
   * @param avatar
   *          the avatar to set
   */
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  /**
   * @return the level
   */
  public String getLevel() {
    return this.level;
  }

  /**
   * @param level
   *          the level to set
   */
  public void setLevel(String level) {
    this.level = level;
  }

  /**
   * @return the point
   */
  public int getPoint() {
    return this.point;
  }

  /**
   * @param point
   *          the point to set
   */
  public void setPoint(int point) {
    this.point = point;
  }

  /**
   * @return the point_redeemed
   */
  public int getPointRedeemed() {
    return this.pointRedeemed;
  }

  /**
   * @param point
   *          the point to set
   */
  public void setPointRedeemed(int pointredeemed) {
    this.pointRedeemed = pointredeemed;
  }

  /**
   * @return the role
   */
  public MEMBER_ROLE getRole() {
    return this.role;
  }

  /**
   * @param role
   *          the role to set
   */
  public void setRole(MEMBER_ROLE role) {
    this.role = role;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the account
   */
  public String getAccount() {
    return this.account;
  }

  /**
   * @param account
   *          the account to set
   */
  public void setAccount(String account) {
    this.account = account;
  }

}
