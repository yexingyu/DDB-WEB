/**
 * 
 */
package com.dailydealsbox.database.model;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dailydealsbox.database.model.base.BaseEntityModel;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_LOGIN_TYPE;
import com.dailydealsbox.database.model.base.BaseEnum.MEMBER_ROLE;

/**
 * @author x_ye
 */
@Entity
//@Table(name = "member", uniqueConstraints = { @UniqueConstraint(columnNames = "account") })
@Table(name = "member")
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
  private MEMBER_ROLE        role;

  @NotNull
  @Column(name = "login_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private MEMBER_LOGIN_TYPE  loginType;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "member",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  //@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberPhone>   phones;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "member",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  //@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberEmail>   emails;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "member",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  //@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<MemberAddress> addresses;

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

  /**
   * setMemberForChildren
   */
  public void setMemberForChildren() {
    for (MemberAddress o : this.getAddresses()) {
      o.setMember(this);
    }
    for (MemberEmail o : this.getEmails()) {
      o.setMember(this);
    }
    for (MemberPhone o : this.getPhones()) {
      o.setMember(this);
    }
  }

}
