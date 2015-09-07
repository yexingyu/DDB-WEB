/**
 *
 */
package com.dailydealsbox.web.database.repository;

import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.web.database.model.MemberEmail;

/**
 * @author x_ye
 */
public interface MemberEmailRepository extends CrudRepository<MemberEmail, Integer> {

  /**
   * findTopByEmail
   *
   * @param email
   * @return
   */
  public MemberEmail findTopByEmail(String email);

  /**
   * findTopByHashCode
   *
   * @param hashCode
   * @return
   */
  public MemberEmail findTopByHashCode(String hashCode);

}
