/**
 *
 */
package com.dailydealsbox.web.service.impl;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.web.database.model.MemberEmail;
import com.dailydealsbox.web.database.repository.MemberEmailRepository;
import com.dailydealsbox.web.mail.SmtpMailSender;
import com.dailydealsbox.web.service.MailService;

/**
 * @author x_ye
 */
@Service
@Transactional
public class MailServiceImpl implements MailService {

  @Autowired
  private MemberEmailRepository repo;
  @Autowired
  private SmtpMailSender        sender;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MailService#send(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public void send(String to, String subject, String body) throws Exception {
    this.sender.send(to, subject, body);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MailService#verifyEmail(java.lang.String, java.lang.String)
   */
  @Override
  public MemberEmail verifyEmail(String hashCode, String ip) {
    MemberEmail email = this.repo.findTopByHashCode(hashCode);
    if (email == null) { return null; }
    email.setVerified(true);
    email.setVerifiedAt(Calendar.getInstance().getTime());
    email.setVerifiedIp(ip);
    return this.repo.save(email);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MailService#sendVerifyEmail(java.lang.String)
   */
  @Override
  public void sendVerifyEmail(MemberEmail email) {
    this.saveHashCode(email);
    String body = "Click http://www.dailydealsbox.com/v1/index.html#/profile/verify_email/" + email.getHashCode() + " to verify your email.";
    try {
      this.send(email.getEmail(), "Email Verification on DailyDealsBox.com", body);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * saveHashCode
   *
   * @param email
   */
  public void saveHashCode(MemberEmail email) {
    if (StringUtils.isBlank(email.getHashCode()) && email.getHashCode().length() != 32) {
      email.generateHashCode();
    }
    this.update(email);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MailService#insert(com.dailydealsbox.database.model.MemberEmail)
   */
  @Override
  public void insert(MemberEmail email) {
    this.fix(email);
    this.repo.save(email);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MailService#update(com.dailydealsbox.database.model.MemberEmail)
   */
  @Override
  public void update(MemberEmail email) {
    this.fix(email);
    this.repo.save(email);
  }

  /**
   * fix
   *
   * @param email
   */
  private void fix(MemberEmail email) {

  }
}
