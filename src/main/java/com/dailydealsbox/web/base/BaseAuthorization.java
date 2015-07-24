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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author x_ye
 */

public class BaseAuthorization {
  public final static String TOKEN  = AuthorizationToken.class.toString();
  public final static long   EXPIRY = 3600 * 24 * 7 * 1000;
  private final String       KEY    = "BZV@Zk+sJTs$xa=p";
  private final ObjectMapper mapper = new ObjectMapper();
  private final Key          aesKey = new SecretKeySpec(this.KEY.getBytes(), "AES");

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
    byte[] cookieBytes = Base64.decodeBase64(cookieValue);
    if (cookieBytes.length <= 16) { return null; }
    byte[] md5hash = ArrayUtils.subarray(cookieBytes, 0, 16);
    byte[] cookieData = ArrayUtils.subarray(cookieBytes, 16, cookieBytes.length);
    try {
      AuthorizationToken token = this.convertToAuthorization(cookieData);
      if (token != null && StringUtils.equals(Base64.encodeBase64String(DigestUtils.md5(token.toString())), Base64.encodeBase64String(md5hash))
          && token.getExpired() > System.currentTimeMillis()) {
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
   * convertToAuthorization
   *
   * @param bytes
   * @return
   * @throws Exception
   */
  private synchronized AuthorizationToken convertToAuthorization(byte[] bytes) throws Exception {
    return this.mapper.readValue(this.decrypt(bytes), AuthorizationToken.class);
  }

  /**
   * buildCookie
   *
   * @param token
   * @param expiry
   * @return
   */
  public Cookie buildCookie(AuthorizationToken token, int expiry) {
    try {
      Cookie cookie = new Cookie("token", this.buildCookieString(token));
      cookie.setPath("/");
      cookie.setMaxAge(expiry);
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
    byte[] md5hash = DigestUtils.md5(token.toString());
    byte[] cookieData = this.encrypt(this.mapper.writeValueAsBytes(token));
    return Base64.encodeBase64URLSafeString(ArrayUtils.addAll(md5hash, cookieData));
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
    cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);
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
    cipher.init(Cipher.DECRYPT_MODE, this.aesKey);
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

    String str = "Lb_AdFeW9EdlIP1qlyaQk7UgP91B9FAlIXa8Qjcx_dsjreVBd5hcUwOTXa040CnlCq8IyLMefISx_pjj0cKZXYC6Qi8t00Pus2uHH78gVNZ9nWa7E_2tVs6OI-PLU642soXCfW5de3zOZPMQv5e9fA";
    //byte[] bytes = DigestUtils.md5(str);
    //System.out.println(bytes.length);
    byte[] bytes = Base64.decodeBase64(str);
    System.out.println(bytes.length);

    AuthorizationToken tt = auth.verify(str);
    System.out.println(tt);
  }
}
