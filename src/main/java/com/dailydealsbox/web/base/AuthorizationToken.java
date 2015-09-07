/**
 *
 */
package com.dailydealsbox.web.base;

import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;

/**
 * @author x_ye
 */
public class AuthorizationToken {

  private int         memberId;
  private String      account;
  private String      password;
  private MEMBER_ROLE role;
  private long        expired;

  /**
   * newInstance
   *
   * @return
   */
  public static AuthorizationToken newInstance() {
    return new AuthorizationToken();
  }

  /**
   * newInstance
   *
   * @param memberId
   * @param account
   * @param password
   * @param expired
   * @param role
   * @return
   */
  public static AuthorizationToken newInstance(int memberId, String account, String password, long expired, MEMBER_ROLE role) {
    return new AuthorizationToken(memberId, account, password, expired, role);
  }

  /*
   * Constructor
   */
  public AuthorizationToken() {}

  public AuthorizationToken(int memberId, String account, String password, long expired, MEMBER_ROLE role) {
    this.setMemberId(memberId);
    this.setAccount(account);
    this.setPassword(password);
    this.setRole(role);
    this.setExpired(expired);
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
   * @return the memberId
   */
  public int getMemberId() {
    return this.memberId;
  }

  /**
   * @param memberId
   *          the memberId to set
   */
  public void setMemberId(int memberId) {
    this.memberId = memberId;
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
   * @return the expired
   */
  public long getExpired() {
    return this.expired;
  }

  /**
   * @param expired
   *          the expired to set
   */
  public void setExpired(long expired) {
    this.expired = expired;
  }

  @Override
  public String toString() {
    return String.format("%d,%s,%d,%s", this.getMemberId(), this.getAccount(), this.getExpired(), this.getRole());
  }
}
