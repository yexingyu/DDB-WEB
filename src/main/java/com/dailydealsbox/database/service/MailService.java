/**
 *
 */
package com.dailydealsbox.database.service;

import com.dailydealsbox.database.model.MemberEmail;

/**
 * @author x_ye
 */
public interface MailService {

  /**
   * verifyEmail
   * 
   * @param hashCode
   * @param ip
   * @return
   */
  public MemberEmail verifyEmail(String hashCode, String ip);

  /**
   * sendVerifyEmail
   *
   * @param email
   */
  public void sendVerifyEmail(MemberEmail email);

  /**
   * send
   *
   * @param to
   * @param subject
   * @param body
   * @throws Exception
   */
  public void send(String to, String subject, String body) throws Exception;

  /**
   * insert
   *
   * @param email
   */
  public void insert(MemberEmail email);

  /**
   * update
   *
   * @param email
   */
  public void update(MemberEmail email);
}
