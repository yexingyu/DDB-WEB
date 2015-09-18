/**
 *
 */
package com.dailydealsbox.web.service;

import javax.servlet.http.Cookie;

import com.dailydealsbox.web.base.AuthorizationToken;

/**
 * @author x_ye
 */
public interface AuthorizationService {

  /**
   * verify
   *
   * @param cookieValue
   * @return
   */
  public AuthorizationToken verify(String cookieValue);

  /**
   * buildCookie
   *
   * @param token
   * @param expiry
   * @return
   */
  public Cookie buildCookie(AuthorizationToken token, int expiry);

  /**
   * buildExpiredStamp
   *
   * @return
   */
  public long buildExpiredStamp();
}
