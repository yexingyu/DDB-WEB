/**
 * 
 */
package com.dailydealsbox.web.base;

/**
 * @author x_ye
 */
public class AuthorizationToken {
  private int    accountId;
  private String account;
  private int    isAdministrator;
  private long   expired;

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
   * @param accountId
   * @param account
   * @param expired
   * @param isAdministrator
   * @return
   */
  public static AuthorizationToken newInstance(int accountId, String account, long expired,
      int isAdministrator) {
    return new AuthorizationToken(accountId, account, expired, isAdministrator);
  }

  /*
   * Constructor
   */
  public AuthorizationToken() {}

  public AuthorizationToken(int accountId, String account, long expired, int isAdministrator) {
    this.setAccountId(accountId);
    this.setAccount(account);
    this.setIsAdministrator(isAdministrator);
    this.setExpired(expired);
  }

  /**
   * @return the isAdministrator
   */
  public int getIsAdministrator() {
    return this.isAdministrator;
  }

  /**
   * @param isAdministrator
   *          the isAdministrator to set
   */
  public void setIsAdministrator(int isAdministrator) {
    this.isAdministrator = isAdministrator;
  }

  /**
   * @return the accountId
   */
  public int getAccountId() {
    return this.accountId;
  }

  /**
   * @param accountId
   *          the accountId to set
   */
  public void setAccountId(int accountId) {
    this.accountId = accountId;
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
    return String.format("%d,%s,%d,%d", this.getAccountId(), this.getAccount(), this.getExpired(),
        this.getIsAdministrator());
  }
}
