/**
 *
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.MemberEmail;

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
