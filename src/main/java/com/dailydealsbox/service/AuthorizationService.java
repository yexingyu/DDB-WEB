/**
 * 
 */
package com.dailydealsbox.service;

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
   * @return
   */
  public Cookie buildCookie(AuthorizationToken token);

  /**
   * buildExpiredStamp
   * 
   * @return
   */
  public long buildExpiredStamp();
}
