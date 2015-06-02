/**
 * 
 */
package com.dailydealsbox.web.base;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;

import org.apache.commons.codec.binary.Base64;

import com.dailydealsbox.database.model.Account;

/**
 * @author x_ye
 */
public class BaseAuthorization {
  public static int     EXPIRY = 3600 * 24 * 31;
  private static String KEY    = "BZV@Zk+sJTs$xa=p";

  /**
   * verify
   * 
   * @param cookieValue
   * @return
   */
  public static boolean verify(String cookieValue) {
    return true;
  }

  /**
   * buildCookie
   * 
   * @param account
   * @return
   * @throws Exception
   */
  public static Cookie buildCookie(Account account) throws Exception {
    String cookieValue = buildCookieString(account);
    Cookie cookie = new Cookie("auth", encrypt(cookieValue));
    cookie.setPath("/");
    cookie.setMaxAge(EXPIRY);
    return cookie;
  }

  /**
   * buildCookieString
   * 
   * @param account
   * @return
   */
  public static String buildCookieString(Account account) {
    return String.format("%d,%s,%d", account.getId(), account.getAccount(),
        System.currentTimeMillis() + 1000 * EXPIRY);
  }

  /**
   * encrypt
   * 
   * @param value
   * @return
   * @throws Exception
   */
  public static String encrypt(String value) throws Exception {
    Key aesKey = new SecretKeySpec(KEY.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");

    // encrypt the text
    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
    byte[] encrypted = cipher.doFinal(value.getBytes());
    return Base64.encodeBase64String(encrypted);
  }

  /**
   * decrypt
   * 
   * @param value
   * @return
   * @throws Exception
   */
  public static String decrypt(String value) throws Exception {
    Key aesKey = new SecretKeySpec(KEY.getBytes(), "AES");
    Cipher cipher = Cipher.getInstance("AES");

    // decrypt the text
    cipher.init(Cipher.DECRYPT_MODE, aesKey);
    return new String(cipher.doFinal(Base64.decodeBase64(value)));
  }

  public static void main(String[] args) throws Exception {
    System.out.println("key_size = " + KEY.getBytes().length);

    String str = "Hello World!";
    String encrypted = encrypt(str);
    System.out.println("encrypted=" + encrypted);

    String decrypted = decrypt(encrypted);
    System.out.println("decrypted=" + decrypted);

  }
}
