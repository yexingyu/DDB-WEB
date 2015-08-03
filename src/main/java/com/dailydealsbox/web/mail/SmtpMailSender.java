/**
 *
 */
package com.dailydealsbox.web.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @author x_ye
 */
@Component
public class SmtpMailSender {

  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String sender;

  /**
   * send
   *
   * @param to
   * @param subject
   * @param body
   * @throws Exception
   */
  public void send(String to, String subject, String body) throws Exception {

    MimeMessage message = this.javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setSubject(subject);
    helper.setTo(to);
    helper.setText(body);
    helper.setFrom(this.sender, "Daily Deals Box");

    this.javaMailSender.send(message);
  }
}
