/**
 * 
 */
package com.dailydealsbox.database.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "members", uniqueConstraints = { @UniqueConstraint(columnNames = "account") })
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
  private String             firstName;

  @NotNull
  @Column(name = "middle_name", nullable = false, length = 100)
  private String             middleName;

  @NotNull
  @Column(name = "last_name", nullable = false, length = 100)
  private String             lastName;

  @NotNull
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private ROLE               role;

  @NotNull
  @Column(name = "login_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private LOGIN_TYPE         loginType;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberPhone>   phones;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberEmail>   emails;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberAddress> addresses;

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
  public LOGIN_TYPE getLoginType() {
    return this.loginType;
  }

  /**
   * @param loginType
   *          the loginType to set
   */
  public void setLoginType(LOGIN_TYPE loginType) {
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

  /**
   * @return the role
   */
  public ROLE getRole() {
    return this.role;
  }

  /**
   * @param role
   *          the role to set
   */
  public void setRole(ROLE role) {
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

  /**
   * @author x_ye
   */
  public static enum ROLE {
    MEMBER, ADMIN
  }

  /**
   * @author x_ye
   */
  public static enum LOGIN_TYPE {
    DAILYDEALSBOX, FACEBOOK
  }
}
