/**
 * 
 */
package com.dailydealsbox.web.base;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author x_ye
 */

public class BaseAuthorization {
  public final long          EXPIRY    = 3600 * 24 * 7 * 1000;
  private final String       KEY       = "BZV@Zk+sJTs$xa=p";
  private final ObjectMapper mapper    = new ObjectMapper();
  private final Key          aesKey    = new SecretKeySpec(KEY.getBytes(), "AES");
  private final String       separator = "|";

  /**
   * buildExpiredStamp
   * 
   * @return
   */
  public long buildExpiredStamp() {
    return System.currentTimeMillis() + EXPIRY;
  }

  /**
   * verify
   * 
   * @param cookieValue
   * @return
   */
  public AuthorizationToken verify(String cookieValue) {
    if (StringUtils.isBlank(cookieValue)) { return null; }
    String[] vals = StringUtils.splitByWholeSeparator(cookieValue, separator);
    if (vals.length != 2) { return null; }
    try {
      AuthorizationToken token = convertFromString(vals[0]);
      System.out.println("1. " + token.getExpired());
      System.out.println("2. " + System.currentTimeMillis());
      System.out.println("3. " + this.buildExpiredStamp());
      if (token != null
          && StringUtils.equals(Base64.encodeBase64String(DigestUtils.md5(token.toString())),
              vals[1]) && token.getExpired() > System.currentTimeMillis()) {
        return token;
      } else {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * convertFromString
   * 
   * @param str
   * @return
   * @throws Exception
   */
  private synchronized AuthorizationToken convertFromString(String str) throws Exception {
    return mapper.readValue(this.decrypt(Base64.decodeBase64(str)), AuthorizationToken.class);
  }

  /**
   * buildCookie
   * 
   * @param token
   * @return
   */
  public Cookie buildCookie(AuthorizationToken token) {
    try {
      Cookie cookie = new Cookie("token", this.buildCookieString(token));
      cookie.setPath("/");
      cookie.setMaxAge((int) (EXPIRY / 1000));
      return cookie;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * buildCookieString
   * 
   * @param token
   * @return
   * @throws Exception
   */
  private synchronized String buildCookieString(AuthorizationToken token) throws Exception {
    String authMd5String = Base64.encodeBase64String(DigestUtils.md5(token.toString()));
    String authString = Base64.encodeBase64String(this.encrypt(mapper.writeValueAsBytes(token)));
    return String.format("%s%s%s", authString, separator, authMd5String);
  }

  /**
   * encrypt
   * 
   * @param bytes
   * @return
   * @throws Exception
   */
  private byte[] encrypt(byte[] bytes) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
    return cipher.doFinal(bytes);
  }

  /**
   * decrypt
   * 
   * @param bytes
   * @return
   * @throws Exception
   */
  private byte[] decrypt(byte[] bytes) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, aesKey);
    return cipher.doFinal(bytes);
  }

  public static void main(String[] args) throws Exception {
    BaseAuthorization auth = new BaseAuthorization();

    //    String passwd = "123456";
    //    AuthorizationToken token = AuthorizationToken.newInstance(1, "yexingyu@gmail.com",
    //        System.currentTimeMillis(), 0);
    //    System.out.println(token);
    //
    //    String cookieString = auth.buildCookieString(token);
    //    System.out.println(cookieString);
    //    System.out.println("length=" + cookieString.length());
    //
    //    AuthorizationToken t = auth.verify(cookieString);
    //    System.out.println(t);

    String str = "hucsqxeV+5avg3ctV4f/y0xh0OtGdfyQeHRH/ULs6/3hJzgjCvtjNlKSdXGu6X0oBq/iQ9g+op/V6wriytBnVBoL1T5nffQ88Br9lZEz2CTMe8L4TIYaWNqrD+nXgj0i|ah+wvR4NVvvzSPvQaiop2Q==";
    AuthorizationToken tt = auth.verify(str);
    System.out.println(tt);
  }
}
