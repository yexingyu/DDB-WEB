/**
 *
 */
package com.dailydealsbox.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailydealsbox.web.mail.SmtpMailSender;

/**
 * @author x_ye
 */
@Service
public class MailService {

  @Autowired
  private SmtpMailSender sender;

  /**
   * send
   *
   * @param to
   * @param subject
   * @param body
   * @throws Exception
   */
  public void send(String to, String subject, String body) throws Exception {
    this.sender.send(to, subject, body);
  }

}
