/**
 * 
 */
package com.dailydealsbox.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.dailydealsbox.database.model.base.BaseModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "member_emails")
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MemberEmail extends BaseModel {

  @NotNull
  @Column(name = "member_id", nullable = false)
  private int    memberId;

  @NotNull
  @Column(name = "email", nullable = false, length = 100)
  private String email;

  /**
   * @return the memberId
   */
  public int getMemberId() {
    return this.memberId;
  }

  /**
   * @param memberId
   *          the memberId to set
   */
  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

}
